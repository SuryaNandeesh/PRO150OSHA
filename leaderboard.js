const express = require('express');
const fs = require('fs').promises;
const path = require('path');

let loginModule = null;
try {
    loginModule = require(path.join(__dirname, 'login.js'));
} catch (err) {
    // ignore: login.js might be a browser-only module or not present
}

// leaderboard.js
// Express router providing a simple leaderboard API.
// Now scores represent time (lower is better). Accepts either { time: number } or legacy { score: number } in requests.

const router = express.Router();
const BOARD_FILE = path.join(__dirname, 'leaderboard.json');
const MAX_ENTRIES = 100;

function getUsernameFromLogin() {
    if (!loginModule) return null;
    if (typeof loginModule.getUsername === 'function') {
        try { return loginModule.getUsername(); } catch (e) { return null; }
    }
    if (typeof loginModule.username === 'string') return loginModule.username;
    if (typeof loginModule.default === 'string') return loginModule.default;
    if (loginModule.default && typeof loginModule.default.getUsername === 'function') {
        try { return loginModule.default.getUsername(); } catch (e) { return null; }
    }
    return null;
}

async function readBoard() {
    try {
        const raw = await fs.readFile(BOARD_FILE, 'utf8');
        return JSON.parse(raw);
    } catch (err) {
        if (err.code === 'ENOENT') return [];
        throw err;
    }
}

async function writeBoard(board) {
    await fs.writeFile(BOARD_FILE, JSON.stringify(board, null, 2), 'utf8');
}

function getTimeFromEntry(entry) {
    if (!entry) return null;
    if (Number.isFinite(entry.time)) return Number(entry.time);
    if (Number.isFinite(entry.score)) return Number(entry.score); // legacy
    return null;
}

function normalizeEntry(entry) {
    if (!entry) return null;
    const time = getTimeFromEntry(entry);
    return {
        username: entry.username,
        time: (time === null ? null : time),
        createdAt: entry.createdAt || entry.updatedAt || null,
        updatedAt: entry.updatedAt || entry.createdAt || null
    };
}

function compareByTime(a, b) {
    const ta = getTimeFromEntry(a);
    const tb = getTimeFromEntry(b);
    const va = (ta === null ? Infinity : ta);
    const vb = (tb === null ? Infinity : tb);
    if (va !== vb) return va - vb; // smaller (faster) first
    // tie-breaker: earlier updatedAt first
    const au = a.updatedAt || '';
    const bu = b.updatedAt || '';
    return au.localeCompare(bu);
}

// Submit or update a time for the current user.
// Body: { time: number } optionally { username: '...' } to override login.js
// Legacy: { score: number } also accepted.
router.post('/submit', async (req, res) => {
    try {
        const rawTime = (req.body && (req.body.time !== undefined ? req.body.time : req.body.score));
        const time = Number(rawTime);
        if (!Number.isFinite(time) || time <= 0) return res.status(400).json({ error: 'Invalid time (must be a positive number)' });

        const usernameFromLogin = getUsernameFromLogin();
        const username = (req.body && req.body.username) || usernameFromLogin || 'anonymous';

        const board = await readBoard();
        const now = new Date().toISOString();

        const existing = board.find(e => e.username === username);
        if (existing) {
            const existingTime = getTimeFromEntry(existing);
            // update only if new time is faster (smaller)
            if (existingTime === null || time < existingTime) {
                existing.time = time;
                // keep legacy score field for compatibility if present
                if ('score' in existing) existing.score = time;
                existing.updatedAt = now;
            } else {
                existing.updatedAt = now; // still update timestamp
            }
        } else {
            board.push({ username, time, score: time, createdAt: now, updatedAt: now });
        }

        // Keep top entries sorted by time asc (fastest first), limit stored entries
        board.sort(compareByTime);
        const trimmed = board.slice(0, MAX_ENTRIES);

        await writeBoard(trimmed);

        const entry = trimmed.find(e => e.username === username);
        return res.json({ ok: true, entry: normalizeEntry(entry), leaderboard: trimmed.slice(0, 10).map(normalizeEntry) });
    } catch (err) {
        console.error('Leaderboard submit error:', err);
        return res.status(500).json({ error: 'Internal server error' });
    }
});

// Get top N times: ?limit=10
router.get('/top', async (req, res) => {
    try {
        const q = parseInt(req.query && req.query.limit, 10);
        const limit = (Number.isFinite(q) && q > 0) ? Math.min(q, MAX_ENTRIES) : 10;
        const board = await readBoard();
        board.sort(compareByTime);
        return res.json(board.slice(0, limit).map(normalizeEntry));
    } catch (err) {
        console.error('Leaderboard top error:', err);
        return res.status(500).json({ error: 'Internal server error' });
    }
});

// Get current user's leaderboard entry
router.get('/me', async (req, res) => {
    try {
        const usernameFromLogin = getUsernameFromLogin();
        const username = (req.query && req.query.username) || usernameFromLogin;
        if (!username) return res.status(400).json({ error: 'No username available' });

        const board = await readBoard();
        const entry = board.find(e => e.username === username) || null;
        return res.json({ username, entry: normalizeEntry(entry) });
    } catch (err) {
        console.error('Leaderboard me error:', err);
        return res.status(500).json({ error: 'Internal server error' });
    }
});

module.exports = router;