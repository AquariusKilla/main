package Kits;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Arrays;

public class KitsDataBase {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Admin/Desktop/paper-server/plugins/kitdatabase.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS kitdatabase (" +
                            "uniqueId TEXT PRIMARY KEY," +
                            "kitname TEXT NOT NULL," +
                            "next_activation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Ошибка при создании базы данных: " + e.getMessage());
        }
    }

    public static void saveTimeActivation(String uniqueId, String kitname, Timestamp next_activation_time) {
        String sql = "INSERT OR REPLACE INTO kitdatabase (uniqueId, kitname, next_activation_time) VALUES(? ,?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uniqueId);
            pstmt.setString(2, kitname);
            pstmt.setTimestamp(3, next_activation_time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка сохранения времени актиции кита: " + e.getMessage());
        }
    }

    public static Timestamp getTime(Player player) {
        String sql = "SELECT next_activation_time FROM kitdatabase WHERE uniqueId = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.getTimestamp("next_activation_time");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при запросе времени использования кита " + e.getMessage() + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
}


