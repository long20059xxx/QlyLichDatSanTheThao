# 📁 CẤU TRÚC PACKAGE – NetBeans Java Project
## Quản Lý Đặt Lịch Sân Thể Thao

---

## 🗂️ SOURCE PACKAGES

```
qlybaixe/                               ← Package gốc
│
├── login/
│   └── Login.java                      ← Màn hình đăng nhập
│
├── dashboard/
│   └── Dashboard.java                  ← Tổng quan hệ thống
│
├── datlich/
│   ├── DatLich.java                    ← Danh sách đặt lịch
│   └── TaoDon.java                     ← Form tạo đơn mới
│
├── lichsan/
│   └── LichSan.java                    ← Lịch sân theo ngày (timeline)
│
├── quanlysân/
│   ├── QuanLySan.java                  ← Danh sách sân (dạng card)
│   └── ThemSan.java                    ← Form thêm / sửa sân
│
├── quanlynhanvien/
│   ├── QuanLyNhanVien.java             ← Danh sách nhân viên
│   └── ThemNhanVien.java               ← Form thêm / sửa nhân viên
│
├── baocao/
│   └── BaoCaoDoanhThu.java             ← Báo cáo doanh thu
│
├── lichsu/
│   └── LichSuDatSan.java               ← Lịch sử đặt sân
│
├── model/                              ← JavaBean (ánh xạ DB)
│   ├── User.java
│   ├── Court.java
│   ├── Booking.java
│   └── Payment.java
│
├── dao/                                ← Truy vấn database (JDBC)
│   ├── UserDAO.java
│   ├── CourtDAO.java
│   ├── BookingDAO.java
│   └── PaymentDAO.java
│
└── util/
    ├── ConnectDB.java                  ← Kết nối MySQL (bạn đã có)
    └── Helper.java                     ← Hàm dùng chung (format tiền, ngày...)
```

---

## 🔗 QUAN HỆ CÁC LỚP

```
Màn hình (JFrame)
      ↓
    DAO          ← Gọi truy vấn SQL
      ↓
 ConnectDB       ← Kết nối MySQL
      ↓
  Database       ← 4 bảng: users, courts, bookings, payments
```

---

## 📋 MỖI FORM TƯƠNG ỨNG VỚI GÌ

| Package | File Java | Màn hình Figma |
|---|---|---|
| `login` | Login.java | Đăng nhập |
| `dashboard` | Dashboard.java | Tổng quan |
| `datlich` | DatLich.java | Quản lý đặt sân |
| `datlich` | TaoDon.java | Tạo đơn mới |
| `lichsan` | LichSan.java | Lịch sân theo ngày |
| `quanlysân` | QuanLySan.java | Quản lý sân |
| `quanlysân` | ThemSan.java | Thêm / sửa sân |
| `quanlynhanvien` | QuanLyNhanVien.java | Quản lý nhân viên |
| `quanlynhanvien` | ThemNhanVien.java | Thêm nhân viên |
| `baocao` | BaoCaoDoanhThu.java | Báo cáo doanh thu |
| `lichsu` | LichSuDatSan.java | Lịch sử đặt sân |

---

## 💡 GHI NHỚ

```
✅ Mỗi màn hình Figma = 1 file .java (JFrame Form)
✅ model/   → chỉ chứa get/set, không có logic
✅ dao/     → chỉ chứa SQL, không có giao diện
✅ util/    → ConnectDB.java bạn đã có → chuyển vào đây
```

---

*Database → xem sports_booking.sql | Chi tiết → xem CLAUDE.md*
