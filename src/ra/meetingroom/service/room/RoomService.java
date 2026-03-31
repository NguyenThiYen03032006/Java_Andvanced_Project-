package ra.meetingroom.service.room;


import ra.meetingroom.dao.booking.BookingDAO;
import ra.meetingroom.dao.room.RoomDAO;
import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.model.room.Room;

import java.time.format.DateTimeFormatter;
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
        // format ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("=========================================");
        for (Room r : rooms) {
            System.out.printf("|Phòng: %-32s|\n",r.getName());
            List<Booking> bookings = bookingDAO.findApprovedByRoomId(r.getId());
            if (bookings.isEmpty()) {
                System.out.println("|Trống                                  |");
                System.out.println("_________________________________________");
            } else {
                System.out.println("|Bận                                    |");
                for (Booking b : bookings) {
                    System.out.printf("| %-17sđến %-17s|\n",b.getStartTime().format(formatter),b.getEndTime().format(formatter));
                }
                System.out.println("_________________________________________");
            }
        }
        /*
        ========================================
        ________________________________________
        |Phòng: P001                            |
        ________________________________________
        |Bận                                    |
        |12/06/2006/10:00 đến 12/06/2006/10:30 |
         */
    }
}
