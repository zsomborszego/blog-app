import { useState } from "react";
import { api } from "../api/client";

export function useAuth() {
  const [userId, setUserId] = useState(sessionStorage.getItem("userId"));
  const [username, setUsername] = useState(sessionStorage.getItem("username"));
  const [authError, setAuthError] = useState("");

  async function login({ email, password }) {
    setAuthError("");

    try {
      const data = await api.loginUser({ email, password });

      sessionStorage.setItem("userId", String(data.userId));
      sessionStorage.setItem("username", data.username);

      setUserId(String(data.userId));
      setUsername(data.username);

      return true;
    } catch (error) {
      setAuthError("Sikertelen bejelentkezés.");
      return false;
    }
  }

  async function register({ username, email, password }) {
    setAuthError("");

    try {
      const data = await api.registerUser({ username, email, password });

      sessionStorage.setItem("userId", String(data.userId));
      sessionStorage.setItem("username", data.username || username);

      setUserId(String(data.userId));
      setUsername(data.username || username);

      return true;
    } catch (error) {
      setAuthError("Sikertelen regisztráció.");
      return false;
    }
  }

  function logout() {
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("username");

    setUserId(null);
    setUsername(null);
    setAuthError("");
  }

  return {
    userId,
    username,
    authError,
    login,
    register,
    logout
  };
}