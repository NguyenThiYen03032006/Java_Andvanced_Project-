package ra.meetingroom.presentation;

import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.service.booking.BookingService;
import ra.meetingroom.service.booking.AssignmentService;
import ra.meetingroom.service.room.RoomService;

import java.util.List;
import java.util.Scanner;

import static ra.meetingroom.Main.currentUser;

public class EmployeeMenu {
    Scanner sc=new Scanner(System.in);
    private RoomService roomService=new RoomService();
    private BookingService bookingService=new BookingService();
    private AssignmentService assignmentService=new AssignmentService();
    public void show(){
        while (true){
            System.out.println("""
                EMPLOYEE MENU
                1. Xem phòng trống
                2. Tạo yêu cầu đặt phòng
                3. Danh sách booking của bản thân
                0. Thoát
                """);

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    roomService.showRoomsWithStatus();
                    break;
                case 2:
                    new BookingMenu().show(currentUser.getId(), "EMPLOYEE");
                    break;
                case 3:
                    viewMyBookings();

                case 0:
                    return; // chỉ thoát EmployeeMenu

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
                System.out.println("👉 Trạng thái phòng: " + status);
            }
        }
    }

}
