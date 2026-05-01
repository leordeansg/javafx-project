package repository;

import database.DBUtil;
import classes.Reminder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReminderRepository {
/* CRUD functions start */
    public List<Reminder> findAll() {
        String sql = "SELECT reminderid, name, description, dueDate, createdOn FROM reminders ORDER BY dueDate ASC";
        
        List<Reminder> rows = new ArrayList<>();

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Reminder (
                        rs.getInt("reminderid"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("dueDate"),
                        rs.getString("createdOn")
                    ));
                } return rows;
        } catch (Exception e) {
            throw new RuntimeException("Reminders couldn't be loaded", e);
        }
    }
    
    public void insert(Reminder r) {
        String sql = "INSERT INTO reminders (name, description, dueDate) VALUES (?, ?, ?)";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getDescription());
            ps.setString(3, r.getDueDate());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Data could not be inserted", e);
        }
    }

    public void delete(int reminderid) {
        String sql = "DELETE FROM reminders WHERE reminderid = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, reminderid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Reminder could not be deleted", e);
        }
    }
/* CRUD functions end */
/* home functions start */
    public Reminder findNext() {
        String sql = "SELECT reminderid, name, description, dueDate, createdOn FROM reminders ORDER BY dueDate ASC LIMIT 1";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (!rs.next()) return null;
            
            return new Reminder(
                rs.getInt("reminderid"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("dueDate"),
                rs.getString("createdOn")
            );
        } catch (Exception e) {
            throw new RuntimeException("Reminder could not be loaded", e);
        }
    }
/* home functions end */
}
