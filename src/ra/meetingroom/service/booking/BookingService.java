package ra.meetingroom.service.booking;

import ra.meetingroom.dao.booking.BookingDAO;
import ra.meetingroom.model.booking.Booking;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private static BookingDAO bookingDAO = new BookingDAO();
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

                //  công thức vàng
                if (newStart.isBefore(oldEnd) && newEnd.isAfter(oldStart)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 🔹 Tạo booking
    public boolean createBooking(Booking b) {

        if (isConflictWithApproved(
                b.getRoomId(),
                b.getStartTime(),
                b.getEndTime()
        )) {
            System.out.println("Đã có phòng được sử dụng trong thời gian này!");
            return false;
        }

        b.setStatus("PENDING");

        return bookingDAO.insert(b);
    }


    public boolean approveBooking(int bookingId) {

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            System.out.println("Không tìm thấy booking!");
            return false;
        }

        //  check trùng với APPROVED
        if (isConflictWithApproved(
                booking.getRoomId(),
                booking.getStartTime(),
                booking.getEndTime()
        )) {
            System.out.println("Đã có booking APPROVED trùng!");
            return false;
        }

        //  duyệt booking này
        bookingDAO.updateStatus(bookingId, "APPROVED");

        // tìm PENDING trùng → hủy
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
}