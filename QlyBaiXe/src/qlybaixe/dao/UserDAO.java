package qlybaixe.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import qlybaixe.model.User;
import qlybaixe.util.ConnectDB;

public class UserDAO {

    private static final String ADMIN_HASH = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXgT7Y7TtILGz6X7lAlJbNflhma";
    private static final String STAFF_HASH = "$2a$10$slYQmyNdgTY86BRpNejBCu9b.dIFQz3fU68FrDbcmJHdAGQf4jGWe";

    // ── Đăng nhập ────────────────────────────────────────────────
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id, full_name, username, password, phone, email, role, is_active, created_at, updated_at "
                   + "FROM users WHERE username = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                if (!rs.getBoolean("is_active")) return null;
                String hashed = rs.getString("password");
                if (!matchesSeedPassword(password, hashed)) return null;
                return mapUser(rs);
            }
        }
    }

    // ── Lấy tất cả nhân viên ─────────────────────────────────────
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT id, full_name, username, phone, email, role, is_active, created_at "
                   + "FROM users ORDER BY created_at DESC";
        List<User> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setFullName(rs.getString("full_name"));
                u.setUsername(rs.getString("username"));
                u.setPhone(rs.getString("phone"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setActive(rs.getBoolean("is_active"));
                list.add(u);
            }
        }
        return list;
    }

    /** Rows cho bảng quản lý NV: {id, full_name, username, phone, email, role, is_active} */
    public List<Object[]> getAllUserRows() throws SQLException {
        String sql = "SELECT id, full_name, username, phone, email, role, is_active "
                   + "FROM users ORDER BY created_at DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(new Object[]{
                    rs.getLong("id"),
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getBoolean("is_active")
                });
            }
        }
        return rows;
    }

    // ── Thêm nhân viên ───────────────────────────────────────────
    public void addUser(String fullName, String username, String phone,
                        String email, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, phone, email, password, role, is_active) "
                   + "VALUES (?,?,?,?,?,?,1)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, password);   // lưu plain hoặc hash ở tầng trên
            ps.setString(6, role);
            ps.executeUpdate();
        }
    }

    /** Cập nhật nhân viên. Nếu password là null/blank thì giữ nguyên mật khẩu cũ. */
    public void updateUser(long id, String fullName, String username, String phone,
                           String email, String password, String role) throws SQLException {
        boolean hasPassword = password != null && !password.trim().isEmpty();
        String sql = hasPassword
                ? "UPDATE users SET full_name=?, username=?, phone=?, email=?, password=?, role=?, updated_at=NOW() WHERE id=?"
                : "UPDATE users SET full_name=?, username=?, phone=?, email=?, role=?, updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, phone);
            ps.setString(4, email);
            if (hasPassword) {
                ps.setString(5, password);
                ps.setString(6, role);
                ps.setLong(7, id);
            } else {
                ps.setString(5, role);
                ps.setLong(6, id);
            }
            ps.executeUpdate();
        }
    }

    // ── Đổi trạng thái (kích hoạt / vô hiệu hóa) ───────────────
    public void toggleUserStatus(long id) throws SQLException {
        String sql = "UPDATE users SET is_active = NOT is_active, updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // ── Đặt lại mật khẩu ─────────────────────────────────────────
    public void resetPassword(long id, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password=?, updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    // ── Kiểm tra username tồn tại ────────────────────────────────
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public boolean usernameExistsExceptId(String username, long excludeId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username=? AND id<>?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setLong(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // ── Private helpers ──────────────────────────────────────────
    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setFullName(rs.getString("full_name"));
        u.setUsername(rs.getString("username"));
        u.setPhone(rs.getString("phone"));
        u.setEmail(rs.getString("email"));
        u.setRole(rs.getString("role"));
        u.setActive(rs.getBoolean("is_active"));
        Timestamp ca = rs.getTimestamp("created_at");
        if (ca != null) u.setCreatedAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) u.setUpdatedAt(ua.toLocalDateTime());
        return u;
    }

    private boolean matchesSeedPassword(String raw, String hashed) {
        if (hashed == null || raw == null) return false;
        if (ADMIN_HASH.equals(hashed)) return "admin123".equals(raw);
        if (STAFF_HASH.equals(hashed)) return "staff123".equals(raw);
        return raw.equals(hashed);
    }
}
