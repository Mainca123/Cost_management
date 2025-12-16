/* =============================
        AUTH CONFIG
============================= */
const AUTH_API = "https://cost-management-na38.onrender.com/api/v1/auth";
const TOKEN_KEY = "ACCESS_TOKEN";

/* =============================
        DEBUG CONFIG
============================= */
const DEBUG = true;

function log(...args) {
  if (DEBUG) console.log("[AUTH]", ...args);
}

function logError(...args) {
  if (DEBUG) console.error("[AUTH ❌]", ...args);
}

/* =============================
        LOADING STATE
============================= */
function setLoading(type, isLoading, message = "") {
  const btn = document.getElementById(type + "Btn");
  const statusEl = document.getElementById(type + "Status");

  if (btn) btn.disabled = isLoading;

  if (statusEl) {
    statusEl.textContent = isLoading ? message : "";
    statusEl.classList.toggle("loading", isLoading);
  }

  log(`🔄 ${type.toUpperCase()} loading =`, isLoading);
}

/* =============================
        VALIDATION
============================= */
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPassword(password) {
  // ≥8 ký tự, 1 hoa, 1 thường, 1 số
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(password);
}

/* =============================
        LOGIN
============================= */
async function login() {
  log("➡️ login() called");

  const email = document.getElementById("loginEmail")?.value.trim();
  const password = document.getElementById("loginPassword")?.value.trim();
  const errorEl = document.getElementById("loginError");

  if (errorEl) errorEl.textContent = "";

  if (!email || !password) {
    if (errorEl) errorEl.textContent = "Vui lòng nhập email và mật khẩu";
    return;
  }

  setLoading("login", true, "Đang đăng nhập...");

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
    log("📡 Login response:", data);

    if (!res.ok) {
      if (errorEl)
        errorEl.textContent = data.message || "Đăng nhập thất bại";
      return;
    }

    localStorage.setItem(TOKEN_KEY, data.data.token);
    log("✅ Token saved");

    closeAuth?.();
    updateUI?.();

    window.location.href = "/page/home";

  } catch (err) {
    logError("Login exception:", err);
    if (errorEl) errorEl.textContent = "Không kết nối được server";
  } finally {
    setLoading("login", false);
  }
}

/* =============================
        REGISTER
============================= */
async function register() {
  log("➡️ register() called");

  const fullName = document.getElementById("regName")?.value.trim();
  const email = document.getElementById("regEmail")?.value.trim();
  const password = document.getElementById("regPassword")?.value.trim();
  const errorEl = document.getElementById("registerError");

  if (errorEl) errorEl.textContent = "";

  if (!fullName || !email || !password) {
    if (errorEl) errorEl.textContent = "Vui lòng nhập đầy đủ thông tin";
    return;
  }

  if (!isValidEmail(email)) {
    if (errorEl) errorEl.textContent = "Email không đúng định dạng";
    return;
  }

  if (!isValidPassword(password)) {
    if (errorEl)
      errorEl.textContent =
        "Mật khẩu phải ≥ 8 ký tự, gồm chữ hoa, chữ thường và số";
    return;
  }

  setLoading("register", true, "Đang đăng ký...");

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
    log("📡 Register response:", data);

    if (!res.ok) {
      if (errorEl)
        errorEl.textContent = data.message || "Đăng ký thất bại";
      return;
    }

    openLogin?.();

  } catch (err) {
    logError("Register exception:", err);
    if (errorEl) errorEl.textContent = "Không kết nối được server";
  } finally {
    setLoading("register", false);
  }
}

/* =============================
        LOGOUT
============================= */
function logout() {
  log("➡️ logout() called");

  localStorage.removeItem(TOKEN_KEY);
  log("🧹 Token removed");

  document.getElementById("dropdownMenu")
    ?.style.setProperty("display", "none");

  updateUI?.();

  window.location.href = "/page/home";
}
