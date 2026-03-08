package qlybaixe.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import qlybaixe.util.ConnectDB;

public class PaymentDAO {

    public BigDecimal getTodayRevenue(LocalDate date) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM payments WHERE DATE(paid_at) = ?";
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getBigDecimal(1) : BigDecimal.ZERO;
            }
        }
    }
}
