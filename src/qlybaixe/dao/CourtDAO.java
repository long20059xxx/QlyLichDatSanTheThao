package qlybaixe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import qlybaixe.util.ConnectDB;

public class CourtDAO {

    public int countActiveCourts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM courts WHERE status = 'HOAT_DONG'";
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
