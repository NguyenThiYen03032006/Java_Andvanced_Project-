package ra.meetingroom.util;

import java.util.regex.Pattern;

public class Validator {

    // check rỗng
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    // check rỗng
    public static boolean requireNotEmpty(String input, String fieldName) {
        if (isEmpty(input)) {
            System.out.println(fieldName + " không được để trống!");
            return false;
        }
        return true;
    }

    // check email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean requireValidEmail(String email) {
        if (!isValidEmail(email)) {
            System.out.println("Email không hợp lệ!");
            return false;
        }
        return true;
    }

    // số nhập vào phải >0
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }

    public static boolean requirePositive(int number, String fieldName) {
        if (number <= 0) {
            System.out.println(fieldName + " phải > 0!");
            return false;
        }
        return true;
    }

    // check password
    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 6;
    }

    public static boolean requireValidPassword(String password) {
        if (!isValidPassword(password)) {
            System.out.println("Password phải >= 6 ký tự!");
            return false;
        }
        return true;
    }
    // check số nguyên dương từ String
    public static boolean isPositiveInteger(String input) {
        if (isEmpty(input)) return false;
        try {
            int number = Integer.parseInt(input.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập vào không hợp lệ");
            return false;
        }
    }

}