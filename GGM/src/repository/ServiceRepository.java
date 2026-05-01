package repository;

import database.DBUtil;
import classes.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepository {
/* CRUD functions start */
    public List<Service> findByCar(int carid) {
        String sql = "SELECT serviceid, carid, miles, note, loggedOn " + "FROM services " + "WHERE carid = ? " + "ORDER BY serviceid DESC";
        List<Service> rows = new ArrayList<>();

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, carid);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Service(
                        rs.getInt("serviceid"),
                        rs.getInt("carid"),
                        rs.getInt("miles"),
                        rs.getString("note"),
                        rs.getString("loggedOn")
                    ));
                }
            }
            return rows;

        } catch (Exception e) {
            throw new RuntimeException("Service could not be Loaded", e);
        }
    }

    public void insert(Service s) {
        String sql = "INSERT INTO services (carid, miles, note) " + "VALUES (?, ?, ?)";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, s.getCarid());
            ps.setInt(2, s.getMiles());
            ps.setString(3, s.getNote());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Service could not be Inserted", e);
        }   
    }

    public void delete(int serviceid) {
        String sql = "DELETE FROM services WHERE serviceid = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, serviceid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Service could not be Deleted", e);
        }
    }
/* CRUD functions end */
/* home functions start */
    public int countServices() {
        String sql = "SELECT COUNT(*) FROM services";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                return rs.getInt(1);
            } catch (Exception e) {
                throw new RuntimeException("Could not count services", e);
            }
    }

    public String lastServiced() {
        String sql = "SELECT c.make, c.model FROM services s JOIN cars c ON c.carid = s.carid ORDER BY s.loggedOn DESC LIMIT 1";

        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return "None";
                return rs.getString(1) + " " + rs.getString(2);
            } catch (Exception e) {
                throw new RuntimeException("Could not get last serviced car", e);
            }
    }

    public String lastServicedDate() {
        String sql = "SELECT MAX(loggedOn) FROM services";
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                String date = rs.getString(1);
                if (date == null || date.isBlank()) {
                    return "None";
                } return date;
            } catch (Exception e) {
                throw new RuntimeException("Could not count services", e);
            }
    }
/* CRUD functions end */
}
