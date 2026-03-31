package ra.meetingroom.service.booking;

import ra.meetingroom.dao.booking.AssignmentDAO;
import ra.meetingroom.dao.booking.BookingDAO;
import ra.meetingroom.dao.people.UserDAO;
import ra.meetingroom.model.booking.Assignment;
import ra.meetingroom.model.booking.Booking;
import java.util.List;

public class AssignmentService {

    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private UserDAO userDAO = new UserDAO(); // nếu có

    public boolean assign(int bookingId, int supportId) {

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            System.out.println("Booking không tồn tại!");
            return false;
        }

        if (!"APPROVED".equals(booking.getStatus())) {
            System.out.println("Chỉ assign khi booking đã APPROVED!");
            return false;
        }

        Assignment exist = assignmentDAO.findByBookingId(bookingId);
        if (exist != null) {
            System.out.println("Booking đã có support rồi!");
            return false;
        }

        // optional check role
        // User user = userDAO.findById(supportId);
        // if (user == null || !"SUPPORT".equals(user.getRole())) return false;

        return assignmentDAO.insert(bookingId, supportId, "PREPARING");
    }

    public Assignment getByBooking(int bookingId) {
        return assignmentDAO.findByBookingId(bookingId);
    }

    public List<Assignment> getBySupport(int supportId) {
        return assignmentDAO.findBySupportId(supportId);
    }

    public boolean updateStatus(int id, int supportId, String status) {

        Assignment a = assignmentDAO.findById(id);

        if (a == null) {
            System.out.println("Không tìm thấy assignment!");
            return false;
        }

        if (a.getSupportStaffId() != supportId) {
            System.out.println("Bạn không có quyền!");
            return false;
        }

        Booking booking = bookingDAO.findById(a.getBookingId());

        if (booking == null) {
            System.out.println("Booking không tồn tại!");
            return false;
        }

        if (!"APPROVED".equals(booking.getStatus())) {
            System.out.println("Chỉ update khi booking đã APPROVED!");
            return false;
        }

        return assignmentDAO.updateStatus(id, status);
    }

    public String getStatusByBooking(int bookingId) {
        Assignment a = assignmentDAO.findByBookingId(bookingId);
        return a != null ? a.getStatus() : null;
    }
}