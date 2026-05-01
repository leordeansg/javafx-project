package repository;

import database.DBUtil;
import classes.Car;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
/* CRUD functions start */
    public List<Car> findAll() {
        String sql = "SELECT carid, nickname, make, model, carYear, plate, currentMiles, addedOn FROM cars ORDER BY carid DESC";

        List<Car> cars = new ArrayList<>();

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Car car = new Car(
                        rs.getInt("carid"),
                        rs.getString("nickname"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("carYear"),
                        rs.getString("plate"),
                        rs.getInt("currentMiles"),
                        rs.getString("addedOn"));
                    cars.add(car);
                }
                return cars;

                } catch (Exception e) {
                    throw new RuntimeException("Could not load Cars", e);
                }
    }

    public void insert(Car car) {
        String sql = "INSERT INTO cars (nickname, make, model, carYear, plate, currentMiles) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, car.getNickname());
                ps.setString(2, car.getMake());
                ps.setString(3, car.getModel());
                ps.setInt(4, car.getCarYear());
                ps.setString(5, car.getPlate());
                ps.setInt(6, car.getCurrentMiles());
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("Could not insert Car", e);
            }
    }

    public void update(Car car) {
        String sql = "UPDATE cars SET nickname = ?, make = ?, model = ?, carYear = ?, plate = ?, currentMiles = ? WHERE carid = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, car.getNickname());
                ps.setString(2, car.getMake());
                ps.setString(3, car.getModel());
                ps.setInt(4, car.getCarYear());
                ps.setString(5, car.getPlate());
                ps.setInt(6, car.getCurrentMiles());
                ps.setInt(7, car.getCarid());
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("Could not update Car", e);
            }
    }

    public void delete(int carid) {
        String sql = "DELETE FROM cars WHERE carid = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, carid);
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("Could not delete Car", e);
            }
    }
/* CRUD functions end */
/* home functions start */
    public int countCars() {
        String sql = "SELECT COUNT(*) FROM cars";
        
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                return rs.getInt(1);
            } catch (Exception e) {
                throw new RuntimeException("Could not count cars", e);
            }
    }

    public int countMiles() {
        String sql = "SELECT SUM(currentMiles) FROM cars";
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                int result = rs.getInt(1);
                if (rs.wasNull()) return 0;
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Could not sum the miles", e);
            }
    }

    public String highestMileage() {
        String sql = "SELECT make, model FROM cars ORDER BY currentMiles DESC LIMIT 1";
        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return "None";
                return rs.getString(1) + " " + rs.getString(2);
            } catch (Exception e) {
                throw new RuntimeException("Could not get highest mileage", e);
            }
    }
/* home functions end */
}

