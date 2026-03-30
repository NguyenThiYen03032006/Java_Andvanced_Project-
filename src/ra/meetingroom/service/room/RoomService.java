package ra.meetingroom.service.room;


import ra.meetingroom.dao.booking.BookingDAO;
import ra.meetingroom.dao.room.RoomDAO;
import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.model.room.Room;

import java.util.List;

public class RoomService {

    private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO=new BookingDAO();

    public boolean addRoom(Room room) {
        return roomDAO.insert(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public boolean updateRoom(Room room) {
        return roomDAO.update(room);
    }

    public boolean deleteRoom(int id) {
        return roomDAO.delete(id);
    }

    public void showRoomsWithStatus() {

        List<Room> rooms = roomDAO.findAll();

        for (Room r : rooms) {

            System.out.println("\nPhòng: " + r.getName());

            List<Booking> bookings = bookingDAO.findApprovedByRoomId(r.getId());

            if (bookings.isEmpty()) {
                System.out.println("👉 Trống");
            } else {
                System.out.println("👉 Đã đặt:");

                for (Booking b : bookings) {
                    System.out.println(" - Bận từ "
                            + b.getStartTime().toLocalTime()
                            + " đến "
                            + b.getEndTime().toLocalTime());
                }
            }
        }
    }
}
