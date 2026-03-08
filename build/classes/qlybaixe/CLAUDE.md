# 🏟️ CLAUDE.MD – TÀI LIỆU DỰ ÁN
## Hệ Thống Quản Lý Đặt Lịch Sân Thể Thao

> **⚡ ĐỌC FILE NÀY TRƯỚC KHI CODE BẤT KỲ THỨ GÌ**
> File này là kim chỉ nam của toàn bộ dự án. Mỗi khi bắt đầu một phiên làm việc mới, đọc lại từ đầu để nhớ context, tránh code sai luồng.

---

## 📌 1. THÔNG TIN DỰ ÁN

| Mục | Chi tiết |
|---|---|
| **Tên dự án** | Quản Lý Đặt Lịch Sân Thể Thao |
| **Môn học** | Lập trình Java |
| **Framework** | Spring Boot 3.x |
| **Ngôn ngữ** | Java 17 |
| **Database** | MySQL 8.x |
| **ORM** | Spring Data JPA + Hibernate |
| **Giao diện** | Thymeleaf (server-side rendering) |
| **Bảo mật** | Spring Security (form login) |
| **Build tool** | Maven |
| **Loại app** | Web app nội bộ, chạy offline tại quầy lễ tân |

---

## 🧠 2. HIỂU RÕ BÀI TOÁN (ĐỌC KỸ)

### Bối cảnh thực tế
Đây là phần mềm dùng **tại quầy lễ tân** của một cơ sở thể thao. Khi khách đến hoặc gọi điện đặt sân, **nhân viên** sẽ mở phần mềm, nhập thông tin và tạo đơn đặt sân thay cho khách.

### Quy tắc cốt lõi – KHÔNG ĐƯỢC VI PHẠM

```
✅ Chỉ có 2 loại tài khoản: ADMIN và NHÂN VIÊN
✅ Khách hàng KHÔNG có tài khoản, KHÔNG đăng nhập
✅ Thông tin khách (tên + SĐT) được nhập thủ công vào đơn đặt sân
✅ Thanh toán = tiền mặt, nhân viên xác nhận thủ công
✅ Hệ thống phải chống trùng lịch (cùng sân + cùng giờ = không cho đặt)
```

### Luồng hoạt động chính
```
[Khách đến quầy / gọi điện]
         ↓
[Nhân viên đăng nhập hệ thống]
         ↓
[Chọn sân → Chọn ngày → Xem giờ trống]
         ↓
[Nhập: Tên khách + SĐT + Giờ bắt đầu + Giờ kết thúc]
         ↓
[Hệ thống tính tiền = Giá/giờ × Số giờ]
         ↓
[Lưu đơn → Trạng thái: "Chờ thanh toán"]
         ↓
[Khách trả tiền mặt → Nhân viên bấm "Xác nhận thanh toán"]
         ↓
[Đơn chuyển sang: "Đã thanh toán"]
```

---

## 👥 3. PHÂN QUYỀN

### Vai trò trong hệ thống

| Vai trò | Enum | Mô tả |
|---|---|---|
| Admin | `ROLE_ADMIN` | Toàn quyền hệ thống |
| Nhân viên | `ROLE_STAFF` | Đặt lịch, thu tiền, xem báo cáo giới hạn |

### Ma trận quyền truy cập

| Chức năng | Admin | Nhân viên |
|---|---|---|
| Đăng nhập / Đăng xuất | ✅ | ✅ |
| Xem danh sách sân | ✅ | ✅ |
| Thêm / Sửa / Xóa sân | ✅ | ❌ |
| Tạo đơn đặt sân | ✅ | ✅ |
| Sửa / Hủy đơn | ✅ | ✅ (chỉ đơn chưa TT) |
| Xác nhận thanh toán | ✅ | ✅ |
| Xem lịch sử đặt sân | ✅ | ✅ (chỉ của mình) |
| Quản lý nhân viên | ✅ | ❌ |
| Xem báo cáo doanh thu | ✅ | ❌ |
| Thống kê tổng quan | ✅ | ❌ |

---

## 🗂️ 4. CÁC MODULE VÀ CHỨC NĂNG

---

### MODULE 1 – ĐĂNG NHẬP / XÁC THỰC

**Mục tiêu:** Kiểm soát ai được vào hệ thống và với quyền gì.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M1.1 | Đăng nhập | Form nhập username + password, Spring Security xác thực, redirect theo role | Admin + NV |
| M1.2 | Đăng xuất | Xóa session, về trang login | Admin + NV |
| M1.3 | Đổi mật khẩu | Nhập mật khẩu cũ, mới, xác nhận mới | Admin + NV |
| M1.4 | Phân quyền tự động | Sau login: Admin → `/admin/dashboard`, NV → `/staff/dashboard` | Hệ thống |
| M1.5 | Bảo vệ URL | Mọi URL cần đăng nhập, sai role → trang 403 | Spring Security |

**URL mapping:**
```
GET  /login           → Trang đăng nhập
POST /login           → Xử lý đăng nhập (Spring Security tự xử lý)
POST /logout          → Đăng xuất
GET  /change-password → Form đổi mật khẩu
POST /change-password → Xử lý đổi mật khẩu
```

---

### MODULE 2 – QUẢN LÝ SÂN

**Mục tiêu:** Admin duy trì danh sách sân. Nhân viên chỉ xem để chọn khi đặt lịch.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M2.1 | Xem danh sách sân | Bảng hiển thị tất cả sân, lọc theo loại/trạng thái | Admin + NV |
| M2.2 | Xem chi tiết sân | Thông tin đầy đủ + lịch đặt trong ngày hiện tại | Admin + NV |
| M2.3 | Thêm sân mới | Form nhập: tên, loại, mô tả, giá/giờ, trạng thái | Admin |
| M2.4 | Sửa thông tin sân | Cập nhật bất kỳ trường nào | Admin |
| M2.5 | Xóa sân | Chỉ xóa được khi không có đơn đang hoạt động | Admin |
| M2.6 | Đổi trạng thái nhanh | Toggle: Hoạt động ↔ Bảo trì (không cần vào form sửa) | Admin |

**Loại sân (CourtType enum):**
```java
BONG_DA, CAU_LONG, TENNIS, BONG_RO, BONG_CHUYEN, KHAC
```

**Trạng thái sân (CourtStatus enum):**
```java
HOAT_DONG, BAO_TRI, TAM_DONG
```

**URL mapping:**
```
GET  /admin/courts              → Danh sách sân
GET  /admin/courts/{id}         → Chi tiết sân
GET  /admin/courts/new          → Form thêm sân
POST /admin/courts/new          → Lưu sân mới
GET  /admin/courts/{id}/edit    → Form sửa sân
POST /admin/courts/{id}/edit    → Lưu sửa sân
POST /admin/courts/{id}/delete  → Xóa sân
POST /admin/courts/{id}/toggle  → Đổi trạng thái nhanh
```

---

### MODULE 3 – ĐẶT LỊCH SÂN

**Mục tiêu:** Nhân viên tạo đơn đặt sân cho khách. Đây là module trung tâm của hệ thống.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M3.1 | Xem lịch sân theo ngày | Chọn sân + ngày → hiển thị timeline khung giờ đã đặt / còn trống | Admin + NV |
| M3.2 | Tạo đơn đặt sân | Chọn sân, ngày, giờ; nhập tên khách + SĐT; hệ thống tự tính tiền | Admin + NV |
| M3.3 | Kiểm tra trùng lịch | Validate backend: không cho trùng sân + ngày + giờ | Hệ thống |
| M3.4 | Xem chi tiết đơn | Xem toàn bộ thông tin 1 đơn đặt | Admin + NV |
| M3.5 | Sửa đơn | Sửa giờ / tên / SĐT khi đơn còn ở trạng thái "Chờ thanh toán" | Admin + NV |
| M3.6 | Hủy đơn | Chuyển trạng thái → HUY, sân được giải phóng | Admin + NV |
| M3.7 | Hoàn thành đơn | Admin đánh dấu hoàn thành sau khi khách chơi xong | Admin |

**Trạng thái đơn (BookingStatus enum):**
```java
CHO_THANH_TOAN,   // Vừa tạo, chưa thu tiền
DA_THANH_TOAN,    // Đã thu tiền mặt
DA_HUY,           // Bị hủy
HOAN_THANH        // Khách đã chơi xong
```

**Logic chống trùng lịch (QUAN TRỌNG):**
```
Điều kiện xung đột = cùng court_id + cùng booking_date
+ (giờ bắt đầu mới < giờ kết thúc cũ) VÀ (giờ kết thúc mới > giờ bắt đầu cũ)
+ trạng thái khác DA_HUY
```

**URL mapping:**
```
GET  /bookings                    → Danh sách đơn hôm nay
GET  /bookings/schedule           → Xem lịch sân theo ngày
GET  /bookings/new                → Form tạo đơn
POST /bookings/new                → Lưu đơn mới
GET  /bookings/{id}               → Chi tiết đơn
GET  /bookings/{id}/edit          → Form sửa đơn
POST /bookings/{id}/edit          → Lưu sửa đơn
POST /bookings/{id}/cancel        → Hủy đơn
POST /bookings/{id}/complete      → Hoàn thành đơn
```

---

### MODULE 4 – QUẢN LÝ NHÂN VIÊN

**Mục tiêu:** Admin tạo và quản lý tài khoản nhân viên. Nhân viên không tự đăng ký.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M4.1 | Xem danh sách NV | Bảng tất cả nhân viên, lọc theo trạng thái | Admin |
| M4.2 | Thêm nhân viên | Tạo tài khoản: nhập họ tên, SĐT, email, username, mật khẩu mặc định | Admin |
| M4.3 | Xem chi tiết NV | Thông tin + lịch sử đơn NV đó đã tạo | Admin |
| M4.4 | Sửa thông tin NV | Cập nhật họ tên, SĐT, email | Admin |
| M4.5 | Reset mật khẩu | Đặt lại mật khẩu mặc định cho NV quên mật khẩu | Admin |
| M4.6 | Khóa / Mở tài khoản | Vô hiệu hóa NV nghỉ việc, không cho đăng nhập | Admin |
| M4.7 | Xóa nhân viên | Xóa tài khoản (chỉ khi NV chưa có đơn nào) | Admin |

**URL mapping:**
```
GET  /admin/staff              → Danh sách nhân viên
GET  /admin/staff/new          → Form thêm nhân viên
POST /admin/staff/new          → Lưu nhân viên mới
GET  /admin/staff/{id}         → Chi tiết nhân viên
GET  /admin/staff/{id}/edit    → Form sửa nhân viên
POST /admin/staff/{id}/edit    → Lưu sửa nhân viên
POST /admin/staff/{id}/reset   → Reset mật khẩu
POST /admin/staff/{id}/toggle  → Khóa / Mở tài khoản
POST /admin/staff/{id}/delete  → Xóa nhân viên
```

---

### MODULE 5 – THANH TOÁN

**Mục tiêu:** Ghi nhận thu tiền mặt từ khách, cập nhật trạng thái đơn.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M5.1 | Tính tiền tự động | Khi tạo đơn: `total = price_per_hour × duration_hours` | Hệ thống |
| M5.2 | Xác nhận đã thu tiền | NV bấm "Thu tiền" → đơn chuyển "Đã thanh toán", ghi thời điểm thu | Admin + NV |
| M5.3 | Xem hóa đơn | Chi tiết đơn: tên khách, SĐT, sân, ngày, giờ, số tiền, NV thu | Admin + NV |
| M5.4 | In hóa đơn | Trang print-friendly để in cho khách | Admin + NV |

**URL mapping:**
```
POST /bookings/{id}/pay       → Xác nhận thanh toán
GET  /bookings/{id}/invoice   → Xem hóa đơn
GET  /bookings/{id}/print     → In hóa đơn
```

---

### MODULE 6 – LỊCH SỬ & TRA CỨU

**Mục tiêu:** Cho phép tra cứu lại mọi đơn đặt sân theo nhiều tiêu chí.

| Mã | Chức năng | Mô tả | Quyền |
|---|---|---|---|
| M6.1 | Xem tất cả lịch sử | Bảng toàn bộ đơn, mới nhất lên đầu | Admin |
| M6.2 | Lịch sử của tôi | NV chỉ xem đơn do mình tạo | NV |
| M6.3 | Tìm theo tên / SĐT khách | Tìm kiếm full-text | Admin + NV |
| M6.4 | Lọc theo ngày | Từ ngày → đến ngày | Admin + NV |
| M6.5 | Lọc theo sân | Xem lịch sử 1 sân cụ thể | Admin + NV |
| M6.6 | Lọc theo trạng thái | Chờ TT / Đã TT / Đã hủy / Hoàn thành | Admin + NV |
| M6.7 | Lọc theo nhân viên | Xem đơn của 1 NV cụ thể | Admin |

**URL mapping:**
```
GET /admin/history             → Lịch sử toàn bộ (Admin)
GET /staff/history             → Lịch sử của tôi (NV)
GET /admin/history?search=...  → Tìm kiếm + lọc
```

---

### MODULE 7 – THỐNG KÊ & BÁO CÁO (Admin only)

**Mục tiêu:** Admin nắm bắt tình hình kinh doanh.

| Mã | Chức năng | Mô tả |
|---|---|---|
| M7.1 | Dashboard tổng quan | Tổng đơn hôm nay, doanh thu hôm nay, số sân đang bận |
| M7.2 | Doanh thu theo ngày | Tổng tiền thu được từng ngày trong tháng |
| M7.3 | Doanh thu theo tháng | So sánh doanh thu các tháng trong năm |
| M7.4 | Sân hot nhất | Xếp hạng sân theo số lượt đặt |
| M7.5 | Khung giờ cao điểm | Khung giờ nào được đặt nhiều nhất |
| M7.6 | Tỉ lệ hủy đơn | Tổng đơn hủy / Tổng đơn tạo × 100% |

**URL mapping:**
```
GET /admin/dashboard           → Dashboard
GET /admin/reports/revenue     → Báo cáo doanh thu
GET /admin/reports/courts      → Thống kê theo sân
```

---

## 🏗️ 5. KIẾN TRÚC PROJECT

### Cấu trúc thư mục
```
sports-booking/
├── src/main/java/com/sportsbooking/
│   ├── SportsBookingApplication.java     ← Main class
│   │
│   ├── entity/                           ← JavaBean / Entity
│   │   ├── User.java                     ← Tài khoản hệ thống
│   │   ├── Court.java                    ← Sân thể thao
│   │   ├── Booking.java                  ← Đơn đặt sân
│   │   └── Payment.java                  ← Thanh toán
│   │
│   ├── enums/                            ← Enum constants
│   │   ├── Role.java                     ← ADMIN, STAFF
│   │   ├── CourtType.java                ← BONG_DA, CAU_LONG...
│   │   ├── CourtStatus.java              ← HOAT_DONG, BAO_TRI...
│   │   └── BookingStatus.java            ← CHO_TT, DA_TT...
│   │
│   ├── repository/                       ← Truy vấn JPA
│   │   ├── UserRepository.java
│   │   ├── CourtRepository.java
│   │   ├── BookingRepository.java
│   │   └── PaymentRepository.java
│   │
│   ├── service/                          ← Logic nghiệp vụ
│   │   ├── UserService.java
│   │   ├── CourtService.java
│   │   ├── BookingService.java           ← Có logic chống trùng lịch
│   │   ├── PaymentService.java
│   │   └── ReportService.java
│   │
│   ├── controller/                       ← Xử lý HTTP request
│   │   ├── AuthController.java
│   │   ├── AdminCourtController.java
│   │   ├── BookingController.java
│   │   ├── AdminStaffController.java
│   │   ├── PaymentController.java
│   │   └── ReportController.java
│   │
│   ├── dto/                              ← Form data / response objects
│   │   ├── BookingForm.java
│   │   ├── CourtForm.java
│   │   └── StaffForm.java
│   │
│   └── config/
│       └── SecurityConfig.java           ← Cấu hình Spring Security
│
├── src/main/resources/
│   ├── templates/                        ← Thymeleaf HTML
│   │   ├── auth/
│   │   │   └── login.html
│   │   ├── admin/
│   │   │   ├── dashboard.html
│   │   │   ├── courts/
│   │   │   ├── staff/
│   │   │   └── reports/
│   │   ├── booking/
│   │   └── layout/
│   │       └── base.html                 ← Template layout chung
│   │
│   ├── static/
│   │   ├── css/
│   │   └── js/
│   │
│   └── application.properties
│
└── pom.xml
```

### Dependencies cần có trong pom.xml
```xml
spring-boot-starter-web
spring-boot-starter-thymeleaf
spring-boot-starter-data-jpa
spring-boot-starter-security
thymeleaf-extras-springsecurity6
mysql-connector-j
spring-boot-starter-validation
lombok
```

---

## ⚙️ 6. CẤU HÌNH APPLICATION.PROPERTIES

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/sports_booking
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.encoding=UTF-8

# Server
server.port=8080
```

---

## 📏 7. QUY TẮC CODE (ĐỌC TRƯỚC KHI VIẾT CODE)

```
1. Entity      → chỉ chứa field + getter/setter (dùng @Data của Lombok)
2. Repository  → chỉ chứa query (extends JpaRepository)
3. Service     → chứa toàn bộ logic nghiệp vụ, KHÔNG viết query SQL ở đây
4. Controller  → chỉ nhận request, gọi service, trả về view (KHÔNG viết logic)
5. Tên biến    → tiếng Anh, camelCase
6. Tên bảng DB → tiếng Anh, snake_case (users, courts, bookings, payments)
7. Mọi thao tác DB → qua Repository, KHÔNG dùng EntityManager trực tiếp
8. Validate    → dùng @Valid + BindingResult ở Controller
9. Mật khẩu   → LUÔN mã hóa bằng BCryptPasswordEncoder, KHÔNG lưu plain text
```

---

## 🔄 8. THỨ TỰ CODE ĐỀ XUẤT

```
Bước 1: Tạo project Spring Boot + thêm dependencies vào pom.xml
Bước 2: Cấu hình application.properties (kết nối DB)
Bước 3: Viết tất cả Entity (User, Court, Booking, Payment)
Bước 4: Viết tất cả Repository
Bước 5: Cấu hình Spring Security (SecurityConfig.java)
Bước 6: Viết AuthController + trang login.html
Bước 7: Viết CourtService + CourtController + giao diện sân
Bước 8: Viết BookingService (có logic chống trùng) + BookingController
Bước 9: Viết PaymentService + PaymentController
Bước 10: Viết StaffController (Admin quản lý NV)
Bước 11: Viết ReportService + Dashboard
Bước 12: Hoàn thiện giao diện + test toàn bộ luồng
```

---

## 🧪 9. CÁC TRƯỜNG HỢP CẦN TEST

```
✅ Đăng nhập đúng với role Admin → vào /admin/dashboard
✅ Đăng nhập đúng với role Staff → vào /staff/dashboard  
✅ Staff truy cập /admin/... → bị chặn (403)
✅ Tạo đơn đặt sân thành công
✅ Tạo đơn trùng giờ → báo lỗi, không cho lưu
✅ Hủy đơn → sân giải phóng, có thể đặt lại
✅ Xác nhận thanh toán → trạng thái chuyển đúng
✅ Admin xóa sân có đơn đang hoạt động → báo lỗi
✅ Khóa tài khoản NV → NV không đăng nhập được
```

---

*📅 Cập nhật lần cuối: 2024 | 👨‍💻 Dự án bài tập môn Lập trình Java*
