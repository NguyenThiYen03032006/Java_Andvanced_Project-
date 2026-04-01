package ra.meetingroom.service.booking;

import ra.meetingroom.dao.booking.BookingDAO;
import ra.meetingroom.dao.booking.BookingEquipmentDAO;
import ra.meetingroom.dao.room.EquipmentDAO;
import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.model.booking.BookingEquipment;
import ra.meetingroom.model.room.Equipment;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private static BookingDAO bookingDAO = new BookingDAO();
    private static BookingEquipmentDAO bookingEquipmentDAO=new BookingEquipmentDAO();
    private static EquipmentDAO equipmentDAO=new EquipmentDAO();
    public List<Booking> getAll() {
        return bookingDAO.findAll();
    }

    // chỉ check trung voi approved
    public boolean isConflictWithApproved(int roomId, LocalDateTime newStart, LocalDateTime newEnd) {
        List<Booking> list = bookingDAO.findByRoomId(roomId);
        for (Booking b : list) {
            if ("APPROVED".equals(b.getStatus())) {
                LocalDateTime oldStart = b.getStartTime();
                LocalDateTime oldEnd = b.getEndTime();
                if (newStart.isBefore(oldEnd) && newEnd.isAfter(oldStart)) {
                    return true;
                }
            }
        }
        return false;
    }
    //  Tạo booking
    public int createBooking(Booking b) {
        if (isConflictWithApproved(
                b.getRoomId(),
                b.getStartTime(),
                b.getEndTime()
        )) {
            System.out.println("Đã có phòng được sử dụng trong thời gian này!");
            return -1;
        }
        b.setStatus("PENDING");

        return bookingDAO.insert(b); // trả ID
    }

    public boolean approveBooking(int bookingId) {

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            System.out.println("Không tìm thấy booking!");
            return false;
        }
        // check trùng phòng
        if (isConflictWithApproved(
                booking.getRoomId(),
                booking.getStartTime(),
                booking.getEndTime()
        )) {
            System.out.println("Trùng lịch phòng!");
            return false;
        }

        //  Lấy thiết bị
        List<BookingEquipment> list = bookingEquipmentDAO.findByBookingId(bookingId);

        //  Check đủ
        for (BookingEquipment be : list) {
            if (!equipmentDAO.hasEnough(be.getEquipmentId(), be.getQuantity())) {
                System.out.println("Thiếu thiết bị ID: " + be.getEquipmentId());
                return false;
            }
        }

        // 3. Trừ thiết bị
        for (BookingEquipment be : list) {
            equipmentDAO.decrease(be.getEquipmentId(), be.getQuantity());
        }

        //  4. Duyệt booking
        bookingDAO.updateStatus(bookingId, "APPROVED");

        // Hủy booking trùng
        List<Booking> conflicts = bookingDAO.findConflictBookings(
                booking.getRoomId(),
                booking.getStartTime(),
                booking.getEndTime()
        );

        for (Booking b : conflicts) {
            if (b.getId() != bookingId) {
                bookingDAO.updateStatus(b.getId(), "CANCELLED");
            }
        }

        return true;
    }

    public List<Booking> getPending() {
        return bookingDAO.findPending();
    }

    public List<Booking> getByUser(int userId) {
        return bookingDAO.findByUserId(userId);
    }

    public boolean updateStatus(int bookingId, String status) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) {
            System.out.println("Không tìm thấy booking!");
            return false;
        }
        if (!"PENDING".equals(booking.getStatus())) {
            System.out.println("Chỉ được cập nhật booking PENDING!");
            return false;
        }
        return bookingDAO.updateStatus(bookingId, status);
    }
    public List<Booking> getApproved() {
        return bookingDAO.findByStatus("APPROVED");
    }

    public List<Booking> getByRoom(int roomId) {
        return bookingDAO.findByRoomId(roomId);
    }
}