package ra.meetingroom.model.booking;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int userId;
    private int roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer supportStaffId;

    public Booking() {
    }

    public Booking(LocalDateTime endTime, int id, int roomId, LocalDateTime startTime, String status, Integer supportStaffId, int userId) {
        this.endTime = endTime;
        this.id = id;
        this.roomId = roomId;
        this.startTime = startTime;
        this.status = status;
        this.supportStaffId = supportStaffId;
        this.userId = userId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSupportStaffId() {
        return supportStaffId;
    }

    public void setSupportStaffId(Integer supportStaffId) {
        this.supportStaffId = supportStaffId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }
}