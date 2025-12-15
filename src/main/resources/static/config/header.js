/* =============================
        AUTH STATE
============================= */
function updateUI() {
  const token = localStorage.getItem("ACCESS_TOKEN");

  const userBox = document.getElementById("userBox");
  const guestBox = document.getElementById("guestBox");

  if (userBox) userBox.style.display = token ? "flex" : "none";
  if (guestBox) guestBox.style.display = token ? "none" : "flex";
}

/* =============================
        LOGOUT
============================= */
function logout() {
  localStorage.removeItem("ACCESS_TOKEN");
  window.location.href = "../home/home.html";
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

updateUI();
