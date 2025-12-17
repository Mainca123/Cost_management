const API_ME = "/api/v1/user/me";
const token = localStorage.getItem("ACCESS_TOKEN");

/* ===== FIX CỨNG CÁC TRƯỜNG CHƯA CÓ API ===== */
const fixedProfile = {
  phone: "0123456789",
  dob: "2002-10-10",
  gender: "Nam",
  address: "Hà Nội"
};

/* ===== LOAD PROFILE FROM API ===== */
async function loadProfile() {
  if (!token) return;

  try {
    const res = await fetch(API_ME, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!res.ok) throw new Error("Unauthorized");

    const json = await res.json();
    const user = json.data;

    // ===== API DATA =====
    fullName.innerText = user.fullName || user.username;
    username.innerText = "@" + user.username;
    email.innerText = user.email;

    // ===== FIX CỨNG =====
    phone.innerText = fixedProfile.phone;
    dob.innerText = fixedProfile.dob;
    gender.innerText = fixedProfile.gender;
    address.innerText = fixedProfile.address;

  } catch (e) {
    console.error("Load profile error:", e);
  }
}

loadProfile();

/* ===== MODAL ===== */
function openEdit() {
  editModal.style.display = "flex";

  editFullName.value = fullName.innerText;
  editPhone.value = fixedProfile.phone;
  editDob.value = fixedProfile.dob;
  editGender.value = fixedProfile.gender;
  editAddress.value = fixedProfile.address;
}

function openPassword() {
  passwordModal.style.display = "flex";
}

function closeModal() {
  editModal.style.display = "none";
  passwordModal.style.display = "none";
}

/* ===== SAVE PROFILE (HIỆN TẠI CHỈ UPDATE UI) ===== */
function saveProfile() {
  // API update sẽ làm sau
  fullName.innerText = editFullName.value;

  fixedProfile.phone = editPhone.value;
  fixedProfile.dob = editDob.value;
  fixedProfile.gender = editGender.value;
  fixedProfile.address = editAddress.value;

  phone.innerText = fixedProfile.phone;
  dob.innerText = fixedProfile.dob;
  gender.innerText = fixedProfile.gender;
  address.innerText = fixedProfile.address;

  closeModal();
  alert("✅ Cập nhật thông tin thành công (local)");
}

/* ===== CHANGE PASSWORD (TẠM UI) ===== */
function changePassword() {
  if (newPassword.value !== confirmPassword.value) {
    alert("❌ Mật khẩu xác nhận không khớp");
    return;
  }

  closeModal();
  alert("🔒 Đổi mật khẩu thành công (demo)");
}
