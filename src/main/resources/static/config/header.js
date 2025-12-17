/* =============================
        AUTH STATE
============================= */
function updateUI(isLoggedIn) {
  const userBox = document.getElementById("userBox");
  const guestBox = document.getElementById("guestBox");

  if (userBox) userBox.style.display = isLoggedIn ? "flex" : "none";
  if (guestBox) guestBox.style.display = isLoggedIn ? "none" : "flex";
}

/* =============================
        LOGOUT
============================= */
function logout() {
  localStorage.removeItem("ACCESS_TOKEN");
  window.location.href = "/page/home";
}

/* =============================
        DROPDOWN MENU
============================= */
function toggleMenu() {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (!token) {
    openLogin?.();
    return;
  }

  const menu = document.getElementById("dropdownMenu");
  if (!menu) return;

  menu.style.display = menu.style.display === "block" ? "none" : "block";
}

function toggleNotify() {
  const menu = document.getElementById("notifyMenu");
  if (!menu) return;
  menu.style.display = menu.style.display === "block" ? "none" : "block";
}

/* =============================
        CLICK OUTSIDE
============================= */
document.addEventListener("click", e => {
  if (!e.target.closest(".dropdown")) {
    const menu = document.getElementById("dropdownMenu");
    if (menu) menu.style.display = "none";
  }
});

/* =============================
        MENU REDIRECT
============================= */
document.addEventListener("click", e => {
  const target = e.target.closest("[data-href]");
  if (!target) return;

  window.location.href = target.getAttribute("data-href");
});

/* =============================
        ACTIVE TAB
============================= */
(function setMenuActiveColor() {
  const path = window.location.pathname;

  const tabHome = document.getElementById("tabHome");
  const tabFunc = document.getElementById("tabFunc");

  if (!tabHome || !tabFunc) return;

  tabHome.classList.remove("active");
  tabFunc.classList.remove("active");

  if (path.includes("/home/")) {
    tabHome.classList.add("active");
  } else {
    tabFunc.classList.add("active");
  }
})();

/* =============================
        LOAD USER INFO
============================= */
async function loadUserInfo() {
  const token = localStorage.getItem("ACCESS_TOKEN");

  if (!token) {
    updateUI(false);
    return;
  }

  try {
    const res = await fetch("/api/v1/user/me", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!res.ok) throw new Error("Unauthorized");

    const json = await res.json();
    const user = json.data;

    updateUI(true);

    // Ưu tiên fullName, fallback username
    const usernameEl = document.getElementById("usernameText");
    if (usernameEl) {
      usernameEl.innerText = user.fullName || user.username;
    }

  } catch (e) {
    // Token sai / hết hạn
    localStorage.removeItem("ACCESS_TOKEN");
    updateUI(false);
  }
}

/* =============================
        INIT
============================= */
document.addEventListener("DOMContentLoaded", () => {
  loadUserInfo();
});
