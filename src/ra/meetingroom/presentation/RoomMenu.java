package ra.meetingroom.presentation;

import ra.meetingroom.model.room.Room;
import ra.meetingroom.service.room.RoomService;

import java.util.Scanner;

public class RoomMenu {

    private RoomService roomService = new RoomService();
    private Scanner sc = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("""
======================== ROOM MANAGEMENT ========================
|   1. Xem danh sách          |    2. Thêm phòng                |
-----------------------------------------------------------------
|   3. Sửa phòng              |    4. Xóa phòng                 |
-----------------------------------------------------------------
|                     0. Thoát                                  |
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
                    delete();
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
        roomService.getAllRooms().forEach(System.out::println);
    }

    // 🔹 Thêm
    private void add() {
        Room r = new Room();

        System.out.print("Tên phòng: ");
        r.setName(sc.nextLine());

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
        Room r = new Room();

        System.out.print("ID: ");
        r.setId(Integer.parseInt(sc.nextLine()));

        System.out.print("Tên mới: ");
        r.setName(sc.nextLine());

        System.out.print("Sức chứa: ");
        r.setCapacity(Integer.parseInt(sc.nextLine()));

        System.out.print("Vị trí: ");
        r.setLocation(sc.nextLine());

        System.out.print("Mô tả: ");
        r.setDescription(sc.nextLine());

        if (roomService.updateRoom(r)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }

    // 🔹 Xóa
    private void delete() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (roomService.deleteRoom(id)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }
}