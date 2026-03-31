package ra.meetingroom.model.booking;

import java.time.LocalDateTime;

public class Assignment {
    private int id;
    private int bookingId;        // FK -> bookings
    private int supportStaffId;   // FK -> users (role SUPPORT)
    private String status;
    // ASSIGNED, IN_PROGRESS, DONE
    private LocalDateTime assignedAt;

    public Assignment() {
    }

    public Assignment(LocalDateTime assignedAt, int bookingId, int id, String status, int supportStaffId) {
        this.assignedAt = assignedAt;
        this.bookingId = bookingId;
        this.id = id;
        this.status = status;
        this.supportStaffId = supportStaffId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
      // 61  ====================== LIST ASSIGNMENT ======================
        return String.format("| %-4d | %-8d | %-8d | %-12s | %-13s |\n|-----------------------------------------------------------|",
                id,
                bookingId,
                supportStaffId,
                status,
                assignedAt
        );//16
    }

    public int getSupportStaffId() {
        return supportStaffId;
    }

    public void setSupportStaffId(int supportStaffId) {
        this.supportStaffId = supportStaffId;
    }
}
