import { useState } from "react";

function NewPostForm({ onCreatePost }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    const success = await onCreatePost({
      title,
      content
    });

    if (success) {
      setTitle("");
      setContent("");
    }
  }

  return (
    <section className="card">
      <h2>Új poszt létrehozása</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Cím
          <input
            type="text"
            value={title}
            onChange={(event) => setTitle(event.target.value)}
            required
          />
        </label>

        <label>
          Tartalom
          <textarea
            value={content}
            onChange={(event) => setContent(event.target.value)}
            required
          />
        </label>

        <button type="submit">Poszt létrehozása</button>
      </form>
    </section>
  );
}

export default NewPostForm;
