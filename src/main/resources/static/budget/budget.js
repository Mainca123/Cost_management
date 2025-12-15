const API = `${window.location.origin}/api/v1/user/budget`;
const token = localStorage.getItem("ACCESS_TOKEN");


let editId = null;

/* ================= UTIL ================= */
function redirectHome() {
  location.href = "../home/home.html";
}

function showError(msg) {
  alert(msg);
}

/* ================= LOAD ================= */
async function loadBudget() {
  if (!token) {
    showError("Bạn chưa đăng nhập");
    redirectHome();
    return;
  }

  let res;
  try {
    res = await fetch(API, {
      headers: { Authorization: `Bearer ${token}` }
    });
  } catch (err) {
    showError("Không kết nối được tới server");
    console.error(err);
    return;
  }

  if (res.status === 401) {
    localStorage.removeItem("ACCESS_TOKEN");
    showError("Phiên đăng nhập đã hết hạn");
    redirectHome();
    return;
  }

  if (!res.ok) {
    showError("Không tải được danh sách ngân sách");
    return;
  }

  let json;
  try {
    json = await res.json();
  } catch {
    showError("Dữ liệu trả về không hợp lệ");
    return;
  }

  const tbody = document.getElementById("budgetTable");
  if (!tbody) {
    console.warn("Không tìm thấy bảng budgetTable");
    return;
  }

  tbody.innerHTML = "";

  if (!json.data || json.data.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="4" style="text-align:center;color:#64748b">
          Chưa có ngân sách nào
        </td>
      </tr>
    `;
    return;
  }

  const today = new Date().toISOString().slice(0, 10);

  json.data.forEach(b => {
    const isActive =
      today >= b.startDate && today <= b.endDate;

    let limitClass = "limit-zero";
    if (b.limitAmount > 0) limitClass = "limit-positive";
    if (b.limitAmount < 0) limitClass = "limit-negative";

    tbody.innerHTML += `
      <tr class="${isActive ? "row-active" : ""}">
        <td>${b.startDate} → ${b.endDate}</td>
        <td class="${limitClass}">
          ${Number(b.limitAmount).toLocaleString("vi-VN")} VND
        </td>
        <td>${new Date(b.createdAt).toLocaleString("vi-VN")}</td>
        <td class="col-action">
          <button class="btn-edit" onclick="editBudget(${b.id})">Sửa</button>
          <button class="btn-delete" onclick="deleteBudget(${b.id})">Xóa</button>
        </td>
      </tr>
    `;
  });
}

/* ================= MODAL ================= */
function openForm() {
  editId = null;
  modalTitle.innerText = "Thiết lập ngân sách";
  budgetModal.style.display = "flex";
  clearForm();
}

function closeForm() {
  budgetModal.style.display = "none";
}

function clearForm() {
  startDate.value = "";
  endDate.value = "";
  limitAmount.value = "";
}

/* ================= EDIT ================= */
async function editBudget(id) {
  let res;
  try {
    res = await fetch(API, {
      headers: { Authorization: `Bearer ${token}` }
    });
  } catch {
    showError("Không kết nối được server");
    return;
  }

  if (!res.ok) {
    showError("Không tải được ngân sách để sửa");
    return;
  }

  const json = await res.json();
  const b = json.data.find(x => x.id === id);
  if (!b) {
    showError("Không tìm thấy ngân sách");
    return;
  }

  editId = b.id;
  modalTitle.innerText = "Sửa ngân sách";
  budgetModal.style.display = "flex";

  startDate.value = b.startDate;
  endDate.value = b.endDate;
  limitAmount.value = b.limitAmount;
}

/* ================= SAVE ================= */
async function saveBudget() {
  if (!startDate.value || !endDate.value || !limitAmount.value) {
    showError("Vui lòng nhập đầy đủ thông tin");
    return;
  }

  const body = {
    startDate: startDate.value,
    endDate: endDate.value,
    limitAmount: Number(limitAmount.value)
  };

  let res;
  try {
    res = await fetch(editId ? `${API}/${editId}` : API, {
      method: editId ? "PUT" : "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(body)
    });
  } catch {
    showError("Không gửi được dữ liệu lên server");
    return;
  }

  if (!res.ok) {
    showError("Lưu ngân sách thất bại");
    return;
  }

  closeForm();
  loadBudget();
}

/* ================= DELETE ================= */
async function deleteBudget(id) {
  if (!confirm("Xóa ngân sách này?")) return;

  let res;
  try {
    res = await fetch(`${API}/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` }
    });
  } catch {
    showError("Không kết nối được server");
    return;
  }

  if (!res.ok) {
    showError("Xóa ngân sách thất bại");
    return;
  }

  loadBudget();
}

/* ================= INIT ================= */
document.addEventListener("DOMContentLoaded", loadBudget);
