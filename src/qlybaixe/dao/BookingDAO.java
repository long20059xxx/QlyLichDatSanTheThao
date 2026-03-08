package qlybaixe.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import qlybaixe.util.ConnectDB;

public class BookingDAO {

    public int countTodayBookings(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE booking_date = ?";
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        }
    }

    public int countTotalBookings() throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings";
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    public List<Object[]> getTodayBookingRows(LocalDate date) throws SQLException {
        String sql = """
                SELECT b.booking_code, b.customer_name, b.customer_phone, c.name AS court_name,
                       b.start_time, b.end_time, b.status, b.total_amount
                FROM bookings b
                JOIN courts c ON c.id = b.court_id
                WHERE b.booking_date = ?
                ORDER BY b.start_time DESC
                """;

        List<Object[]> rows = new ArrayList<>();
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(new Object[]{
                        resultSet.getString("booking_code"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_phone"),
                        resultSet.getString("court_name"),
                        resultSet.getTime("start_time").toLocalTime(),
                        resultSet.getTime("end_time").toLocalTime(),
                        resultSet.getString("status"),
                        resultSet.getBigDecimal("total_amount")
                    });
                }
            }
        }
        return rows;
    }
}
