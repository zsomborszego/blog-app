import { useState } from "react";

function LoginForm({ auth }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    await auth.login({
      email,
      password
    });
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Bejelentkezés</h2>

      <label>
        Email
        <input
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
        />
      </label>

      <label>
        Jelszó
        <input
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />
      </label>

      <button type="submit">Belépés</button>
    </form>
  );
}

export default LoginForm;
