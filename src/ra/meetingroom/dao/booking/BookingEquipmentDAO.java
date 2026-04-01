package ra.meetingroom.dao.booking;

import ra.meetingroom.model.booking.BookingEquipment;
import ra.meetingroom.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingEquipmentDAO {

    public boolean insert(int bookingId, int equipmentId, int quantity) {
        String sql = "INSERT INTO booking_equipments(booking_id, equipment_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, equipmentId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BookingEquipment> findByBookingId(int bookingId) {
        List<BookingEquipment> list = new ArrayList<>();
        String sql = "SELECT * FROM booking_equipments WHERE booking_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookingEquipment be = new BookingEquipment();
                be.setId(rs.getInt("id"));
                be.setBookingId(rs.getInt("booking_id"));
                be.setEquipmentId(rs.getInt("equipment_id"));
                be.setQuantity(rs.getInt("quantity"));

                list.add(be);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}