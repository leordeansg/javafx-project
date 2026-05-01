package database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class DBUtil {
    private static final String DATABASE_DIRECTORY = "database";
    private static final String DATABASE_FILE = "ggm.db";
    private static final String URL = "jdbc:sqlite:" + DATABASE_DIRECTORY + "/" + DATABASE_FILE;

    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        databaseFolderCheck();
        Connection conn = DriverManager.getConnection(URL);
        try (Statement s = conn.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    private static void databaseFolderCheck() {
        try {
            Path dir = Paths.get(DATABASE_DIRECTORY);
            if (!Files.exists(dir)) Files.createDirectories(dir);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create database folder", e);
        }
    }

    public static Path getDatabasePath() {
        return Paths.get(DATABASE_DIRECTORY, DATABASE_FILE).toAbsolutePath();
    }

    public static void resetDatabase() {
        try {
            Path dbPath = getDatabasePath();
            if (Files.exists(dbPath)) {
                Files.delete(dbPath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset database", e);
        }
    }
}
