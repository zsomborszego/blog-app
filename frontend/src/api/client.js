const API_BASE_URL = "http://localhost:8080/api";

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {})
    },
    ...options
  });

  if (!response.ok) {
    throw new Error(`API hiba: ${response.status}`);
  }

  const contentType = response.headers.get("content-type");

  if (contentType && contentType.includes("application/json")) {
    return response.json();
  }

  return null;
}

export const api = {
  registerUser(payload) {
    return request("/user", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },

  loginUser(payload) {
    return request("/user/login", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },

  getPosts() {
    return request("/post");
  },

  createPost(payload) {
    return request("/post", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },

  addComment(postId, payload) {
    return request(`/post/${postId}/comments`, {
      method: "POST",
      body: JSON.stringify(payload)
    });
  }
};
