package qlybaixe.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import qlybaixe.util.ConnectDB;

public class PaymentDAO {

    /** Doanh thu hôm nay */
    public BigDecimal getTodayRevenue(LocalDate date) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM payments WHERE DATE(paid_at) = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            }
        }
    }

    /** Tổng doanh thu trong khoảng thời gian */
    public BigDecimal getRevenueRange(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT COALESCE(SUM(p.amount), 0) FROM payments p WHERE DATE(p.paid_at) BETWEEN ? AND ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            }
        }
    }

    /** Doanh thu theo từng ngày: [{date, amount, count}] */
    public List<Object[]> getRevenueByDay(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT DATE(p.paid_at) AS day, SUM(p.amount) AS total, COUNT(*) AS cnt "
                   + "FROM payments p WHERE DATE(p.paid_at) BETWEEN ? AND ? "
                   + "GROUP BY DATE(p.paid_at) ORDER BY day ASC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getDate("day").toLocalDate(),
                        rs.getBigDecimal("total"),
                        rs.getInt("cnt")
                    });
                }
            }
        }
        return rows;
    }

    /** Doanh thu theo sân: [{court_name, court_type, amount, count}] */
    public List<Object[]> getRevenueByCourt(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT c.name, c.court_type, COALESCE(SUM(p.amount),0) AS total, COUNT(p.id) AS cnt "
                   + "FROM courts c "
                   + "LEFT JOIN bookings b ON b.court_id = c.id "
                   + "LEFT JOIN payments p ON p.booking_id = b.id AND DATE(p.paid_at) BETWEEN ? AND ? "
                   + "GROUP BY c.id, c.name, c.court_type ORDER BY total DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("name"),
                        rs.getString("court_type"),
                        rs.getBigDecimal("total"),
                        rs.getInt("cnt")
                    });
                }
            }
        }
        return rows;
    }

    /** Thống kê tổng: {totalRevenue, totalBookings, paidBookings, cancelledBookings} */
    public Object[] getSummary(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT "
                   + "  COALESCE((SELECT SUM(amount) FROM payments WHERE DATE(paid_at) BETWEEN ? AND ?), 0) AS rev, "
                   + "  (SELECT COUNT(*) FROM bookings WHERE booking_date BETWEEN ? AND ?) AS total, "
                   + "  (SELECT COUNT(*) FROM bookings WHERE booking_date BETWEEN ? AND ? AND status='DA_THANH_TOAN') AS paid, "
                   + "  (SELECT COUNT(*) FROM bookings WHERE booking_date BETWEEN ? AND ? AND status='DA_HUY') AS cancelled";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            Date f = Date.valueOf(from), t = Date.valueOf(to);
            ps.setDate(1,f); ps.setDate(2,t);
            ps.setDate(3,f); ps.setDate(4,t);
            ps.setDate(5,f); ps.setDate(6,t);
            ps.setDate(7,f); ps.setDate(8,t);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Object[]{
                    rs.getBigDecimal("rev"), rs.getInt("total"),
                    rs.getInt("paid"), rs.getInt("cancelled")
                };
            }
        }
        return new Object[]{BigDecimal.ZERO, 0, 0, 0};
    }
}
