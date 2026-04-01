package ra.meetingroom.dao.booking;

import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // lấy danh sách booking theo phòng => check trùng lịch
    public List<Booking> findByRoomId(int roomId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean existsByRoomId(int roomId) {
        String sql = "SELECT 1 FROM bookings WHERE room_id = ? LIMIT 1";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // Insert booking
    public int insert(Booking b) {
        String sql = "INSERT INTO bookings(user_id, room_id, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getRoomId());
            ps.setTimestamp(3, Timestamp.valueOf(b.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(b.getEndTime()));
            ps.setString(5, b.getStatus());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); //trả về ID vừa insert
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // thất bại
    }

    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));
                return b;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";

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

    // lọc phòng có trạng thái pendding
    public List<Booking> findConflictBookings(int roomId, LocalDateTime start, LocalDateTime end) {

        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id = ? AND status = 'PENDING'";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                // check trùng
                if (start.isBefore(b.getEndTime()) && end.isAfter(b.getStartTime())) {
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // tim phong da duoc dat
    public List<Booking> findApprovedByRoomId(int roomId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id = ? AND status = 'APPROVED'";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // tim danh sach phong pending
    public List<Booking> findPending() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status = 'PENDING'";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Booking> findByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Booking> findByStatus(String status) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}