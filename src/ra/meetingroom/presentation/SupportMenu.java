package ra.meetingroom.presentation;

import ra.meetingroom.model.people.User;
import ra.meetingroom.service.booking.AssignmentService;

import java.util.Scanner;

public class SupportMenu {
    Scanner sc=new Scanner(System.in);
    private AssignmentService assignmentService = new AssignmentService();
    public void show(User currentUser) {
        while (true) {
            System.out.println("""
========================= SUPPORT MENU =========================
|   1. Xem công việc           |    2. Cập nhật trạng thái     |
----------------------------------------------------------------
|                           0. Thoát                           |
----------------------------------------------------------------""");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("====================== LIST ASSIGNMENT ======================");
                    System.out.println("|  ID  |BOOKING ID| STAFT ID |    STATUS    |  ASSIGNED AT  |");
                    System.out.println("-------------------------------------------------------------");
                    assignmentService.getBySupport(currentUser.getId())
                            .forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 2:
                    updateStatus(currentUser);
                    break;
                case 0:
                    System.out.println("Bạn đã chọn thoát !!!");
                    return;
                default:
                    System.out.println("Lựa chọn của bạn không hợp lệ");
            }
        }
    }
    private void updateStatus(User currentUser) {

        System.out.print("Assignment ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.println("""
    1. Preparing
    2. Ready
    3. Missing
    """);

        int c = Integer.parseInt(sc.nextLine());

        String status = switch (c) {
            case 1 -> "PREPARING";
            case 2 -> "READY";
            default -> "MISSING";
        };

        boolean result = assignmentService.updateStatus(id, currentUser.getId(), status);

        if (result) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }
}
