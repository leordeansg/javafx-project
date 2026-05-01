package repository;

import database.DBUtil;
import classes.Finance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FinanceRepository {
/* CRUD functions start */
    public List<Finance> findByCar(int carid) {
        String sql = "SELECT financeid, carid, amount, note, loggedOn FROM finances WHERE carid = ? ORDER BY financeid DESC";
        List<Finance> rows = new ArrayList<>();

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, carid);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Finance(
                        rs.getInt("financeid"),
                        rs.getInt("carid"),
                        rs.getInt("amount"),
                        rs.getString("note"),
                        rs.getString("loggedOn")
                    ));
                }
            }
            return rows;

        } catch (Exception e) {
            throw new RuntimeException("Finance could not be Loaded", e);
        }
    }

    public void insert(Finance f) {
        String sql = "INSERT INTO finances (carid, amount, note) VALUES (?, ?, ?)";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, f.getCarid());
            ps.setInt(2, f.getAmount());
            ps.setString(3, f.getNote());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Finance could not be Inserted", e);
        }   
    }

    public void delete(int financeid) {
        String sql = "DELETE FROM finances WHERE financeid = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, financeid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Finance could not be Deleted", e);
        }
    }
/* CRUD functions end */
/* home functions start */
    public int totalAmount() {
        String sql = "SELECT SUM(amount) FROM finances";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                int result = rs.getInt(1);
                if (rs.wasNull()) return 0;
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Could not get total finances", e);
            }
    }

    public int mostExpensivePrice() {
        String sql = "SELECT MAX(amount) FROM finances";
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                int result = rs.getInt(1);
                if (rs.wasNull()) return 0;
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Could not get most expensive item", e);
            }
    }

    public String mostExpensiveName() {
        String sql = "SELECT note FROM finances ORDER BY amount DESC LIMIT 1";
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return "None";
                String note = rs.getString(1);
                if (note == null || note.isBlank()) {
                    return "";
                } return note;
            } catch (Exception e) {
                throw new RuntimeException("Could not get most expensive item", e);
            }
    }
/* home functions end */
}
