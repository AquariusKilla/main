package Auth;

import java.sql.*;

public class AuthDatabase {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Admin/Desktop/paper-server/plugins/registeredUsers.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS registeredUsers (" +
                            "uniqueId TEXT PRIMARY KEY," +
                            "playerName TEXT NOT NULL UNIQUE," +
                            "password TEXT NOT NULL);";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Ошибка при создании базы данных: " + e.getMessage());
        }
    }

    public static void addUser(String uniqueId, String playerName, String password) {
        String sql = "INSERT INTO registeredUsers(uniqueId, playerName, password) VALUES(? ,?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uniqueId);
            pstmt.setString(2, playerName);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }
    public static boolean isRegistred(String uniqueId) {
        String sql = "SELECT 1 FROM registeredUsers WHERE uniqueId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uniqueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке пользователя: " + e.getMessage());
            return false;
        }
    }

    public static String getPassword(String uniqueId) {
        String sql = "SELECT password FROM registeredUsers WHERE uniqueId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uniqueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при запросе пароля: " + e.getMessage());
        }
        return null;
    }
}
