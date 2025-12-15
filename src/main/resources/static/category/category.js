/* =============================
        CONFIG
============================= */
const API_BASE = `${window.location.origin}/api/v1/user`;
const TOKEN_KEY = "ACCESS_TOKEN";


/* =============================
        AUTH GUARD
============================= */
function getTokenOrRedirect() {
  const token = localStorage.getItem(TOKEN_KEY);
  if (!token) {
    window.location.href = "../home/home.html";
    return null;
  }
  return token;
}

/* =============================
        STATE
============================= */
let editId = null;

/* =============================
        LOAD CATEGORIES (GET)
============================= */
async function loadCategories() {
  const token = getTokenOrRedirect();
  if (!token) return;

  try {
    const res = await fetch(`${API_BASE}/categories`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    // ❌ Token hết hạn
    if (res.status === 401 || res.status === 403) {
      localStorage.removeItem(TOKEN_KEY);
      window.location.href = "../home/home.html";
      return;
    }

    const json = await res.json();
    const tbody = document.getElementById("categoryTable");
    tbody.innerHTML = "";

    if (!json.data || json.data.length === 0) {
      tbody.innerHTML = `
        <tr>
          <td colspan="5" style="text-align:center;color:#777">
            Chưa có danh mục nào
          </td>
        </tr>
      `;
      return;
    }

    json.data.forEach(c => {
      tbody.innerHTML += `
        <tr>
          <td>${c.id}</td>
          <td>${c.name}</td>
          <td>${c.description || ""}</td>
          <td>${new Date(c.createdAt).toLocaleDateString("vi-VN")}</td>
          <td>
            <button class="btn btn-login"
              onclick="editCategory(${c.id}, '${escapeHtml(c.name)}', '${escapeHtml(c.description || "")}')">
              Sửa
            </button>
            <button class="btn btn-logout" onclick="deleteCategory(${c.id})">
              Xóa
            </button>
          </td>
        </tr>
      `;
    });

  } catch (err) {
    console.error("Load categories error:", err);
  }
}

/* =============================
        MODAL CONTROL
============================= */
function openForm() {
  editId = null;
  document.getElementById("modalTitle").innerText = "Thêm danh mục";
  document.getElementById("catName").value = "";
  document.getElementById("catDesc").value = "";
  document.getElementById("categoryModal").style.display = "flex";
}

function editCategory(id, name, desc) {
  editId = id;
  document.getElementById("modalTitle").innerText = "Sửa danh mục";
  document.getElementById("catName").value = name;
  document.getElementById("catDesc").value = desc;
  document.getElementById("categoryModal").style.display = "flex";
}

function closeForm() {
  document.getElementById("categoryModal").style.display = "none";
}

/* =============================
        SAVE CATEGORY
        POST / PUT
============================= */
async function saveCategory() {
  const token = getTokenOrRedirect();
  if (!token) return;

  const name = document.getElementById("catName").value.trim();
  const description = document.getElementById("catDesc").value.trim();

  if (!name) {
    alert("Tên danh mục không được để trống");
    return;
  }

  const body = { name, description };

  const url = editId
    ? `${API_BASE}/categories/${editId}`
    : `${API_BASE}/categories`;

  const method = editId ? "PUT" : "POST";

  try {
    const res = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(body)
    });

    if (res.status === 401) {
      localStorage.removeItem(TOKEN_KEY);
      window.location.href = "../home/home.html";
      return;
    }

    closeForm();
    loadCategories();

  } catch (err) {
    console.error("Save category error:", err);
  }
}

/* =============================
        DELETE
============================= */
async function deleteCategory(id) {
  if (!confirm("Xóa danh mục này?")) return;

  const token = getTokenOrRedirect();
  if (!token) return;

  try {
    const res = await fetch(`${API_BASE}/categories/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (res.status === 401) {
      localStorage.removeItem(TOKEN_KEY);
      window.location.href = "../home/home.html";
      return;
    }

    loadCategories();

  } catch (err) {
    console.error("Delete category error:", err);
  }
}

/* =============================
        UTILS
============================= */
function escapeHtml(text) {
  return text.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

/* =============================
        INIT
============================= */
loadCategories();
