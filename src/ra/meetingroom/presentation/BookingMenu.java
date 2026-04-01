package ra.meetingroom.presentation;

import ra.meetingroom.dao.booking.BookingEquipmentDAO;
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
    BookingEquipmentDAO bookingEquipmentDAO = new BookingEquipmentDAO();
    private Scanner sc = new Scanner(System.in);

    public void show(int userId, String role) {
        while (true) {
            if ("EMPLOYEE".equals(role)) {
                System.out.println("""
========================= BOOKING MENU =========================
|   1. Đặt phòng                                               |
---------------------------------------------------------------- 
|                    0. Thoát                                  |
----------------------------------------------------------------""");
                System.out.print("Lựa chọn của bạn: ");
            }
            if ("ADMIN".equals(role)) {
                System.out.println("""
========================= BOOKING MENU =========================
|   1. Xem booking PENDING     |    2. Duyệt booking           |
----------------------------------------------------------------
|   3. Từ chối booking         |    4. Phân công support       |
---------------------------------------------------------------- 
|                    0. Thoát                                  |
----------------------------------------------------------------""");
                System.out.print("Lựa chọn của bạn: ");
            }
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn hợp lệ!");
                continue;
            }
            switch (role) {
                case "EMPLOYEE":
                    if (choice == 1) createBooking(userId);
                    else if (choice == 0) return;
                    break;
                case "ADMIN":
                    switch (choice) {
                        case 1:
                            viewPendingBookings();
                            break;
                        case 2:
                            approveBooking();
                            break;
                        case 3:
                            rejectBooking();
                            break;
                        case 4:
                            assignSupport();
                            break;
                        case 0:
                            System.out.println("Bạn đã chọn thoát !!!");
                            return;
                        default:
                            System.out.println("Lựa chọn của bnaj không hợp lệ");
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

        int bookingId = bookingService.createBooking(b);

        if (bookingId > 0) {
            System.out.println("Đặt phòng thành công! ID = " + bookingId);

            System.out.print("Thêm thiết bị? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {

                while (true) {
                    System.out.print("Equipment ID (0 để thoát): ");
                    int equipmentId = Integer.parseInt(sc.nextLine());
                    if (equipmentId == 0) break;

                    System.out.print("Số lượng: ");
                    int qty = Integer.parseInt(sc.nextLine());

                    bookingEquipmentDAO.insert(bookingId, equipmentId, qty);
                }
            }

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
    private void viewPendingBookings() {
        List<Booking> list = bookingService.getPending();

        if (list.isEmpty()) {
            System.out.println("Không có booking PENDING!");
            return;
        }

        list.forEach(System.out::println);
    }

    private void approveBooking() {

        List<Booking> list = bookingService.getPending();

        if (list.isEmpty()) {
            System.out.println("Không có booking PENDING!");
            return;
        }

        list.forEach(System.out::println);

        System.out.print("Nhập ID cần duyệt: ");
        int id = Integer.parseInt(sc.nextLine());

        if (bookingService.approveBooking(id)) {
            System.out.println("Duyệt thành công!");
        } else {
            System.out.println("Duyệt thất bại!");
        }
    }
    private void assignSupport() {

        List<Booking> list = bookingService.getApproved();

        if (list.isEmpty()) {
            System.out.println("Không có booking APPROVED!");
            return;
        }

        list.forEach(System.out::println);

        System.out.print("Nhập ID booking: ");
        int bookingId = Integer.parseInt(sc.nextLine());

        System.out.print("Nhập ID support: ");
        int supportId = Integer.parseInt(sc.nextLine());

        boolean result = assignmentService.assign(bookingId, supportId);

        if (result) {
            System.out.println("Phân công thành công!");
        } else {
            System.out.println("Phân công thất bại!");
        }
    }
    private void rejectBooking() {

        List<Booking> list = bookingService.getPending();

        if (list.isEmpty()) {
            System.out.println("Không có booking PENDING!");
            return;
        }

        list.forEach(System.out::println);

        System.out.print("Nhập ID cần từ chối: ");
        int id = Integer.parseInt(sc.nextLine());

        if (bookingService.updateStatus(id, "REJECTED")) {
            System.out.println("Đã từ chối!");
        } else {
            System.out.println("Thất bại!");
        }
    }
}