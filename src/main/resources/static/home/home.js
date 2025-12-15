const BASE_URL = window.location.origin;

const EXPENSE_API  = `${BASE_URL}/api/v1/user/expense`;
const BUDGET_API   = `${BASE_URL}/api/v1/user/budget`;
const CATEGORY_API = `${BASE_URL}/api/v1/user/categories`;
const EXPENSE_SUM_API = `${BASE_URL}/api/v1/user/expense/sum`;
const BUDGET_SUM_API  = `${BASE_URL}/api/v1/user/budget/sum`;
const STAT_YEAR_API = `${BASE_URL}/api/v1/user/expense/year`;



const token = localStorage.getItem("ACCESS_TOKEN");
let chartData = new Array(12).fill(0); // đơn vị: k


/* =============================
        AUTH UI CONTROL
============================= */
document.addEventListener("DOMContentLoaded", () => {
  if (localStorage.getItem("ACCESS_TOKEN")) {
    loadCategoriesForHome();
    refreshHomeStats();
  }
});

function toneFromRemainPercent(percent) {
  if (percent <= 0.10) return "bad";     // đỏ
  if (percent < 0.50)  return "warn";    // vàng
  return "ok";                           // xanh
}

/* ================= REFRESH HOME STATS ================= */
async function refreshHomeStats() {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (!token) return;

  try {
    /* ===== GỌI TỔNG CHI ===== */
    const expenseRes = await fetch(EXPENSE_SUM_API, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!expenseRes.ok) throw new Error("Expense sum error");

    const expenseJson = await expenseRes.json();
    const totalSpent = Number(expenseJson.data || 0);

    // 👉 tổng chi LUÔN xanh
    setStat(
      document.getElementById("spentValue"),
      totalSpent,
      "ok"
    );

    /* ===== GỌI TỔNG NGÂN SÁCH ===== */
    const budgetRes = await fetch(BUDGET_SUM_API, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!budgetRes.ok) throw new Error("Budget sum error");

    const budgetJson = await budgetRes.json();
    const totalBudget = Number(budgetJson.data || 0);

    // ❌ chưa có ngân sách
    if (totalBudget <= 0) {
      setStat(
        document.getElementById("remainValue"),
        totalBudget,
        "bad"
      );
      return;
    }

    /* ===== TÍNH CÒN LẠI ===== */
    const remain = totalBudget - totalSpent;
    const remainSafe = Math.max(remain, 0);
    const percentRemain = remainSafe / totalBudget;

    const tone = toneFromRemainPercent(percentRemain);

    setStat(
      document.getElementById("remainValue"),
      remainSafe,
      tone
    );

  } catch (err) {
    console.error(err);
    alert("❌ Không tải được thống kê");
  }
}


/* ==================================================================================*/

function openLogin() {
  document.getElementById("authOverlay").style.display = "flex";
  document.getElementById("loginModal").style.display = "block";
  document.getElementById("registerModal").style.display = "none";
}

function openRegister() {
  document.getElementById("authOverlay").style.display = "flex";
  document.getElementById("loginModal").style.display = "none";
  document.getElementById("registerModal").style.display = "block";
}

function closeAuth() {
  document.getElementById("authOverlay").style.display = "none";
}

/* =============================
   CLICK NGOÀI MODAL → ĐÓNG
============================= */
document.getElementById("authOverlay").addEventListener("click", function () {
  closeAuth();
});

/* Ngăn click trong modal làm đóng */
document.querySelectorAll(".auth-modal").forEach(modal => {
  modal.addEventListener("click", function (e) {
    e.stopPropagation();
  });
});

/* =============================
        LOGIN STATE (TOKEN BASED)
============================= */
function updateUI() {
  const token = localStorage.getItem("ACCESS_TOKEN");

  const userBox = document.getElementById("userBox");
  const guestBox = document.getElementById("guestBox");

  if (userBox) userBox.style.display = token ? "flex" : "none";
  if (guestBox) guestBox.style.display = token ? "none" : "flex";
}


// gọi khi load trang
updateUI();


/* =============================
        DROPDOWNS
============================= */
function toggleMenu() {
  const token = localStorage.getItem("ACCESS_TOKEN");

  // ❌ Chưa đăng nhập → mở login
  if (!token) {
    openLogin();
    return;
  }

  // ✅ Đã đăng nhập → toggle menu
  const menu = document.getElementById("dropdownMenu");
  if (!menu) return;

  menu.style.display = menu.style.display === "block" ? "none" : "block";
}


function toggleNotify() {
  const menu = document.getElementById("notifyMenu");
  menu.style.display = menu.style.display === "block" ? "none" : "block";
}

document.addEventListener("click", e => {
  if (!e.target.closest(".dropdown"))
    document.getElementById("dropdownMenu").style.display = "none";

  if (!e.target.closest(".notify-icon"))
    document.getElementById("notifyMenu").style.display = "none";
});

/* =============================
        RIPPLE EFFECT
============================= */
document.addEventListener("click", e => {
  if (e.target.classList.contains("btn") ||
      e.target.classList.contains("btn-primary") ||
      e.target.classList.contains("tab-btn")) {

    const rect = e.target.getBoundingClientRect();
    e.target.style.setProperty("--x", `${e.clientX - rect.left}px`);
    e.target.style.setProperty("--y", `${e.clientY - rect.top}px`);
  }
});

/* =============================
        CHART RESIZE
============================= */
function resizeCanvas() {
  const canvas = document.getElementById("chartCanvas");
  canvas.width = canvas.clientWidth;
  canvas.height = canvas.clientHeight;
}
window.onresize = resizeCanvas;

/* =============================
        API MOCK + UI BINDING
   (đổi sang fetch thật là xong)
============================= */
function formatVND(n) {
  const num = Number(n || 0);
  return num.toLocaleString("vi-VN") + " VND";
}

function setStat(el, value, tone /* ok|warn|bad */) {
  el.textContent = formatVND(value);
  el.classList.remove("stat-ok", "stat-warn", "stat-bad");
  if (tone === "bad") el.classList.add("stat-bad");
  else if (tone === "warn") el.classList.add("stat-warn");
  else el.classList.add("stat-ok");
}

/* Mock: tổng chi */
async function fetchSpentTotalMock() {
  // Ví dụ response API: { totalSpent: 100000 }
  return { totalSpent: 100000 };
}

/* Mock: còn lại ngân sách + mức cảnh báo dựa theo % còn lại */
async function fetchBudgetRemainMock() {
  // Ví dụ response API: { remaining: 250000, percentRemaining: 0.18 }
  return { remaining: 100000, percentRemaining: 0.35 };
}

function toneFromPercent(p) {
  if (p <= 0.15) return "bad";     // đỏ: sắp cạn
  if (p <= 0.35) return "warn";    // vàng: hơi căng
  return "ok";                      // xanh: ổn
}

async function refreshStats() {
  const spentEl = document.getElementById("spentValue");
  const remainEl = document.getElementById("remainValue");

  // Tổng chi: luôn xanh (như bạn yêu cầu)
  const spentRes = await fetchSpentTotalMock();
  setStat(spentEl, spentRes.totalSpent, "ok");

  // Còn lại: xanh/vàng/đỏ theo API
  const remainRes = await fetchBudgetRemainMock();
  const tone = toneFromPercent(remainRes.percentRemaining);
  setStat(remainEl, remainRes.remaining, tone);
}

/* Button demo: bạn thay bằng call API thật */
document.getElementById("btnAddExpense")?.addEventListener("click", async () => {
  // TODO: POST /expenses ...
  // Sau khi POST xong, refresh lại số liệu
  await refreshStats();
});

document.getElementById("btnSetBudget")?.addEventListener("click", async () => {
  // TODO: POST /budgets ...
  await refreshStats();
});

/* =============================
        ANIMATED CHART
============================= */
let anim = 0;

function startChartAnimation() {
  anim = 0;
  function step() {
    anim += 0.03;
    if (anim > 1) anim = 1;
    drawChart(anim);
    if (anim < 1) requestAnimationFrame(step);
  }
  requestAnimationFrame(step);
}

window.onload = async () => {
  resizeCanvas();
  startChartAnimation();
  await refreshStats();
};

/* =============================
            DRAW CHART
============================= */
function initYearSelect() {
  const select = document.getElementById("yearSelect");
  const currentYear = new Date().getFullYear();

  for (let y = currentYear; y >= currentYear - 5; y--) {
    const opt = document.createElement("option");
    opt.value = y;
    opt.textContent = y;
    select.appendChild(opt);
  }

  select.value = currentYear;

  select.addEventListener("change", () => {
    loadStatisticByYear(select.value);
  });

  loadStatisticByYear(currentYear);
}

async function loadStatisticByYear(year) {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (!token) return;

  try {
    const res = await fetch(`${STAT_YEAR_API}?year=${year}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!res.ok) {
      console.error("Không load được thống kê năm");
      return;
    }

    const json = await res.json();
    const rawData = json.data || [];

    // 🔥 QUY ĐỔI: 1000 = 1k (GIỮ SỐ ÂM)
    chartData = rawData.map(v => v / 1000);

    startChartAnimation();

  } catch (e) {
    console.error("Statistic year error", e);
  }
}

function drawChart(t = 1) {

  const canvas = document.getElementById("chartCanvas");
  const ctx = canvas.getContext("2d");

  const W = canvas.width;
  const H = canvas.height;

  ctx.clearRect(0, 0, W, H);

  // DATA
  let data = chartData;


  // TIME
  const now = new Date();
  let day = now.getDate();
  let month = now.getMonth() + 1;
  let daysInMonth = new Date(now.getFullYear(), month, 0).getDate();
  let ratio = day / daysInMonth;

  // LAYOUT
  const paddingLeft = 120;
  const paddingRight = 120;
  const totalMonths = 12;

  const chartWidth = W - paddingLeft - paddingRight;
  const cellW = chartWidth / totalMonths;

  const baseY = H - 55;
  const topMargin = 40;
  const usableH = baseY - topMargin;

  let maxValue = Math.max(...data);
  let scale = usableH * 0.9 / maxValue;

  /* GRID */
  ctx.strokeStyle = "rgba(0,0,0,0.07)";
  ctx.lineWidth = 1;
  for (let i = 0; i <= 5; i++) {
    let y = baseY - (usableH / 5) * i;
    ctx.beginPath();
    ctx.moveTo(paddingLeft, y);
    ctx.lineTo(W - paddingRight, y);
    ctx.stroke();
  }

  /* AXIS Y */
  ctx.lineWidth = 3;
  ctx.strokeStyle = "#000";
  ctx.beginPath();
  ctx.moveTo(60, baseY);
  ctx.lineTo(60, 20);
  ctx.stroke();

  ctx.textAlign = "right";
  ctx.font = "16px Segoe UI";
  ctx.fillText("Tổng", 52, 35);
  ctx.fillText("tiền", 52, 55);

  /* AXIS X */
  ctx.beginPath();
  ctx.moveTo(60, baseY);
  ctx.lineTo(W - 20, baseY);
  ctx.stroke();

  ctx.font = "16px Segoe UI";
  ctx.textAlign = "center";
  ctx.fillText("Tháng", W - 80, baseY + 32);

  /* TIME RED LINE */
  const cellStart = paddingLeft + (month - 1) * cellW;
  const redX = cellStart + cellW * ratio;

  ctx.strokeStyle = "red";
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(redX, 20);
  ctx.lineTo(redX, baseY);
  ctx.stroke();

  ctx.fillStyle = "red";
  ctx.fillText(`${day}/${month}/${now.getFullYear()}`, redX, 18);

  /* DRAW BARS */
  const barW = cellW * 0.55;

  for (let i = 0; i < totalMonths; i++) {

    const xCenter = paddingLeft + i * cellW + cellW / 2;

    ctx.font = "14px Segoe UI";
    ctx.fillStyle = "#000";
    ctx.fillText(i + 1, xCenter, baseY + 20);

    if (i >= month) continue;
    if (data[i] === 0) continue;

    let h = data[i] * scale * t;

    let grd = ctx.createLinearGradient(0, baseY - h, 0, baseY);
    grd.addColorStop(0, "#ffffff");
    grd.addColorStop(1, "#d6e4ff");

    ctx.shadowColor = "rgba(0,0,0,0.25)";
    ctx.shadowBlur = 4;

    // Tháng hiện tại -> cắt ngang
    if (i === month - 1 && day !== daysInMonth) {
      let left = xCenter - barW / 2;
      let maxRight = left + barW;
      let allowedRight = Math.min(redX, maxRight);
      let widthAllowed = Math.max(0, allowedRight - left);

      ctx.beginPath();
      ctx.rect(left, baseY - h, widthAllowed, h);
      ctx.fillStyle = grd;
      ctx.fill();
      ctx.strokeStyle = "#000";
      ctx.stroke();
    }
    else {
      // Tháng trước → full
      ctx.beginPath();
      ctx.rect(xCenter - barW / 2, baseY - h, barW, h);
      ctx.fillStyle = grd;
      ctx.fill();
      ctx.strokeStyle = "#000";
      ctx.stroke();
    }

    ctx.shadowBlur = 0;
    ctx.fillStyle = "#000";
    ctx.fillText(data[i] + "k", xCenter, baseY - h - 6);
  }
}

// MENU REDIRECT
document.addEventListener("click", function (e) {
  const target = e.target.closest("[data-href]");
  if (!target) return;

  const url = target.getAttribute("data-href");
  if (url) {
    window.location.href = url;
  }
});


/* =============================
   CHỈ ĐIỀU KHIỂN MÀU 2 NÚT MENU
============================= */
(function setMenuActiveColor() {
  const path = window.location.pathname;

  const tabHome = document.getElementById("tabHome");
  const tabFunc = document.getElementById("tabFunc");

  if (!tabHome || !tabFunc) return;

  // reset về trắng
  tabHome.classList.remove("active");
  tabFunc.classList.remove("active");

  // ĐANG Ở TRANG HOME
  if (path.includes("/page/home")) {
    tabHome.classList.add("active");
  }
  // ĐANG Ở CÁC TRANG CHỨC NĂNG
  else {
    tabFunc.classList.add("active");
  }
})();


function togglePassword(inputId, icon) {
  const input = document.getElementById(inputId);
  if (!input) return;

  if (input.type === "password") {
    input.type = "text";
    icon.textContent = "🙈";
  } else {
    input.type = "password";
    icon.textContent = "👁️";
  }
}

/* ================= LOAD CATEGORIES FOR SELECT ================= */
async function loadCategoriesForHome() {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (!token) return;

  try {
    const res = await fetch(CATEGORY_API, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!res.ok) {
      console.warn("Không load được danh mục");
      return;
    }

    const json = await res.json();
    const select = document.getElementById("expenseCategory");
    if (!select) return;

    // reset
    select.innerHTML = `<option value="">-- Chọn danh mục --</option>`;

    json.data.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.id;        // 🔑 dùng id
      opt.textContent = c.name;
      select.appendChild(opt);
    });

  } catch (err) {
    console.error("Load category error:", err);
  }
}

/* ================= CREATE EXPENSE & BUDGET ================= */
async function createExpense() {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (!token) {
    alert("Bạn chưa đăng nhập");
    return;
  }

  const amount = Number(document.getElementById("expenseAmount").value);
  const categoryId = document.getElementById("expenseCategory").value;
  const note = document.getElementById("expenseNote").value;

  if (!categoryId || !amount || amount <= 0) {
    alert("Vui lòng chọn danh mục và nhập số tiền hợp lệ");
    return;
  }

  const body = {
    categoryId,
    amount,
    note,
    spentAt: new Date().toISOString()
  };

  try {
    const res = await fetch(EXPENSE_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(body)
    });

    if (!res.ok) {
      alert("❌ Không thêm được khoản chi");
      return;
    }

    // reset form
    document.getElementById("expenseAmount").value = "";
    document.getElementById("expenseNote").value = "";
    document.getElementById("expenseCategory").value = "";

    await refreshHomeStats();
    alert("✅ Đã thêm khoản chi thành công");

  } catch (err) {
    console.error(err);
    alert("❌ Lỗi kết nối server");
  }
}

async function createBudget() {
  if (!token) {
    alert("Bạn chưa đăng nhập");
    return;
  }

  const limitAmount = Number(document.getElementById("budgetAmount").value);
  const startDate = document.getElementById("budgetStart").value;
  const endDate = document.getElementById("budgetEnd").value;

  if (!limitAmount || !startDate || !endDate) {
    alert("Vui lòng nhập đầy đủ thông tin ngân sách");
    return;
  }

  const body = {
    startDate,
    endDate,
    limitAmount
  };

  try {
    const res = await fetch(BUDGET_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(body)
    });

    if (!res.ok) {
      alert("❌ Không thiết lập được ngân sách");
      return;
    }

    await refreshHomeStats();
    alert("✅ Đã thiết lập ngân sách thành công");

  } catch (err) {
    console.error(err);
    alert("❌ Lỗi kết nối server");
  }
}

window.addEventListener("DOMContentLoaded", () => {
  resizeCanvas();
  initYearSelect();
});

