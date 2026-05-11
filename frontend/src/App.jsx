import { useAuth } from "./hooks/useAuth";
import AuthPage from "./pages/AuthPage";
import BlogPage from "./pages/BlogPage";

function App() {
  const auth = useAuth();

  return (
    <div className="app">
      <header className="app-header">
        <h1>Simple Blog</h1>
      </header>

      {!auth.userId ? (
        <AuthPage auth={auth} />
      ) : (
        <BlogPage auth={auth} />
      )}
    </div>
  );
}

export default App;
