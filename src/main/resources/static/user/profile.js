/* ===== MOCK DATA (sau thay bằng API) ===== */
const profile = {
  fullName: "Nguyễn Văn A",
  username: "username",
  email: "example@email.com",
  phone: "0123456789",
  dob: "2002-10-10",
  gender: "Nam",
  address: "Hà Nội"
};

/* ===== LOAD DATA ===== */
function loadProfile() {
  fullName.innerText = profile.fullName;
  username.innerText = "@" + profile.username;
  email.innerText = profile.email;
  phone.innerText = profile.phone;
  dob.innerText = profile.dob;
  gender.innerText = profile.gender;
  address.innerText = profile.address;
}
loadProfile();

/* ===== MODAL ===== */
function openEdit() {
  editModal.style.display = "flex";

  editFullName.value = profile.fullName;
  editPhone.value = profile.phone;
  editDob.value = profile.dob;
  editGender.value = profile.gender;
  editAddress.value = profile.address;
}

function openPassword() {
  passwordModal.style.display = "flex";
}

function closeModal() {
  editModal.style.display = "none";
  passwordModal.style.display = "none";
}

/* ===== SAVE PROFILE ===== */
function saveProfile() {
  profile.fullName = editFullName.value;
  profile.phone = editPhone.value;
  profile.dob = editDob.value;
  profile.gender = editGender.value;
  profile.address = editAddress.value;

  loadProfile();
  closeModal();
  alert("✅ Cập nhật thông tin thành công");
}

/* ===== CHANGE PASSWORD ===== */
function changePassword() {
  if (newPassword.value !== confirmPassword.value) {
    alert("❌ Mật khẩu xác nhận không khớp");
    return;
  }

  closeModal();
  alert("🔒 Đổi mật khẩu thành công");
}
