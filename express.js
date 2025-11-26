const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

const app = express();
app.use(express.json());

// Example user stored in DB (hashed password)
const users = [
  {
    username: "admin",
    passwordHash: "$2b$10$u4kGGG57tlelNxQvKMxFuOI38sEiGwm2CIyWhKTQpiVLSXoPsDnse" // hash of "1234"
  }
];

app.post("/api/login", async (req, res) => {
  const { username, password } = req.body;

  const user = users.find(u => u.username === username);
  if (!user) return res.status(400).json({ message: "User not found" });

  const valid = await bcrypt.compare(password, user.passwordHash);
  if (!valid) return res.status(400).json({ message: "Invalid password" });

  const token = jwt.sign({ username }, "SECRET_KEY", { expiresIn: "1h" });
  res.json({ token });
});

app.listen(3000, () => console.log("Server running on port 3000"));
