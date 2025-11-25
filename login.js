async function login() {
  const user = document.getElementById("username").value;
  const pass = document.getElementById("password").value;

  const response = await fetch("/api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username: user, password: pass })
  });

  const result = await response.json();

  if (response.ok) {
    localStorage.setItem("token", result.token);
    alert("Login successful!");
  } else {
    alert(result.message);
  }
}
