-- ============================================================
--  HỆ THỐNG QUẢN LÝ ĐẶT LỊCH SÂN THỂ THAO
--  File: sports_booking.sql
--  Môn: Lập trình Java - Spring Boot
--  Chạy file này 1 lần để khởi tạo toàn bộ database
-- ============================================================

-- ❶ TẠO VÀ CHỌN DATABASE
-- ------------------------------------------------------------
DROP DATABASE IF EXISTS sports_booking;
CREATE DATABASE sports_booking
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sports_booking;

-- ============================================================
--  BẢNG 1: users
--  Lưu tài khoản Admin và Nhân viên
--  Khách hàng KHÔNG có tài khoản ở đây
-- ============================================================
CREATE TABLE users (
    id          BIGINT          NOT NULL AUTO_INCREMENT         COMMENT 'Khóa chính',
    full_name   VARCHAR(100)    NOT NULL                        COMMENT 'Họ và tên',
    username    VARCHAR(50)     NOT NULL UNIQUE                 COMMENT 'Tên đăng nhập',
    password    VARCHAR(255)    NOT NULL                        COMMENT 'Mật khẩu BCrypt',
    phone       VARCHAR(15)     DEFAULT NULL                    COMMENT 'Số điện thoại',
    email       VARCHAR(100)    DEFAULT NULL                    COMMENT 'Email',
    role        ENUM('ADMIN','STAFF') NOT NULL DEFAULT 'STAFF'  COMMENT 'Vai trò',
    is_active   TINYINT(1)      NOT NULL DEFAULT 1              COMMENT '1=hoạt động, 0=bị khóa',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Tài khoản Admin và Nhân viên';


-- ============================================================
--  BẢNG 2: courts
--  Thông tin các sân thể thao
-- ============================================================
CREATE TABLE courts (
    id              BIGINT          NOT NULL AUTO_INCREMENT     COMMENT 'Khóa chính',
    name            VARCHAR(100)    NOT NULL                    COMMENT 'Tên sân',
    court_type      ENUM(
                        'BONG_DA',
                        'CAU_LONG',
                        'TENNIS',
                        'BONG_RO',
                        'BONG_CHUYEN',
                        'KHAC'
                    ) NOT NULL                                  COMMENT 'Loại sân',
    description     TEXT            DEFAULT NULL                COMMENT 'Mô tả chi tiết',
    price_per_hour  DECIMAL(10,0)   NOT NULL                    COMMENT 'Giá thuê/giờ (VNĐ)',
    status          ENUM(
                        'HOAT_DONG',
                        'BAO_TRI',
                        'TAM_DONG'
                    ) NOT NULL DEFAULT 'HOAT_DONG'              COMMENT 'Trạng thái sân',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_court_type   (court_type),
    INDEX idx_court_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Danh sách sân thể thao';


-- ============================================================
--  BẢNG 3: bookings
--  Đơn đặt lịch sân - BẢNG TRUNG TÂM
--  Lưu tên + SĐT khách trực tiếp (không cần tài khoản)
-- ============================================================
CREATE TABLE bookings (
    id              BIGINT          NOT NULL AUTO_INCREMENT     COMMENT 'Khóa chính',
    booking_code    VARCHAR(20)     NOT NULL UNIQUE             COMMENT 'Mã đơn: DL20260308001',
    court_id        BIGINT          NOT NULL                    COMMENT 'FK → courts.id',
    customer_name   VARCHAR(100)    NOT NULL                    COMMENT 'Tên khách (nhập thủ công)',
    customer_phone  VARCHAR(15)     NOT NULL                    COMMENT 'SĐT khách (nhập thủ công)',
    booking_date    DATE            NOT NULL                    COMMENT 'Ngày chơi',
    start_time      TIME            NOT NULL                    COMMENT 'Giờ bắt đầu',
    end_time        TIME            NOT NULL                    COMMENT 'Giờ kết thúc',
    duration_hours  DECIMAL(4,1)    NOT NULL                    COMMENT 'Số giờ thuê',
    price_per_hour  DECIMAL(10,0)   NOT NULL                    COMMENT 'Giá tại thời điểm đặt',
    total_amount    DECIMAL(10,0)   NOT NULL                    COMMENT 'Tổng tiền = giá × giờ',
    status          ENUM(
                        'CHO_THANH_TOAN',
                        'DA_THANH_TOAN',
                        'DA_HUY',
                        'HOAN_THANH'
                    ) NOT NULL DEFAULT 'CHO_THANH_TOAN'         COMMENT 'Trạng thái đơn',
    notes           TEXT            DEFAULT NULL                COMMENT 'Ghi chú thêm',
    created_by      BIGINT          NOT NULL                    COMMENT 'FK → users.id (NV tạo đơn)',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_booking_court
        FOREIGN KEY (court_id)   REFERENCES courts(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_booking_user
        FOREIGN KEY (created_by) REFERENCES users(id)  ON DELETE RESTRICT ON UPDATE CASCADE,
    -- Index quan trọng: tăng tốc query kiểm tra trùng lịch
    INDEX idx_court_date        (court_id, booking_date),
    INDEX idx_court_date_status (court_id, booking_date, status),
    INDEX idx_booking_date      (booking_date),
    INDEX idx_status            (status),
    INDEX idx_customer_phone    (customer_phone),
    INDEX idx_created_by        (created_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Đơn đặt lịch sân thể thao';


-- ============================================================
--  BẢNG 4: payments
--  Ghi nhận thanh toán tiền mặt
--  Mỗi đơn đặt có tối đa 1 bản ghi thanh toán
-- ============================================================
CREATE TABLE payments (
    id              BIGINT          NOT NULL AUTO_INCREMENT     COMMENT 'Khóa chính',
    booking_id      BIGINT          NOT NULL UNIQUE             COMMENT 'FK → bookings.id (1:1)',
    amount          DECIMAL(10,0)   NOT NULL                    COMMENT 'Số tiền đã thu',
    payment_method  ENUM(
                        'TIEN_MAT',
                        'CHUYEN_KHOAN'
                    ) NOT NULL DEFAULT 'TIEN_MAT'               COMMENT 'Phương thức thanh toán',
    paid_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT 'Thời điểm thu tiền',
    collected_by    BIGINT          NOT NULL                    COMMENT 'FK → users.id (NV thu tiền)',
    notes           VARCHAR(255)    DEFAULT NULL                COMMENT 'Ghi chú thanh toán',
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id)    REFERENCES bookings(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_payment_collector
        FOREIGN KEY (collected_by)  REFERENCES users(id)    ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_paid_at      (paid_at),
    INDEX idx_collected_by (collected_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Thông tin thanh toán tiền mặt';


-- ============================================================
--  DỮ LIỆU MẪU - users
--  Mật khẩu đã hash BCrypt:
--    admin   → admin123
--    lan.nv  → staff123
--    minh.nv → staff123
-- ============================================================
INSERT INTO users (full_name, username, password, phone, email, role, is_active) VALUES
(
    'Quản Trị Viên',
    'admin',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXgT7Y7TtILGz6X7lAlJbNflhma',
    '0900000000',
    'admin@sports.com',
    'ADMIN',
    1
),
(
    'Nguyễn Thị Lan',
    'lan.nv',
    '$2a$10$slYQmyNdgTY86BRpNejBCu9b.dIFQz3fU68FrDbcmJHdAGQf4jGWe',
    '0901234568',
    'lan@sports.com',
    'STAFF',
    1
),
(
    'Trần Văn Minh',
    'minh.nv',
    '$2a$10$slYQmyNdgTY86BRpNejBCu9b.dIFQz3fU68FrDbcmJHdAGQf4jGWe',
    '0901234569',
    'minh@sports.com',
    'STAFF',
    1
);


-- ============================================================
--  DỮ LIỆU MẪU - courts
--  Đúng với dữ liệu hiển thị trong Figma
-- ============================================================
INSERT INTO courts (name, court_type, description, price_per_hour, status) VALUES
('Sân Bóng Đá A1',  'BONG_DA',  'Sân cỏ nhân tạo, có mái che, sức chứa 10 người',        200000, 'HOAT_DONG'),
('Sân Bóng Đá A2',  'BONG_DA',  'Sân cỏ nhân tạo ngoài trời, sức chứa 14 người',         250000, 'HOAT_DONG'),
('Sân Cầu Lông B1', 'CAU_LONG', 'Sàn gỗ, hệ thống đèn LED, 2 người',                     80000,  'HOAT_DONG'),
('Sân Cầu Lông B2', 'CAU_LONG', 'Sàn gỗ, điều hòa không khí, 2 người',                   100000, 'HOAT_DONG'),
('Sân Tennis C1',   'TENNIS',   'Sân hard court tiêu chuẩn, có mái che',                  150000, 'HOAT_DONG'),
('Sân Bóng Rổ D1',  'BONG_RO',  'Sàn gỗ, 10 người chơi',                                 120000, 'BAO_TRI');


-- ============================================================
--  DỮ LIỆU MẪU - bookings
--  Đúng với 3 đơn hiển thị trong Figma (ngày 08/03/2026)
-- ============================================================
INSERT INTO bookings
    (booking_code, court_id, customer_name, customer_phone,
     booking_date, start_time, end_time, duration_hours,
     price_per_hour, total_amount, status, created_by)
VALUES
(
    'DL20260308001',
    1,                   -- Sân Bóng Đá A1
    'Nguyễn Văn A',
    '0912345678',
    '2026-03-08',
    '08:00:00',
    '10:00:00',
    2.0,
    200000,
    400000,
    'DA_THANH_TOAN',
    2                    -- Nguyễn Thị Lan
),
(
    'DL20260308002',
    3,                   -- Sân Cầu Lông B1
    'Trần Thị B',
    '0923456789',
    '2026-03-08',
    '14:00:00',
    '16:00:00',
    2.0,
    80000,
    160000,
    'CHO_THANH_TOAN',
    2                    -- Nguyễn Thị Lan
),
(
    'DL20260308003',
    2,                   -- Sân Bóng Đá A2
    'Lê Văn C',
    '0934567890',
    '2026-03-08',
    '16:00:00',
    '18:00:00',
    2.0,
    250000,
    500000,
    'CHO_THANH_TOAN',
    3                    -- Trần Văn Minh
);


-- ============================================================
--  DỮ LIỆU MẪU - payments
--  Chỉ đơn DL20260308001 đã thanh toán
-- ============================================================
INSERT INTO payments (booking_id, amount, payment_method, paid_at, collected_by) VALUES
(
    1,           -- booking DL20260308001
    400000,
    'TIEN_MAT',
    '2026-03-08 09:00:00',
    2            -- Nguyễn Thị Lan thu tiền
);


-- ============================================================
--  KIỂM TRA KẾT QUẢ SAU KHI CHẠY
-- ============================================================
SELECT '✅ Khởi tạo database thành công!' AS ket_qua;

SELECT
    'users'    AS bang, COUNT(*) AS so_ban_ghi FROM users    UNION ALL
SELECT
    'courts'   AS bang, COUNT(*) AS so_ban_ghi FROM courts   UNION ALL
SELECT
    'bookings' AS bang, COUNT(*) AS so_ban_ghi FROM bookings UNION ALL
SELECT
    'payments' AS bang, COUNT(*) AS so_ban_ghi FROM payments;


-- ============================================================
--  GHI NHỚ KHI CODE
-- ============================================================
--
--  Tài khoản test:
--    admin   / admin123  → role ADMIN
--    lan.nv  / staff123  → role STAFF
--    minh.nv / staff123  → role STAFF
--
--  Công thức tính tiền:
--    total_amount = price_per_hour × duration_hours
--
--  Mã đơn tự sinh theo pattern:
--    DL + yyyyMMdd + 3 chữ số (VD: DL20260308001)
--
--  Query kiểm tra trùng lịch (dùng trong BookingService):
--    WHERE court_id = ?
--      AND booking_date = ?
--      AND status != 'DA_HUY'
--      AND start_time < ?end_time
--      AND end_time   > ?start_time
--
--  Mật khẩu LUÔN mã hóa BCrypt trước khi lưu
--  KHÔNG lưu plain text password
--
-- ============================================================
