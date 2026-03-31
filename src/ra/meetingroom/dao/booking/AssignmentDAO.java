package ra.meetingroom.dao.booking;


import ra.meetingroom.model.booking.Assignment;
import ra.meetingroom.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    // 🔹 1. Thêm phân công
    public boolean insert(int bookingId, int supportId, String status) {
        String sql = "INSERT INTO assignments (booking_id, support_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, supportId);
            ps.setString(3, status);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 2. Lấy danh sách công việc theo support
    public List<Assignment> findBySupportId(int supportId) {
        List<Assignment> list = new ArrayList<>();
        String sql = "SELECT * FROM assignments WHERE support_id = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, supportId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setBookingId(rs.getInt("booking_id"));
                a.setSupportStaffId(rs.getInt("support_id"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 3. Update trạng thái (Preparing → Ready → Missing)
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE assignments SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 4. Lấy trạng thái theo booking (cho Employee xem)
    public Assignment findByBookingId(int bookingId) {
        String sql = "SELECT * FROM assignments WHERE booking_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setBookingId(rs.getInt("booking_id"));
                a.setSupportStaffId(rs.getInt("support_id"));
                a.setStatus(rs.getString("status"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Assignment findById(int id) {

        String sql = "SELECT * FROM assignments WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setBookingId(rs.getInt("booking_id"));
                a.setSupportStaffId(rs.getInt("support_id"));
                a.setStatus(rs.getString("status"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}