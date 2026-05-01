package database;

import java.sql.Connection;
import java.sql.Statement;

public final class DatabaseInit {

    private DatabaseInit() {}

    public static void init() {
        try (Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement()) {

            String carsSql = (
                "CREATE TABLE IF NOT EXISTS cars (" +
                "carid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nickname TEXT NOT NULL, " +
                "make TEXT NOT NULL, " +
                "model TEXT NOT NULL, " +
                "carYear INTEGER NOT NULL, " +
                "plate TEXT NOT NULL, " +
                "currentMiles INTEGER NOT NULL DEFAULT 0, " +
                "addedOn TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            stmt.execute(carsSql);
            
            String servicesSql = (
                "CREATE TABLE IF NOT EXISTS services (" +
                "serviceid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "carid INTEGER NOT NULL, " +
                "miles INTEGER NOT NULL, " +
                "note TEXT NOT NULL, " +
                "loggedOn TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(carid) REFERENCES cars(carid) ON DELETE CASCADE" +
                ")"
            );
            stmt.execute(servicesSql);

            String financesSql = (
                "CREATE TABLE IF NOT EXISTS finances (" +
                "financeid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "carid INTEGER NOT NULL, " +
                "amount INTEGER NOT NULL, " +
                "note TEXT NOT NULL, " +
                "loggedOn TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(carid) REFERENCES cars(carid) ON DELETE CASCADE" +
                ")"
            );
            stmt.execute(financesSql);

            String remindersSql = (
                "CREATE TABLE IF NOT EXISTS reminders (" +
                "reminderid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL, " +
                "dueDate TEXT NOT NULL, " +
                "createdOn TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            stmt.execute(remindersSql);

        } catch (Exception e) {
            throw new RuntimeException("Database Init failed", e);
        }
    }
}