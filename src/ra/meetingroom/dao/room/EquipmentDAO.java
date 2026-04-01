package ra.meetingroom.dao.room;

import ra.meetingroom.model.room.Equipment;
import ra.meetingroom.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    // Lấy danh sách
    public List<Equipment> findAll() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipments";

        try (Connection conn = DBConnection.openConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setTotalQuantity(rs.getInt("total_quantity"));
                e.setAvailableQuantity(rs.getInt("available_quantity"));
                e.setStatus(rs.getString("status"));

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(Equipment e) {
        String sql = "INSERT INTO equipments(name, total_quantity, available_quantity, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setInt(2, e.getTotalQuantity());
            ps.setInt(3, e.getAvailableQuantity());
            ps.setString(4, e.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    //  Update số lượng
    public boolean updateQuantity(int id, int total, int available) {
        String sql = "UPDATE equipments SET total_quantity=?, available_quantity=? WHERE id=?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, total);
            ps.setInt(2, available);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa thiết bị
    public boolean delete(int id) {
        String sql = "DELETE FROM equipments WHERE id=?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean hasEnough(int equipmentId, int quantity) {
        String sql = "SELECT available_quantity FROM equipments WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, equipmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("available_quantity") >= quantity;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean decrease(int equipmentId, int quantity) {
        String sql = "UPDATE equipments SET available_quantity = available_quantity - ? WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, equipmentId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}