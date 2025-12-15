/* =============================
        AUTH CONFIG
============================= */
const AUTH_API = `${window.location.origin}/api/v1/auth`;


/* =============================
        LOGIN
============================= */
async function login() {
  const email = document.getElementById("loginEmail")?.value.trim();
  const password = document.getElementById("loginPassword")?.value.trim();
  const errorEl = document.getElementById("loginError");

  if (errorEl) errorEl.textContent = "";

  if (!email || !password) {
    if (errorEl) errorEl.textContent = "Vui lòng nhập email và mật khẩu";
    return;
  }

  try {
    const res = await fetch(`${AUTH_API}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "*/*"
      },
      body: JSON.stringify({ email, password })
    });

    const data = await res.json();

    if (!res.ok) {
      if (errorEl)
        errorEl.textContent = data.message || "Đăng nhập thất bại";
      return;
    }

    // ✅ LƯU TOKEN
    localStorage.setItem(TOKEN_KEY, data.data.token);

    // Đóng modal + cập nhật UI
    closeAuth();
    updateUI();
	window.location.href = "/page/home";
  } catch (err) {
    if (errorEl)
      errorEl.textContent = "Không kết nối được server";
  }
}

/* =============================
        REGISTER
============================= */
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPassword(password) {
  // ≥8 ký tự, 1 hoa, 1 thường, 1 số
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(password);
}
async function register() {
  const fullName = document.getElementById("regName")?.value.trim();
  const email = document.getElementById("regEmail")?.value.trim();
  const password = document.getElementById("regPassword")?.value.trim();
  const errorEl = document.getElementById("registerError");

  if (errorEl) errorEl.textContent = "";

  // ❌ Thiếu dữ liệu
  if (!fullName || !email || !password) {
    if (errorEl)
      errorEl.textContent = "Vui lòng nhập đầy đủ thông tin";
    return;
  }

  // ❌ Email sai định dạng
  if (!isValidEmail(email)) {
    if (errorEl)
      errorEl.textContent = "Email không đúng định dạng";
    return;
  }

  // ❌ Password không đủ mạnh
  if (!isValidPassword(password)) {
    if (errorEl)
      errorEl.textContent =
        "Mật khẩu phải ≥ 8 ký tự, gồm chữ hoa, chữ thường và số";
    return;
  }

  try {
    const res = await fetch(`${AUTH_API}/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "*/*"
      },
      body: JSON.stringify({ fullName, email, password })
    });

    const data = await res.json();

    if (!res.ok) {
      if (errorEl)
        errorEl.textContent = data.message || "Đăng ký thất bại";
      return;
    }

    // ✅ Thành công → quay lại login
    openLogin();

  } catch (err) {
    if (errorEl)
      errorEl.textContent = "Không kết nối được server";
  }
}


/* =============================
        LOGOUT
============================= */
function logout() {
  // ❌ Xoá token
  localStorage.removeItem(TOKEN_KEY);

  // Đóng menu nếu đang mở
  document.getElementById("dropdownMenu")?.style.setProperty("display", "none");

  // Cập nhật UI
  updateUI();
  window.location.href = "/page/home";
}
