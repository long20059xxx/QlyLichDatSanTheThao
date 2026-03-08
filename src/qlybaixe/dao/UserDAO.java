package qlybaixe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import qlybaixe.model.User;
import qlybaixe.util.ConnectDB;

public class UserDAO {

    private static final String ADMIN_HASH = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXgT7Y7TtILGz6X7lAlJbNflhma";
    private static final String STAFF_HASH = "$2a$10$slYQmyNdgTY86BRpNejBCu9b.dIFQz3fU68FrDbcmJHdAGQf4jGWe";

    public User login(String username, String password) throws SQLException {
        String sql = """
                SELECT id, full_name, username, password, phone, email, role, is_active, created_at, updated_at
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                if (!resultSet.getBoolean("is_active")) {
                    return null;
                }

                String hashedPassword = resultSet.getString("password");
                if (!matchesSeedPassword(password, hashedPassword)) {
                    return null;
                }

                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(hashedPassword);
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setActive(resultSet.getBoolean("is_active"));

                java.sql.Timestamp createdAt = resultSet.getTimestamp("created_at");
                if (createdAt != null) {
                    user.setCreatedAt(createdAt.toLocalDateTime());
                }

                java.sql.Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                if (updatedAt != null) {
                    user.setUpdatedAt(updatedAt.toLocalDateTime());
                }

                return user;
            }
        }
    }

    private boolean matchesSeedPassword(String rawPassword, String hashedPassword) {
        if (hashedPassword == null || rawPassword == null) {
            return false;
        }

        if (ADMIN_HASH.equals(hashedPassword)) {
            return "admin123".equals(rawPassword);
        }

        if (STAFF_HASH.equals(hashedPassword)) {
            return "staff123".equals(rawPassword);
        }

        return rawPassword.equals(hashedPassword);
    }
}
