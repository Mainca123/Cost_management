const BASE_URL = window.location.origin;

const API = `${BASE_URL}/api/v1/user/expense`;
const CATEGORY_API = `${BASE_URL}/api/v1/user/categories`;

let editId = null;

const expenseModal = document.getElementById("expenseModal");
const modalTitle   = document.getElementById("modalTitle");

const amount  = document.getElementById("amount");
const note    = document.getElementById("note");
const spentAt = document.getElementById("spentAt");

/* ================= TOKEN ================= */
function getToken() {
  return localStorage.getItem("ACCESS_TOKEN");
}

/* ================= LOAD EXPENSE ================= */
async function loadExpense() {
  const token = getToken();
  if (!token) {
    redirectHome();
    return;
  }

  const res = await fetch(API, {
    headers: { Authorization: `Bearer ${token}` }
  });

  if (res.status === 401) {
    logoutAndRedirect();
    return;
  }

  if (!res.ok) {
    console.error("Load expense failed");
    return;
  }

  const json = await res.json();
  const data = json.data || [];

  const tbody = document.getElementById("expenseTable");
  tbody.innerHTML = "";

  if (data.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="5" style="text-align:center;color:#888">
          Chưa có khoản chi nào
        </td>
      </tr>`;
    return;
  }

  data.forEach(e => {
    tbody.innerHTML += `
      <tr>
        <td>${e.categoryName}</td>
        <td>${Number(e.amount).toLocaleString("vi-VN")} VND</td>
        <td>${e.note || "-"}</td>
        <td>${e.spentAt ? new Date(e.spentAt).toLocaleString("vi-VN") : "-"}</td>
        <td class="col-action">
          <button class="btn btn-login" onclick="editExpense(${e.id})">Sửa</button>
          <button class="btn btn-logout" onclick="removeExpense(${e.id})">Xóa</button>
        </td>
      </tr>
    `;
  });
}

/* ================= MODAL ================= */
async function openForm() {
  editId = null;
  modalTitle.innerText = "Thêm khoản chi";
  expenseModal.style.display = "flex";
  clearForm();
  await loadCategoriesForSelect();
}

function closeForm() {
  expenseModal.style.display = "none";
}

function clearForm() {
  document.getElementById("categoryId").value = "";
  amount.value = "";
  note.value = "";
  spentAt.value = "";
}

/* ================= EDIT ================= */
async function editExpense(id) {
  const token = getToken();
  if (!token) return redirectHome();

  const res = await fetch(`${API}/${id}`, {
    headers: { Authorization: `Bearer ${token}` }
  });

  if (res.status === 401) {
    logoutAndRedirect();
    return;
  }

  if (!res.ok) {
    alert("Không tải được khoản chi");
    return;
  }

  const json = await res.json();
  const e = json.data;

  editId = e.id;
  modalTitle.innerText = "Sửa khoản chi";
  expenseModal.style.display = "flex";

  await loadCategoriesForSelect();

  document.getElementById("categoryId").value = e.categoryId;
  amount.value = e.amount;
  note.value = e.note || "";
  spentAt.value = e.spentAt ? e.spentAt.slice(0, 16) : "";
}

/* ================= SAVE ================= */
async function saveExpense() {
  const token = getToken();
  if (!token) return redirectHome();

  const body = {
    categoryId: Number(document.getElementById("categoryId").value),
    amount: Number(amount.value),
    note: note.value,
    spentAt: spentAt.value || null
  };

  if (!body.categoryId || !body.amount) {
    alert("Vui lòng chọn danh mục và nhập số tiền");
    return;
  }

  const res = await fetch(editId ? `${API}/${editId}` : API, {
    method: editId ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(body)
  });

  if (res.status === 401) {
    logoutAndRedirect();
    return;
  }

  if (!res.ok) {
    alert("Lưu khoản chi thất bại");
    return;
  }

  closeForm();
  loadExpense();
}

/* ================= DELETE ================= */
async function removeExpense(id) {
  if (!confirm("Xóa khoản chi này?")) return;

  const token = getToken();
  if (!token) return redirectHome();

  const res = await fetch(`${API}/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` }
  });

  if (res.status === 401) {
    logoutAndRedirect();
    return;
  }

  if (!res.ok) {
    alert("Xóa khoản chi thất bại");
    return;
  }

  loadExpense();
}

/* ================= LOAD CATEGORY ================= */
async function loadCategoriesForSelect() {
  const token = getToken();
  if (!token) return redirectHome();

  const res = await fetch(CATEGORY_API, {
    headers: { Authorization: `Bearer ${token}` }
  });

  if (res.status === 401) {
    logoutAndRedirect();
    return;
  }

  if (!res.ok) {
    console.error("Không load được danh mục");
    return;
  }

  const json = await res.json();
  const select = document.getElementById("categoryId");

  select.innerHTML = `<option value="">-- Chọn danh mục --</option>`;

  (json.data || []).forEach(c => {
    const opt = document.createElement("option");
    opt.value = c.id;
    opt.textContent = c.name;
    select.appendChild(opt);
  });
}

/* ================= UTILS ================= */
function logoutAndRedirect() {
  localStorage.removeItem("ACCESS_TOKEN");
  redirectHome();
}

function redirectHome() {
  window.location.href = "/page/home";
}

/* ================= INIT ================= */
document.addEventListener("DOMContentLoaded", loadExpense);
