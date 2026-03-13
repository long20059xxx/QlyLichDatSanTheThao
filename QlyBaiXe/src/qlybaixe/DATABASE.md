# 🗄️ DATABASE.MD – THIẾT KẾ CƠ SỞ DỮ LIỆU
## Hệ Thống Quản Lý Đặt Lịch Sân Thể Thao

> **⚡ ĐỌC FILE NÀY KHI:** Tạo Entity, viết Repository query, hoặc cần hiểu quan hệ giữa các bảng.

---

## 📌 THÔNG TIN DATABASE

| Mục | Chi tiết |
|---|---|
| **Tên database** | `sports_booking` |
| **Hệ quản trị** | MySQL 8.x |
| **Charset** | `utf8mb4` |
| **Collation** | `utf8mb4_unicode_ci` |
| **Tổng số bảng** | 4 bảng chính |

---

## 🗺️ SƠ ĐỒ QUAN HỆ (ERD – Mô tả bằng chữ)

```
users ──────────────────────────────────────────────────────────┐
  │ id (PK)                                                      │
  │                                                              │
  │ 1                                                            │
  │ │                                                            │
  │ N                                                            │
  ▼                                                              │
bookings ──────────────────────────────── payments              │
  │ id (PK)              booking.id → payments.booking_id (1:1) │
  │ court_id (FK)        booking.created_by → users.id (N:1) ───┘
  │ created_by (FK)
  │
  │ N
  │ │
  │ 1
  ▼
courts
  id (PK)


Quan hệ tóm tắt:
  users     1 ──── N  bookings   (1 NV tạo nhiều đơn)
  courts    1 ──── N  bookings   (1 sân có nhiều đơn)
  bookings  1 ──── 1  payments   (1 đơn có 1 thanh toán)
```

---

## 📋 BẢNG 1: `users` – Tài khoản hệ thống

> Lưu thông tin Admin và Nhân viên. **KHÔNG lưu khách hàng ở đây.**

### Cấu trúc bảng

| Cột | Kiểu dữ liệu | Null | Default | Mô tả |
|---|---|---|---|---|
| `id` | BIGINT | NO | AUTO_INCREMENT | Khóa chính |
| `full_name` | VARCHAR(100) | NO | | Họ và tên |
| `username` | VARCHAR(50) | NO | | Tên đăng nhập (unique) |
| `password` | VARCHAR(255) | NO | | Mật khẩu (BCrypt hash) |
| `phone` | VARCHAR(15) | YES | NULL | Số điện thoại |
| `email` | VARCHAR(100) | YES | NULL | Email |
| `role` | ENUM | NO | 'STAFF' | ADMIN hoặc STAFF |
| `is_active` | TINYINT(1) | NO | 1 | 1 = hoạt động, 0 = bị khóa |
| `created_at` | DATETIME | NO | CURRENT_TIMESTAMP | Ngày tạo tài khoản |
| `updated_at` | DATETIME | YES | NULL | Ngày cập nhật cuối |

### SQL tạo bảng
```sql
CREATE TABLE users (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    full_name   VARCHAR(100)    NOT NULL COMMENT 'Họ và tên',
    username    VARCHAR(50)     NOT NULL UNIQUE COMMENT 'Tên đăng nhập',
    password    VARCHAR(255)    NOT NULL COMMENT 'Mật khẩu đã mã hóa BCrypt',
    phone       VARCHAR(15)     DEFAULT NULL COMMENT 'Số điện thoại',
    email       VARCHAR(100)    DEFAULT NULL COMMENT 'Email',
    role        ENUM('ADMIN','STAFF') NOT NULL DEFAULT 'STAFF' COMMENT 'Vai trò',
    is_active   TINYINT(1)      NOT NULL DEFAULT 1 COMMENT '1=hoạt động, 0=bị khóa',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Tài khoản Admin và Nhân viên';
```

### Java Entity tương ứng
```java
@Entity
@Table(name = "users")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Dữ liệu mẫu (insert khi khởi tạo)
```sql
-- Mật khẩu: admin123 (đã hash BCrypt)
INSERT INTO users (full_name, username, password, role, is_active)
VALUES ('Quản Trị Viên', 'admin',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXgT7Y7TtILGz6X7lAlJbNflhma',
        'ADMIN', 1);

-- Mật khẩu: staff123 (đã hash BCrypt)
INSERT INTO users (full_name, username, password, phone, role, is_active)
VALUES ('Nguyễn Thị Lan', 'lan.nv',
        '$2a$10$WDl2ADoJGm/kQR4eNH9VEuZsMT4ug1CDjOGSiYvDX8.wkMHgIz.6W',
        '0901234567', 'STAFF', 1);
```

---

## 📋 BẢNG 2: `courts` – Sân thể thao

> Lưu thông tin tất cả các sân. Admin quản lý, nhân viên chọn khi đặt lịch.

### Cấu trúc bảng

| Cột | Kiểu dữ liệu | Null | Default | Mô tả |
|---|---|---|---|---|
| `id` | BIGINT | NO | AUTO_INCREMENT | Khóa chính |
| `name` | VARCHAR(100) | NO | | Tên sân |
| `court_type` | ENUM | NO | | Loại sân |
| `description` | TEXT | YES | NULL | Mô tả chi tiết |
| `price_per_hour` | DECIMAL(10,0) | NO | | Giá thuê theo giờ (VNĐ) |
| `status` | ENUM | NO | 'HOAT_DONG' | Trạng thái sân |
| `created_at` | DATETIME | NO | CURRENT_TIMESTAMP | Ngày tạo |
| `updated_at` | DATETIME | YES | NULL | Ngày cập nhật |

### SQL tạo bảng
```sql
CREATE TABLE courts (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100)    NOT NULL COMMENT 'Tên sân',
    court_type      ENUM('BONG_DA','CAU_LONG','TENNIS','BONG_RO','BONG_CHUYEN','KHAC')
                    NOT NULL COMMENT 'Loại sân',
    description     TEXT            DEFAULT NULL COMMENT 'Mô tả sân',
    price_per_hour  DECIMAL(10,0)   NOT NULL COMMENT 'Giá thuê/giờ (VNĐ)',
    status          ENUM('HOAT_DONG','BAO_TRI','TAM_DONG')
                    NOT NULL DEFAULT 'HOAT_DONG' COMMENT 'Trạng thái sân',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Danh sách sân thể thao';
```

### Java Entity tương ứng
```java
@Entity
@Table(name = "courts")
@Data
public class Court {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "court_type", nullable = false)
    private CourtType courtType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 0)
    private BigDecimal pricePerHour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourtStatus status = CourtStatus.HOAT_DONG;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Dữ liệu mẫu
```sql
INSERT INTO courts (name, court_type, description, price_per_hour, status) VALUES
('Sân Bóng Đá A1',   'BONG_DA',   'Sân cỏ nhân tạo, có mái che, sức chứa 10 người', 200000, 'HOAT_DONG'),
('Sân Bóng Đá A2',   'BONG_DA',   'Sân cỏ nhân tạo ngoài trời, sức chứa 14 người',  250000, 'HOAT_DONG'),
('Sân Cầu Lông B1',  'CAU_LONG',  'Sàn gỗ, hệ thống đèn LED, 2 người',              80000,  'HOAT_DONG'),
('Sân Cầu Lông B2',  'CAU_LONG',  'Sàn gỗ, điều hòa không khí, 2 người',             100000, 'HOAT_DONG'),
('Sân Tennis C1',    'TENNIS',    'Sân hard court tiêu chuẩn, có mái che',            150000, 'HOAT_DONG'),
('Sân Bóng Rổ D1',   'BONG_RO',   'Sàn gỗ, 10 người chơi',                           120000, 'BAO_TRI');
```

---

## 📋 BẢNG 3: `bookings` – Đơn đặt sân

> Bảng trung tâm. Lưu mọi lịch đặt sân. Chứa cả tên + SĐT khách (không cần tài khoản).

### Cấu trúc bảng

| Cột | Kiểu dữ liệu | Null | Default | Mô tả |
|---|---|---|---|---|
| `id` | BIGINT | NO | AUTO_INCREMENT | Khóa chính |
| `booking_code` | VARCHAR(20) | NO | | Mã đơn (DL + ngày + số) |
| `court_id` | BIGINT | NO | | FK → courts.id |
| `customer_name` | VARCHAR(100) | NO | | **Tên khách (nhập thủ công)** |
| `customer_phone` | VARCHAR(15) | NO | | **SĐT khách (nhập thủ công)** |
| `booking_date` | DATE | NO | | Ngày chơi |
| `start_time` | TIME | NO | | Giờ bắt đầu |
| `end_time` | TIME | NO | | Giờ kết thúc |
| `duration_hours` | DECIMAL(4,1) | NO | | Số giờ (end - start) |
| `price_per_hour` | DECIMAL(10,0) | NO | | Giá/giờ tại thời điểm đặt |
| `total_amount` | DECIMAL(10,0) | NO | | Tổng tiền = giá × giờ |
| `status` | ENUM | NO | 'CHO_THANH_TOAN' | Trạng thái đơn |
| `notes` | TEXT | YES | NULL | Ghi chú thêm |
| `created_by` | BIGINT | NO | | FK → users.id (NV tạo đơn) |
| `created_at` | DATETIME | NO | CURRENT_TIMESTAMP | Thời điểm tạo đơn |
| `updated_at` | DATETIME | YES | NULL | Cập nhật cuối |

### SQL tạo bảng
```sql
CREATE TABLE bookings (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    booking_code    VARCHAR(20)     NOT NULL UNIQUE COMMENT 'Mã đơn: DL20240601001',
    court_id        BIGINT          NOT NULL COMMENT 'FK sân thể thao',
    customer_name   VARCHAR(100)    NOT NULL COMMENT 'Tên khách hàng (nhập thủ công)',
    customer_phone  VARCHAR(15)     NOT NULL COMMENT 'SĐT khách hàng',
    booking_date    DATE            NOT NULL COMMENT 'Ngày chơi',
    start_time      TIME            NOT NULL COMMENT 'Giờ bắt đầu',
    end_time        TIME            NOT NULL COMMENT 'Giờ kết thúc',
    duration_hours  DECIMAL(4,1)    NOT NULL COMMENT 'Tổng số giờ',
    price_per_hour  DECIMAL(10,0)   NOT NULL COMMENT 'Giá tại thời điểm đặt',
    total_amount    DECIMAL(10,0)   NOT NULL COMMENT 'Tổng tiền = price × duration',
    status          ENUM('CHO_THANH_TOAN','DA_THANH_TOAN','DA_HUY','HOAN_THANH')
                    NOT NULL DEFAULT 'CHO_THANH_TOAN',
    notes           TEXT            DEFAULT NULL COMMENT 'Ghi chú',
    created_by      BIGINT          NOT NULL COMMENT 'FK nhân viên tạo đơn',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_booking_court
        FOREIGN KEY (court_id) REFERENCES courts(id) ON DELETE RESTRICT,
    CONSTRAINT fk_booking_user
        FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Đơn đặt lịch sân thể thao';

-- Index tăng tốc truy vấn kiểm tra trùng lịch
CREATE INDEX idx_booking_court_date
    ON bookings (court_id, booking_date, status);
```

### Java Entity tương ứng
```java
@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_code", nullable = false, unique = true, length = 20)
    private String bookingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_phone", nullable = false, length = 15)
    private String customerPhone;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "duration_hours", nullable = false, precision = 4, scale = 1)
    private BigDecimal durationHours;

    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 0)
    private BigDecimal pricePerHour;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 0)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.CHO_THANH_TOAN;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

---

## 📋 BẢNG 4: `payments` – Thanh toán

> Ghi nhận chi tiết việc thu tiền. Mỗi đơn đặt có tối đa 1 bản ghi thanh toán.

### Cấu trúc bảng

| Cột | Kiểu dữ liệu | Null | Default | Mô tả |
|---|---|---|---|---|
| `id` | BIGINT | NO | AUTO_INCREMENT | Khóa chính |
| `booking_id` | BIGINT | NO | | FK → bookings.id (unique) |
| `amount` | DECIMAL(10,0) | NO | | Số tiền đã thu |
| `payment_method` | ENUM | NO | 'TIEN_MAT' | Phương thức |
| `paid_at` | DATETIME | NO | CURRENT_TIMESTAMP | Thời điểm thu tiền |
| `collected_by` | BIGINT | NO | | FK → users.id (NV thu tiền) |
| `notes` | VARCHAR(255) | YES | NULL | Ghi chú thanh toán |

### SQL tạo bảng
```sql
CREATE TABLE payments (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    booking_id      BIGINT          NOT NULL UNIQUE COMMENT 'FK đơn đặt sân (1:1)',
    amount          DECIMAL(10,0)   NOT NULL COMMENT 'Số tiền đã thu',
    payment_method  ENUM('TIEN_MAT','CHUYEN_KHOAN')
                    NOT NULL DEFAULT 'TIEN_MAT' COMMENT 'Phương thức thanh toán',
    paid_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm thu',
    collected_by    BIGINT          NOT NULL COMMENT 'FK nhân viên thu tiền',
    notes           VARCHAR(255)    DEFAULT NULL COMMENT 'Ghi chú',
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE RESTRICT,
    CONSTRAINT fk_payment_collector
        FOREIGN KEY (collected_by) REFERENCES users(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Thông tin thanh toán';
```

### Java Entity tương ứng
```java
@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.TIEN_MAT;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collected_by", nullable = false)
    private User collectedBy;

    @Column(length = 255)
    private String notes;
}
```

---

## 🔍 CÁC QUERY QUAN TRỌNG

### 1. Kiểm tra trùng lịch (QUERY QUAN TRỌNG NHẤT)
```java
// BookingRepository.java
@Query("""
    SELECT COUNT(b) > 0 FROM Booking b
    WHERE b.court.id = :courtId
      AND b.bookingDate = :date
      AND b.status <> 'DA_HUY'
      AND b.startTime < :endTime
      AND b.endTime > :startTime
      AND (:excludeId IS NULL OR b.id <> :excludeId)
""")
boolean hasConflict(Long courtId, LocalDate date,
                    LocalTime startTime, LocalTime endTime,
                    Long excludeId);
```

### 2. Lấy lịch sân theo ngày
```java
@Query("SELECT b FROM Booking b WHERE b.court.id = :courtId AND b.bookingDate = :date AND b.status <> 'DA_HUY' ORDER BY b.startTime")
List<Booking> findScheduleByCourtAndDate(Long courtId, LocalDate date);
```

### 3. Thống kê doanh thu theo ngày
```sql
SELECT DATE(p.paid_at) as ngay, SUM(p.amount) as doanh_thu
FROM payments p
WHERE DATE(p.paid_at) BETWEEN :startDate AND :endDate
GROUP BY DATE(p.paid_at)
ORDER BY ngay;
```

### 4. Đơn đặt hôm nay
```java
@Query("SELECT b FROM Booking b WHERE b.bookingDate = :today ORDER BY b.startTime")
List<Booking> findTodayBookings(LocalDate today);
```

### 5. Tìm kiếm theo tên / SĐT khách
```java
@Query("SELECT b FROM Booking b WHERE b.customerName LIKE %:keyword% OR b.customerPhone LIKE %:keyword% ORDER BY b.createdAt DESC")
List<Booking> searchByCustomer(String keyword);
```

---

## 🚀 SCRIPT KHỞI TẠO NHANH

```sql
-- Tạo database
CREATE DATABASE IF NOT EXISTS sports_booking
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sports_booking;

-- Chạy lần lượt 4 bảng ở trên (users → courts → bookings → payments)
-- Sau đó insert dữ liệu mẫu

-- Xác nhận
SELECT 'users' as bang, COUNT(*) as so_ban_ghi FROM users
UNION ALL
SELECT 'courts', COUNT(*) FROM courts
UNION ALL
SELECT 'bookings', COUNT(*) FROM bookings
UNION ALL
SELECT 'payments', COUNT(*) FROM payments;
```

---

## ⚠️ LƯU Ý QUAN TRỌNG

```
1. KHÔNG xóa cứng (hard delete) booking đã có payment → dùng soft delete (status = DA_HUY)
2. price_per_hour trong bookings lưu giá TẠI THỜI ĐIỂM ĐẶT
   → Sau này admin sửa giá sân thì đơn cũ vẫn đúng
3. Luôn tạo index trên (court_id, booking_date) để query trùng lịch nhanh
4. booking_code sinh tự động theo pattern: DL + yyyyMMdd + 3 số tự tăng
   Ví dụ: DL20240601001, DL20240601002...
5. duration_hours tính = (end_time - start_time) tính bằng giờ (hỗ trợ nửa giờ: 1.5, 2.5...)
6. Mật khẩu LUÔN dùng BCryptPasswordEncoder, KHÔNG lưu plain text
```

---

*📅 Cập nhật lần cuối: 2024 | 🗄️ Database Design – Môn Lập trình Java*
