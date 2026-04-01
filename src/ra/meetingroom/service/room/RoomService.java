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

        if (roomDAO.existsByName(room.getName())) {
            System.out.println("Tên phòng đã tồn tại!");
            return false;
        }

        return roomDAO.insert(room);
    }

    public Room getById(int id) {
        return roomDAO.findById(id);
    }
    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public boolean updateRoom(Room room) {

        if (roomDAO.existsByNameExceptId(room.getName(), room.getId())) {
            System.out.println("Tên phòng đã tồn tại!");
            return false;
        }

        return roomDAO.update(room);
    }
    public boolean isNameExists(String name) {
        return roomDAO.existsByName(name.trim());
    }

    public boolean isNameExistsForUpdate(String name, int id) {
        return roomDAO.existsByNameExceptId(name.trim(), id);
    }

    public boolean deleteRoomWithCascade(int roomId) {

        List<Booking> bookings = bookingDAO.findByRoomId(roomId);

        // hủy toàn bộ booking
        for (Booking b : bookings) {
            bookingDAO.updateStatus(b.getId(), "CANCELLED");
        }

        return roomDAO.delete(roomId);
    }
    public boolean deleteRoom(int id) {
        // check FK
        if (bookingDAO.existsByRoomId(id)) {
            System.out.println("Không thể xóa phòng vì đã có booking!");
            return false;
        }

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
    public List<Room> searchRoom(String keyword) {
        return roomDAO.searchByName(keyword);
    }
}
