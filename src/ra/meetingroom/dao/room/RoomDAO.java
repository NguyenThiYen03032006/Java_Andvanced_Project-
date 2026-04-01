package ra.meetingroom.dao.room;

import ra.meetingroom.model.room.Room;
import ra.meetingroom.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public boolean insert(Room room) {
        String sql = "INSERT INTO rooms(name, capacity,location, description) VALUES (?, ?,?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3,room.getLocation());
            ps.setString(4, room.getDescription());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Room> findAll() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DBConnection.openConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setDescription(rs.getString("description"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // check tồn tại theo tên
    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM rooms WHERE name = ? LIMIT 1";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // check trung ten khi update tru chinh no
    public boolean existsByNameExceptId(String name, int id) {
        String sql = "SELECT 1 FROM rooms WHERE name = ? AND id != ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Room room) {
        String sql = "UPDATE rooms SET name=?, capacity=?,location=?, description=? WHERE id=?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getLocation());
            ps.setString(4, room.getDescription());
            ps.setInt(5, room.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM rooms WHERE id=?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Room> searchByName(String keyword) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE name LIKE ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setDescription(rs.getString("description"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Room findById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setDescription(rs.getString("description"));
                return r;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
