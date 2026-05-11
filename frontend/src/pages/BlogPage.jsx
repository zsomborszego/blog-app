import { useEffect, useState } from "react";
import { api } from "../api/client";
import NewPostForm from "../components/NewPostForm";
import PostCard from "../components/PostCard";

function BlogPage({ auth }) {
  const [posts, setPosts] = useState([]);
  const [pageError, setPageError] = useState("");
  const [loading, setLoading] = useState(false);

  async function loadPosts() {
    setLoading(true);
    setPageError("");

    try {
      const data = await api.getPosts();
      setPosts(data.postDTOS || []);
    } catch (error) {
      setPageError("Nem sikerült betölteni a posztokat.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadPosts();
  }, []);

  async function handleCreatePost({ title, content }) {
    try {
      await api.createPost({
        userId: Number(auth.userId),
        title,
        content
      });

      await loadPosts();
      return true;
    } catch (error) {
      setPageError("Nem sikerült létrehozni a posztot.");
      return false;
    }
  }

  async function handleAddComment(postId, content) {
    try {
      await api.addComment(postId, {
        userId: Number(auth.userId),
        content
      });

      await loadPosts();
      return true;
    } catch (error) {
      setPageError("Nem sikerült elküldeni a kommentet.");
      return false;
    }
  }

  return (
    <main className="page">
      <section className="user-bar">
        <div>
          <span className="muted">Bejelentkezve mint</span>
          <strong>{auth.username}</strong>
        </div>

        <button type="button" onClick={auth.logout}>
          Kijelentkezés
        </button>
      </section>

      <NewPostForm onCreatePost={handleCreatePost} />

      {pageError && <p className="error-message">{pageError}</p>}

      <section className="posts-section">
        <h2>Posztok</h2>

        {loading && <p>Betöltés...</p>}

        {!loading && posts.length === 0 && (
          <p className="muted">Még nincs poszt.</p>
        )}

        {posts.map((post, index) => (
          <PostCard
            key={post.id || index}
            post={post}
            onAddComment={handleAddComment}
          />
        ))}
      </section>
    </main>
  );
}

export default BlogPage;
