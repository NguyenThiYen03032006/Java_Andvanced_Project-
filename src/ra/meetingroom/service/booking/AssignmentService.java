package ra.meetingroom.service.booking;

import ra.meetingroom.dao.booking.AssignmentDAO;
import ra.meetingroom.model.booking.Assignment;

import java.util.List;

public class AssignmentService {
    private AssignmentDAO assignmentDAO=new AssignmentDAO();
    public boolean assign(int bookingId, int supportId) {
        return assignmentDAO.insert(bookingId, supportId, "PREPARING");
    }

    public List<Assignment> getBySupport(int supportId) {
        return assignmentDAO.findBySupportId(supportId);
    }

    public void updateStatus(int id, String status) {
        assignmentDAO.updateStatus(id, status);
    }

    public String getStatusByBooking(int bookingId) {
        return assignmentDAO.findByBookingId(bookingId);
    }
}
