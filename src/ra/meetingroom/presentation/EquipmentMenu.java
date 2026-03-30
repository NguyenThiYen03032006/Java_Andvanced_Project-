package ra.meetingroom.presentation;


import ra.meetingroom.model.room.Equipment;
import ra.meetingroom.service.room.EquipmentService;

import java.util.List;
import java.util.Scanner;

public class EquipmentMenu {

    private EquipmentService service = new EquipmentService();
    private Scanner sc = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ THIẾT BỊ =====");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm thiết bị");   // thêm
            System.out.println("3. Sửa số lượng");
            System.out.println("4. Xóa thiết bị");
            System.out.println("0. Thoát");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: showList(); break;
                case 2: add(); break;        // thêm
                case 3: update(); break;
                case 4: delete(); break;
                case 0: return;
            }
        }
    }

    // 🔹 Xem danh sách
    private void showList() {
        List<Equipment> list = service.getAll();

        for (Equipment e : list) {
            System.out.println(e.getId() + " | " + e.getName()
                    + " | Tổng số lượng: " + e.getTotalQuantity()
                    + " | Số lượng khả dụng: " + e.getAvailableQuantity()
                    + " | " + e.getStatus());
        }
    }
    private void add() {
        Equipment e = new Equipment();

        System.out.print("Tên thiết bị: ");
        e.setName(sc.nextLine());

        System.out.print("Tổng số lượng: ");
        e.setTotalQuantity(Integer.parseInt(sc.nextLine()));

        System.out.print("Số lượng khả dụng: ");
        e.setAvailableQuantity(Integer.parseInt(sc.nextLine()));

        System.out.print("Trạng thái: ");
        e.setStatus(sc.nextLine());

        if (service.addEquipment(e)) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    // 🔹 Sửa
    private void update() {
        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Tổng số lượng mới: ");
        int total = Integer.parseInt(sc.nextLine());

        System.out.print("Số lượng khả dụng mới: ");
        int available = Integer.parseInt(sc.nextLine());

        if (service.updateQuantity(id, total, available)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }

    // 🔹 Xóa
    private void delete() {
        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (service.delete(id)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }
}
