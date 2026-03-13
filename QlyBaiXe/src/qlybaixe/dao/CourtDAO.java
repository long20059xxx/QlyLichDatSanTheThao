package qlybaixe.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import qlybaixe.model.Court;
import qlybaixe.util.ConnectDB;

public class CourtDAO {

    /** Đếm sân đang hoạt động */
    public int countActiveCourts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM courts WHERE status = 'HOAT_DONG'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /** Lấy tất cả sân đang hoạt động cho dropdown */
    public List<Court> getAllActiveCourts() throws SQLException {
        String sql = "SELECT id, name, court_type, description, price_per_hour, status "
                   + "FROM courts WHERE status = 'HOAT_DONG' ORDER BY name";
        return queryCourts(sql);
    }

    /** Lấy tất cả sân (để quản lý) */
    public List<Court> getAllCourts() throws SQLException {
        String sql = "SELECT id, name, court_type, description, price_per_hour, status "
                   + "FROM courts ORDER BY name";
        return queryCourts(sql);
    }

    private List<Court> queryCourts(String sql) throws SQLException {
        List<Court> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Court c = new Court();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setCourtType(rs.getString("court_type"));
                c.setDescription(rs.getString("description"));
                c.setPricePerHour(rs.getBigDecimal("price_per_hour"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }
        }
        return list;
    }

    /** Thêm sân mới */
    public void addCourt(String name, String courtType, String description,
                         BigDecimal pricePerHour, String status) throws SQLException {
        String sql = "INSERT INTO courts (name, court_type, description, price_per_hour, status) VALUES (?,?,?,?,?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, courtType);
            ps.setString(3, description);
            ps.setBigDecimal(4, pricePerHour);
            ps.setString(5, status);
            ps.executeUpdate();
        }
    }

    /** Cập nhật sân */
    public void updateCourt(long id, String name, String courtType, String description,
                            BigDecimal pricePerHour, String status) throws SQLException {
        String sql = "UPDATE courts SET name=?, court_type=?, description=?, price_per_hour=?, status=?, updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, courtType);
            ps.setString(3, description);
            ps.setBigDecimal(4, pricePerHour);
            ps.setString(5, status);
            ps.setLong(6, id);
            ps.executeUpdate();
        }
    }

    /** Đổi trạng thái sân (HOAT_DONG ↔ BAO_TRI) */
    public void toggleCourtStatus(long id) throws SQLException {
        String sql = "UPDATE courts SET status = CASE WHEN status='HOAT_DONG' THEN 'BAO_TRI' ELSE 'HOAT_DONG' END, "
                   + "updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Lấy lịch theo ngày: trả về List mỗi phần tử là Object[]{courtId, courtName, courtType, hour(int), bookingStatus}
     * bookingStatus null = trống
     */
    public List<Object[]> getScheduleByDate(LocalDate date) throws SQLException {
        // Lấy tất cả sân và tất cả booking trong ngày
        String sqlCourts = "SELECT id, name, court_type FROM courts WHERE status='HOAT_DONG' ORDER BY name";
        String sqlBook   = "SELECT court_id, start_time, end_time, status FROM bookings "
                         + "WHERE booking_date=? AND status NOT IN ('DA_HUY')";
        List<Object[]> result = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnection()) {
            // Lấy sân
            List<long[]>   courtIds   = new ArrayList<>();
            List<String[]> courtNames = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sqlCourts);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    courtIds.add(new long[]{rs.getLong("id")});
                    courtNames.add(new String[]{rs.getString("name"), rs.getString("court_type")});
                }
            }

            // Lấy booking (court_id → list of [startH, endH, status])
            java.util.Map<Long, List<int[]>> bookMap = new java.util.HashMap<>();
            java.util.Map<Long, List<String>> statMap = new java.util.HashMap<>();
            try (PreparedStatement ps = conn.prepareStatement(sqlBook)) {
                ps.setDate(1, Date.valueOf(date));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        long cid = rs.getLong("court_id");
                        LocalTime st = rs.getTime("start_time").toLocalTime();
                        LocalTime et = rs.getTime("end_time").toLocalTime();
                        String stat = rs.getString("status");
                        bookMap.computeIfAbsent(cid, k -> new ArrayList<>())
                               .add(new int[]{st.getHour(), et.getHour()});
                        statMap.computeIfAbsent(cid, k -> new ArrayList<>()).add(stat);
                    }
                }
            }

            // Build result: mỗi sân × mỗi giờ (6–23)
            for (int i = 0; i < courtIds.size(); i++) {
                long cid  = courtIds.get(i)[0];
                String nm = courtNames.get(i)[0];
                String tp = courtNames.get(i)[1];
                List<int[]>   bks = bookMap.getOrDefault(cid, new ArrayList<>());
                List<String> sts  = statMap.getOrDefault(cid, new ArrayList<>());
                for (int h = 6; h <= 23; h++) {
                    String s = null;
                    for (int k = 0; k < bks.size(); k++) {
                        if (h >= bks.get(k)[0] && h < bks.get(k)[1]) {
                            s = sts.get(k); break;
                        }
                    }
                    result.add(new Object[]{cid, nm, tp, h, s});
                }
            }
        }
        return result;
    }
}
