import { useState } from "react";

function PostCard({ post, onAddComment }) {
  const [comment, setComment] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    const success = await onAddComment(post.id, comment);

    if (success) {
      setComment("");
    }
  }

  return (
    <article className="card post-card">
      <div className="post-header">
        <h3>{post.title}</h3>
        <span className="muted">
          {post.createdAt ? new Date(post.createdAt).toLocaleString() : ""}
        </span>
      </div>

      <p className="muted">
        Szerző: <strong>{post.username}</strong>
      </p>

      <p>{post.content}</p>

      <section className="comments">
        <h4>Kommentek</h4>

        {post.commentDTOS?.length > 0 ? (
          post.commentDTOS.map((commentItem, index) => (
            <div className="comment" key={index}>
              <strong>{commentItem.userName}</strong>
              <p>{commentItem.content}</p>
              <span className="muted">
                {commentItem.createdAt
                  ? new Date(commentItem.createdAt).toLocaleString()
                  : ""}
              </span>
            </div>
          ))
        ) : (
          <p className="muted">Még nincs komment.</p>
        )}
      </section>

      <form className="comment-form" onSubmit={handleSubmit}>
        <textarea
          placeholder="Írj kommentet..."
          value={comment}
          onChange={(event) => setComment(event.target.value)}
          required
        />

        <button type="submit">Komment küldése</button>
      </form>
    </article>
  );
}

export default PostCard;
