package ra.meetingroom.presentation;

import ra.meetingroom.model.booking.Booking;
import ra.meetingroom.service.booking.AssignmentService;
import ra.meetingroom.service.booking.BookingService;
import ra.meetingroom.util.Validator;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class BookingMenu {

    private BookingService bookingService = new BookingService();
    private AssignmentService assignmentService=new AssignmentService();
    private Scanner sc = new Scanner(System.in);

    public void show(int userId, String role) {
        while (true) {
            System.out.println("\n===== BOOKING MENU =====");
            if ("EMPLOYEE".equals(role)) {
                System.out.println("1. Đặt phòng");
            }
            if ("ADMIN".equals(role)) {
                System.out.println("""
    1. Xem booking PENDING
    2. Duyệt booking + phân công support
    3. Từ chối booking
    """);
            }
            System.out.println("0. Thoát");
            int choice = Integer.parseInt(sc.nextLine());
            switch (role) {
                case "EMPLOYEE":
                    if (choice == 1) createBooking(userId);
                    else if (choice == 0) return;
                    break;
                case "ADMIN":
                    switch (choice) {
                        case 1:
                            viewAllBookings();
                            break;
                        case 2:
                            approveAndAssign();
                            break;
                        case 0:
                            return;
                    }
                    break;
            }
        }
    }

    private void createBooking(int userId) {
        int roomId;
        String input;
        do {
            System.out.print("Room ID: ");
            input = sc.nextLine();
        } while (!Validator.isPositiveInteger(input));

        roomId = Integer.parseInt(input);

        LocalDateTime start = inputStartDateTime();
        int duration = inputDurationMinutes();

        LocalDateTime end = start.plusMinutes(duration);

        //  Không cho vượt quá 17:00
        if (end.getHour() > 17 ||
                (end.getHour() == 17 && end.getMinute() > 0)) {
            System.out.println("Cuộc họp vượt quá giờ hành chính (17:00)!");
            return;
        }

        Booking b = new Booking();
        b.setUserId(userId);
        b.setRoomId(roomId);
        b.setStartTime(start);
        b.setEndTime(end);

        if (bookingService.createBooking(b)) {
            System.out.println("Đặt phòng thành công!");
        } else {
            System.out.println("Đặt phòng thất bại!");
        }
    }
    private LocalDateTime inputStartDateTime() {
        LocalDateTime now = LocalDateTime.now();

        int year;
        do {
            System.out.print("Năm (>= " + now.getYear() + "): ");
            year = Integer.parseInt(sc.nextLine());
        } while (year < now.getYear());

        int month;
        do {
            System.out.print("Tháng (1-12): ");
            month = Integer.parseInt(sc.nextLine());
        } while (month < 1 || month > 12 ||
                (year == now.getYear() && month < now.getMonthValue()));

        YearMonth yearMonth = YearMonth.of(year, month);
        int maxDay = yearMonth.lengthOfMonth();

        int day;
        do {
            System.out.print("Ngày (1-" + maxDay + "): ");
            day = Integer.parseInt(sc.nextLine());
        } while (day < 1 || day > maxDay ||
                (year == now.getYear() &&
                        month == now.getMonthValue() &&
                        day < now.getDayOfMonth()));

        int hour;
        do {
            System.out.print("Giờ bắt đầu (8 - 16): ");
            hour = Integer.parseInt(sc.nextLine());
        } while (hour < 8 || hour > 16); // 16 để còn cộng thời gian

        int minute;
        do {
            System.out.print("Phút (0-59): ");
            minute = Integer.parseInt(sc.nextLine());
        } while (minute < 0 || minute > 59);

        LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);

        if (start.isBefore(now)) {
            System.out.println("Không được đặt thời gian trong quá khứ!");
            return inputStartDateTime();
        }

        return start;
    }
    private int inputDurationMinutes() {
        int duration;
        do {
            System.out.print("Thời lượng cuộc họp (30 / 60 / 90 / 120 phút): ");
            duration = Integer.parseInt(sc.nextLine());
        } while (duration <= 0 || duration % 30 != 0);

        return duration;
    }

    //  Admin xem
    private void viewAllBookings() {
        bookingService.getAll().forEach(System.out::println);
    }

    private void approveAndAssign() {

        List<Booking> list = bookingService.getPending();

        list.forEach(System.out::println);

        System.out.print("Nhập ID booking: ");
        int bookingId = Integer.parseInt(sc.nextLine());

        // duyệt trước
        boolean approved = bookingService.approveBooking(bookingId);

        if (!approved) return;

        // gán support
        System.out.print("Nhập ID support: ");
        int supportId = Integer.parseInt(sc.nextLine());

        assignmentService.assign(bookingId, supportId);

        System.out.println("Đã duyệt và phân công!");
    }
    private void rejectBooking() {
        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(sc.nextLine());

        bookingService.updateStatus(id, "REJECTED");
    }
}