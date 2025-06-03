# 📚 Book Market - Java Bookstore Application

## 📝 Mô tả dự án

**Book Market** là một ứng dụng siêu thị bán sách được xây dựng bằng **Java**. Ứng dụng cho phép người dùng đăng nhập, quản lý sách, thể loại, tài khoản, hóa đơn, giỏ hàng và thực hiện thống kê doanh thu.

## 👥 Thành viên dự án

| Vai trò | Họ và tên |
|---------|-----------|
| **Trưởng nhóm** | Nguyễn Quang Tùng |
| **Thành viên 1** | Vương Đình Hiến |
| **Thành viên 2** | *(Đang cập nhật)* |

**⏱ Thời gian thực hiện:** Từ **05/03/2024** đến **13/04/2024**

## 📁 Cấu trúc thư mục chính

```
nguyenquangtung004-bookmarketfphn/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/.../Book_Market/     → Code Java chia theo mô hình MVC
│   │   │   ├── res/layout/               → Giao diện XML
│   │   │   ├── res/drawable/             → Icon, background XML
│   │   │   ├── res/values/               → Strings, colors, theme
│   │   │   └── AndroidManifest.xml
├── gradle/
├── build.gradle
├── README.md
└── ...
```

## ⚙️ Quy tắc đặt tên & làm việc trong dự án

### 1. 🛠️ Quy trình làm việc với Git

- **Bắt buộc chia nhánh** trước khi thực hiện code để tránh lỗi bất đồng bộ
- **Commit đầy đủ** những phần đã cập nhật, nêu rõ nội dung trong message

### 2. 📐 Quy tắc đặt tên

> Đặt theo chuẩn để dễ đọc code

#### ➤ Class:
- Dùng **PascalCase**
- *Ví dụ:* `DangKi`, `MainActivity`, `ThongKeDoanhThu`

#### ➤ ID thuộc tính XML (TextView, Button, ...)
- Dùng **snake_case**

**TextView:**
```
tv_<layout>_<đối_tượng>_<số_lần nếu có>
Ví dụ: tv_dn_ttk hoặc tv_dn_ttk_1
```

**Button:**
```
btn_<layout>_<mục_đích>_<số_lần nếu có>
Ví dụ: btn_dn hoặc btn_dn_1
```

#### ➤ Các biến còn lại:
- Dùng **camelCase**
- *Ví dụ:* `tenTaiKhoan`, `soLuongSach`, `giaBan`

## 🎯 Mục tiêu & Lưu ý

- Dự án tuân thủ mô hình **MVC**: chia rõ `Model`, `View`, `Controller/DAO`
- Tên biến, layout, component phải **rõ ràng**, **thống nhất** để dễ bảo trì
- **TẤT CẢ THÀNH VIÊN** bắt buộc phải đọc và hiểu rõ file README này trước khi bắt đầu hoặc tiếp tục code

## 💬 Liên hệ nhóm

Nếu có bất kỳ thắc mắc hay góp ý, hãy liên hệ trưởng nhóm:

📧 **Nguyễn Quang Tùng** – `nguyenquangtung004@...`

---

## ✅ Lưu ý quan trọng

**Hãy commit đúng chuẩn – review kỹ – tôn trọng thời gian của cả nhóm!**
