package ra.meetingroom.presentation;

import ra.meetingroom.service.people.AdminService;
import ra.meetingroom.service.room.EquipmentService;
import ra.meetingroom.service.room.RoomService;
import ra.meetingroom.util.Validator;

import java.util.Scanner;

import static ra.meetingroom.Main.currentUser;


public class AdminMenu {

    private RoomService roomService = new RoomService();
    private EquipmentService equipmentService = new EquipmentService();
    private AdminService adminService = new AdminService();

    private Scanner sc = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Quản lý phòng");
            System.out.println("2. Cập nhật thiết bị");
            System.out.println("3. Tạo tài khoản Support");
            System.out.println("4. Quản lý booking");
            System.out.println("0. Thoát");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: new RoomMenu().show(); break;
                case 2: new EquipmentMenu().show();; break;
                case 3: createSupport(); break;
                case 4:
                    new BookingMenu().show(currentUser.getId(), "ADMIN");
                    break;
                case 0: return;
            }
        }
    }
    //  CREATE SUPPORT
    private void createSupport() {
        System.out.println("""
            =============================
            1. Tạo tài khoản Support mới
            2. Chuyển từ Employee
            0. Thoát
            """);
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                createNewSupport();
                break;
            case 2:
                promoteEmployee();
                break;
        }
    }
    private void createNewSupport() {
        String fullname;
        do{
            System.out.println("Nhap full name: ");
            fullname=sc.nextLine();
        }while (!Validator.requireNotEmpty(fullname, "Username" ));

        String username;
        do{
            System.out.println("Nhap username: ");
            username= sc.nextLine();
        }while(!Validator.requireNotEmpty(username, "Username"));

        String email;
        do{
            System.out.println("Nhap email: ");
            email=sc.nextLine();
        }while (!Validator.requireValidEmail(email));

        String pass;
        do{
            System.out.println("Nhap password: ");
            pass=sc.nextLine();
        }while (!Validator.requireValidPassword(pass));

        if (adminService.createSupport(username, pass, fullname,pass)) {
            System.out.println("Tạo thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }
    private void promoteEmployee() {
        System.out.println("Danh sách Employee:");
        adminService.getAllEmployee().forEach(u ->
                System.out.println(u.getId() + " - " + u.getUsername() + " - " + u.getFullName())
        );

        System.out.print("Nhập ID cần nâng: ");
        int id = Integer.parseInt(sc.nextLine());
        if (adminService.promoteToSupport(id)) {
            System.out.println("Nâng quyền thành công!");
        } else {
            System.out.println("Thất bại!");
        }
    }
}