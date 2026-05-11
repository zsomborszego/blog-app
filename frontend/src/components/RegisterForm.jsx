import { useState } from "react";

function RegisterForm({ auth }) {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    await auth.register({
      username,
      email,
      password
    });
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Regisztráció</h2>

      <label>
        Felhasználónév
        <input
          type="text"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          required
        />
      </label>

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

      <button type="submit">Regisztráció</button>
    </form>
  );
}

export default RegisterForm;
