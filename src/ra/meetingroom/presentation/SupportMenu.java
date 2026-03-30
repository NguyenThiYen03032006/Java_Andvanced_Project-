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
        SUPPORT MENU
        1. Xem công việc
        2. Cập nhật trạng thái
        0. Thoát
        """);
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    assignmentService.getBySupport(currentUser.getId())
                            .forEach(System.out::println);
                    break;
                case 2:
                    updateStatus();
                    break;
                case 0:
                    return;
            }
        }
    }
    private void updateStatus() {

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

        assignmentService.updateStatus(id, status);
    }
}
