import { useState } from "react";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";

function AuthPage({ auth }) {
  const [showLogin, setShowLogin] = useState(true);

  return (
    <main className="page">
      <section className="card auth-card">
        {showLogin ? (
          <LoginForm auth={auth} />
        ) : (
          <RegisterForm auth={auth} />
        )}

        {auth.authError && (
          <p className="error-message">{auth.authError}</p>
        )}

        <button
          className="link-button"
          type="button"
          onClick={() => setShowLogin(!showLogin)}
        >
          {showLogin
            ? "Nincs még felhasználód? Regisztrálj"
            : "Van már felhasználód? Jelentkezz be"}
        </button>
      </section>
    </main>
  );
}

export default AuthPage;
