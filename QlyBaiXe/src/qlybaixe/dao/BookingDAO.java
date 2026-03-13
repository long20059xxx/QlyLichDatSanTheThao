package qlybaixe.dao;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import qlybaixe.model.Booking;
import qlybaixe.util.ConnectDB;

public class BookingDAO {

    // ── Đếm đơn hôm nay ─────────────────────────────────────────
    public int countTodayBookings(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE booking_date = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    // ── Đếm tổng đơn ─────────────────────────────────────────────
    public int countTotalBookings() throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // ── Lấy danh sách đơn hôm nay (cho Dashboard) ───────────────
    public List<Object[]> getTodayBookingRows(LocalDate date) throws SQLException {
        String sql = "SELECT b.booking_code, b.customer_name, b.customer_phone, c.name AS court_name,"
                   + "       b.start_time, b.end_time, b.status, b.total_amount"
                   + " FROM bookings b"
                   + " JOIN courts c ON c.id = b.court_id"
                   + " WHERE b.booking_date = ?"
                   + " ORDER BY b.start_time DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("booking_code"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("court_name"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("status"),
                        rs.getBigDecimal("total_amount")
                    });
                }
            }
        }
        return rows;
    }

    // ── Lấy tất cả đơn (cho trang Đặt lịch) ─────────────────────
    public List<Object[]> getAllBookingRows() throws SQLException {
        String sql = "SELECT b.id, b.booking_code, b.customer_name, b.customer_phone,"
                   + "       c.name AS court_name, b.booking_date,"
                   + "       b.start_time, b.end_time, b.total_amount, b.status"
                   + " FROM bookings b"
                   + " JOIN courts c ON c.id = b.court_id"
                   + " ORDER BY b.booking_date DESC, b.start_time DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(new Object[]{
                    rs.getLong("id"),
                    rs.getString("booking_code"),
                    rs.getString("customer_name"),
                    rs.getString("customer_phone"),
                    rs.getString("court_name"),
                    rs.getDate("booking_date").toLocalDate(),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime(),
                    rs.getBigDecimal("total_amount"),
                    rs.getString("status")
                });
            }
        }
        return rows;
    }

    // ── Kiểm tra trùng lịch ──────────────────────────────────────
    public boolean hasConflict(long courtId, LocalDate date,
                               LocalTime startTime, LocalTime endTime,
                               Long excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings"
                   + " WHERE court_id = ? AND booking_date = ?"
                   + "   AND status != 'DA_HUY'"
                   + "   AND start_time < ? AND end_time > ?"
                   + "   AND (? IS NULL OR id != ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, courtId);
            ps.setDate(2, Date.valueOf(date));
            ps.setTime(3, Time.valueOf(endTime));
            ps.setTime(4, Time.valueOf(startTime));
            if (excludeId == null) {
                ps.setNull(5, Types.BIGINT);
                ps.setNull(6, Types.BIGINT);
            } else {
                ps.setLong(5, excludeId);
                ps.setLong(6, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // ── Tạo mã đơn tự động (DLyyyyMMdd + 3 số) ──────────────────
    public String generateBookingCode(LocalDate date) throws SQLException {
        String prefix = "DL" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sql = "SELECT MAX(booking_code) FROM bookings WHERE booking_code LIKE ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString(1) != null) {
                    String last = rs.getString(1);
                    int seq = Integer.parseInt(last.substring(prefix.length())) + 1;
                    return prefix + String.format("%03d", seq);
                }
            }
        }
        return prefix + "001";
    }

    // ── Tạo đơn mới ─────────────────────────────────────────────
    public long createBooking(Booking b) throws SQLException {
        String sql = "INSERT INTO bookings"
                   + " (booking_code, court_id, customer_name, customer_phone,"
                   + "  booking_date, start_time, end_time, duration_hours,"
                   + "  price_per_hour, total_amount, status, notes, created_by)"
                   + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getBookingCode());
            ps.setLong(2, b.getCourtId());
            ps.setString(3, b.getCustomerName());
            ps.setString(4, b.getCustomerPhone());
            ps.setDate(5, Date.valueOf(b.getBookingDate()));
            ps.setTime(6, Time.valueOf(b.getStartTime()));
            ps.setTime(7, Time.valueOf(b.getEndTime()));
            ps.setBigDecimal(8, b.getDurationHours());
            ps.setBigDecimal(9, b.getPricePerHour());
            ps.setBigDecimal(10, b.getTotalAmount());
            ps.setString(11, "CHO_THANH_TOAN");
            ps.setString(12, b.getNotes());
            ps.setLong(13, b.getCreatedBy() != null ? b.getCreatedBy() : 1L);
            ps.executeUpdate();
            try (ResultSet gk = ps.getGeneratedKeys()) {
                return gk.next() ? gk.getLong(1) : -1;
            }
        }
    }

    // ── Xác nhận thanh toán ──────────────────────────────────────
    public void confirmPayment(long bookingId, long collectedBy) throws SQLException {
        String updSql = "UPDATE bookings SET status='DA_THANH_TOAN' WHERE id=?";
        String paySQL = "INSERT INTO payments (booking_id, amount, payment_method, collected_by)"
                      + " SELECT id, total_amount, 'TIEN_MAT', ? FROM bookings WHERE id=?";
        try (Connection conn = ConnectDB.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(updSql);
                 PreparedStatement ps2 = conn.prepareStatement(paySQL)) {
                ps1.setLong(1, bookingId);
                ps1.executeUpdate();
                ps2.setLong(1, collectedBy);
                ps2.setLong(2, bookingId);
                ps2.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // ── Hủy đơn ─────────────────────────────────────────────────
    public void cancelBooking(long bookingId) throws SQLException {
        String sql = "UPDATE bookings SET status='DA_HUY' WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            ps.executeUpdate();
        }
    }

    // ── Lịch sử có bộ lọc ───────────────────────────────────────
    public List<Object[]> getBookingHistory(LocalDate from, LocalDate to,
                                             String status, String search) throws SQLException {
        StringBuilder sb = new StringBuilder(
            "SELECT b.id, b.booking_code, b.customer_name, b.customer_phone," +
            "       c.name AS court_name, b.booking_date, b.start_time, b.end_time, b.total_amount, b.status" +
            " FROM bookings b JOIN courts c ON c.id = b.court_id" +
            " WHERE b.booking_date BETWEEN ? AND ?");
        if (status != null && !status.isEmpty() && !"ALL".equals(status))
            sb.append(" AND b.status = ?");
        if (search != null && !search.trim().isEmpty())
            sb.append(" AND (b.customer_name LIKE ? OR b.customer_phone LIKE ? OR b.booking_code LIKE ?)");
        sb.append(" ORDER BY b.booking_date DESC, b.start_time DESC");

        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            if (status != null && !status.isEmpty() && !"ALL".equals(status))
                ps.setString(idx++, status);
            if (search != null && !search.trim().isEmpty()) {
                String like = "%" + search.trim() + "%";
                ps.setString(idx++, like); ps.setString(idx++, like); ps.setString(idx, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getLong("id"), rs.getString("booking_code"),
                        rs.getString("customer_name"), rs.getString("customer_phone"),
                        rs.getString("court_name"), rs.getDate("booking_date").toLocalDate(),
                        rs.getTime("start_time").toLocalTime(), rs.getTime("end_time").toLocalTime(),
                        rs.getBigDecimal("total_amount"), rs.getString("status")
                    });
                }
            }
        }
        return rows;
    }
}
