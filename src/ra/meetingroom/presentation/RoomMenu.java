package ra.meetingroom.presentation;

import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.model.room.Room;
import ra.meetingroom.service.booking.BookingService;
import ra.meetingroom.service.room.RoomService;
import ra.meetingroom.util.Validator;

import java.util.List;
import java.util.Scanner;

public class RoomMenu {

    private RoomService roomService = new RoomService();
    private BookingService bookingService=new BookingService();
    private Scanner sc = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("""
======================== ROOM MANAGEMENT ========================
|   1. Xem danh sách          |    2. Thêm phòng                |
-----------------------------------------------------------------
|   3. Sửa phòng              |    4. Xóa phòng                 |
-----------------------------------------------------------------
|   5. Tìm kiếm phòng         |    0. Thoát                     |
-----------------------------------------------------------------""");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    showList();
                    break;
                case 2:
                    add();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    System.out.print("Nhập tên phòng cần tìm: ");
                    String keyword = sc.nextLine();

                    List<Room> list = roomService.searchRoom(keyword);

                    if (list.isEmpty()) {
                        System.out.println("Không tìm thấy phòng!");
                    } else {
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("| ID |  Name  | Capacity | Location |       Description     | ");
                        System.out.println("-------------------------------------------------------------");
                        list.forEach(System.out::println);
                    }
                    break;
                case 0:
                    System.out.println("Bạn đã chọn thoát !!!");
                    return;
                default:
                    System.out.println("Lựa chọn của bạn không hợp lệ");
            }
        }
    }

    // 🔹 Xem
    private void showList() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("| ID |  Name  | Capacity | Location |       Description     | ");
        System.out.println("-------------------------------------------------------------");
        roomService.getAllRooms().forEach(System.out::println);
    }

    // 🔹 Thêm
    private void add() {
        Room r = new Room();

        String name;
        do {
            System.out.print("Nhập tên phòng: ");
            name = sc.nextLine().trim();

            if (!Validator.requireNotEmpty(name, "Tên phòng")) continue;

            // 🔥 check trùng NGAY TẠI ĐÂY
            if (roomService.isNameExists(name)) {
                System.out.println("Tên phòng đã tồn tại!");
                name = null; // ép nhập lại
            }

        } while (name == null);

        r.setName(name); // 🔥 nhớ set

        System.out.print("Sức chứa: ");
        r.setCapacity(Integer.parseInt(sc.nextLine()));

        System.out.print("Vị trí: ");
        r.setLocation(sc.nextLine());

        System.out.print("Mô tả: ");
        r.setDescription(sc.nextLine());

        if (roomService.addRoom(r)) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }

    // 🔹 Sửa
    private void update() {

        System.out.print("Nhập ID phòng cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());

        // 🔥 lấy phòng cũ
        Room oldRoom = roomService.getById(id);

        if (oldRoom == null) {
            System.out.println("Không tìm thấy phòng!");
            return;
        }

        // HIỂN THỊ THÔNG TIN CŨ
        System.out.println("----------------------Thông tin hiện tại---------------------");
        System.out.println("| ID |  Name  | Capacity | Location |       Description     | ");
        System.out.println("-------------------------------------------------------------");
        System.out.println(oldRoom);
        Room r = new Room();
        r.setId(id);

        // 🔹 nhập tên mới
        String name;
        do {
            System.out.print("Nhập tên mới (Enter để giữ nguyên): ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                name = oldRoom.getName(); // giữ nguyên
                break;
            }

            if (roomService.isNameExistsForUpdate(name, id)) {
                System.out.println("Tên đã tồn tại!");
                name = null;
            }

        } while (name == null);

        r.setName(name);
        // 🔹 capacity
        System.out.print("Sức chứa (Enter để giữ " + oldRoom.getCapacity() + "): ");
        String capInput = sc.nextLine();
        if (capInput.isEmpty()) {
            r.setCapacity(oldRoom.getCapacity());
        } else {
            r.setCapacity(Integer.parseInt(capInput));
        }

        // 🔹 location
        System.out.print("Vị trí (Enter để giữ \"" + oldRoom.getLocation() + "\"): ");
        String loc = sc.nextLine();
        r.setLocation(loc.isEmpty() ? oldRoom.getLocation() : loc);

        // 🔹 description
        System.out.print("Mô tả (Enter để giữ \"" + oldRoom.getDescription() + "\"): ");
        String desc = sc.nextLine();
        r.setDescription(desc.isEmpty() ? oldRoom.getDescription() : desc);

        // 🔥 update
        if (roomService.updateRoom(r)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }

    // 🔹 Xóa
    private void deleteRoom() {

        System.out.print("Nhập ID phòng: ");
        int id = Integer.parseInt(sc.nextLine());

        List<Booking> bookings = bookingService.getByRoom(id);

        if (bookings.isEmpty()) {
            // không có booking → xóa luôn
            if (roomService.deleteRoom(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại!");
            }
            return;
        }

        // 🔥 có booking → hiển thị
        System.out.println("Phòng đang có booking:");

        for (Booking b : bookings) {
            System.out.println("- "
                    + b.getStartTime().toLocalDate()
                    + " "
                    + b.getStartTime().toLocalTime()
                    + " → "
                    + b.getEndTime().toLocalTime());
        }

        // 🔥 hỏi user
        System.out.println("Bạn có muốn hủy toàn bộ để xóa phòng không?");
        System.out.println("1. Có");
        System.out.println("2. Không");

        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {
            boolean result = roomService.deleteRoomWithCascade(id);

            if (result) {
                System.out.println("Đã hủy booking và xóa phòng!");
            } else {
                System.out.println("Thất bại!");
            }

        } else {
            System.out.println("Đã hủy thao tác!");
        }
    }
}