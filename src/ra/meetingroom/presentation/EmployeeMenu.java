package ra.meetingroom.presentation;

import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.model.people.Employee;
import ra.meetingroom.model.people.User;
import ra.meetingroom.service.booking.BookingService;
import ra.meetingroom.service.booking.AssignmentService;
import ra.meetingroom.service.people.UserService;
import ra.meetingroom.service.room.RoomService;
import ra.meetingroom.util.Validator;

import java.util.List;
import java.util.Scanner;

import static ra.meetingroom.Main.currentUser;

public class EmployeeMenu {
    Scanner sc=new Scanner(System.in);
    private RoomService roomService=new RoomService();
    private BookingService bookingService=new BookingService();
    private AssignmentService assignmentService=new AssignmentService();
    private UserService userService=new UserService();
    public void show(){
        while (true){
            System.out.println("""
========================= EMPLOYEE MENU =========================
|   1. Xem phòng trống          |    2. Tạo yêu cầu đặt phòng   |
-----------------------------------------------------------------
|                3. Danh sách booking của bản thân              |
-----------------------------------------------------------------
|                4. Chỉnh sửa thông tin cá nhân                 |
-----------------------------------------------------------------
|                    0. Thoát                                   |
-----------------------------------------------------------------""");
            System.out.print("Lựa chọn của bạn: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn hợp lệ!");
                continue;
            }
            switch (choice) {
                case 1:
                    roomService.showRoomsWithStatus();
                    break;
                case 2:
                    new BookingMenu().show(currentUser.getId(), "EMPLOYEE");
                    break;
                case 3:
                    viewMyBookings();
                    break;
                case 4:
                    updateProfile(currentUser);
                    break;
                case 0:
                    System.out.println("Bạn đã chọn thoát !!!");
                    return;
                default:
                    System.out.println("Lựa chọn của bạn không hợp lệ");
            }
        }
    }
    private void viewMyBookings() {

        List<Booking> list = bookingService.getByUser(currentUser.getId());

        for (Booking b : list) {

            System.out.println(b);

            String status = assignmentService.getStatusByBooking(b.getId());

            if (status != null) {
                System.out.println(" Trạng thái phòng: " + status);
            }
        }
    }
    private void updateProfile(User currentUser) {

        // Load lại từ DB để đảm bảo đủ dữ liệu
        User updatedUser = userService.findById(currentUser.getId());

        while (true) {

            System.out.println("""
        ===== CẬP NHẬT THÔNG TIN =====
        1. Username
        2. Fullname
        3. Email
        4. Password
        5. Phone
        0. Lưu & thoát
        """);

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Username mới: ");
                    String username = sc.nextLine();
                    if (Validator.requireNotEmpty(username, "Username")) {
                        updatedUser.setUsername(username);
                    }
                    break;

                case 2:
                    System.out.print("Fullname mới: ");
                    String fullname = sc.nextLine();
                    if (Validator.requireNotEmpty(fullname, "Fullname")) {
                        updatedUser.setFullName(fullname);
                    }
                    break;

                case 3:
                    System.out.print("Email mới: ");
                    String email = sc.nextLine();
                    if (Validator.requireValidEmail(email)) {
                        updatedUser.setEmail(email);
                    }
                    break;

                case 4:
                    System.out.print("Password mới: ");
                    String pass = sc.nextLine();
                    if (Validator.requireValidPassword(pass)) {
                        updatedUser.setPassword(pass);
                    }
                    break;

                case 5:
                    System.out.print("Phone mới: ");
                    String phone = sc.nextLine();
                    if (Validator.requireValidPhone(phone)) {
                        updatedUser.setPhone(phone);
                    }
                    break;

                case 0:
                    boolean result = userService.updateProfile(updatedUser);

                    if (result) {
                        System.out.println("Cập nhật thành công!");

                        // 🔥 cập nhật lại currentUser
                        currentUser.setUsername(updatedUser.getUsername());
                        currentUser.setFullName(updatedUser.getFullName());
                        currentUser.setEmail(updatedUser.getEmail());
                        currentUser.setPhone(updatedUser.getPhone());

                    } else {
                        System.out.println("Cập nhật thất bại!");
                    }
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

}
