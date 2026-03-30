package ra.meetingroom.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;

public class PasswordUtil {
    private static final int COST = 12;
    // Hash mật khẩu
    public static String hashPassword(String plainPassword) {
        if(plainPassword == null || plainPassword.length() < 6) {
           // throw new IllegalArgumentException("Password phải từ 6 ký tự");
            System.out.println("Password phai tu 6 ky tu");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Kiểm tra mật khẩu khi login
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            System.out.println("Mat khau khong duoc de trong");
            return false; // hoặc throw exception tùy logic
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /*
    Dang ky
    public void register(String username, String password) {

    String hashedPassword = PasswordUtil.hashPassword(password);

    User user = new User();
    user.setUsername(username);
    user.setPassword(hashedPassword);

    userDAO.insert(user);


    dang nhap
    public boolean login(String username, String password) {

    User user = userDAO.findByUsername(username);

    if(user == null) {
        return false;
    }

    return PasswordUtil.checkPassword(password, user.getPassword());
}
}

     */



    // 🔹 Hàm mã hóa SHA-256
//    public static String hashPassword(String password) {
//        try {
//            // tao bo ma hoa SHA-256
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            // convert password -> dang byte -> dem di hash + mang byte kho doc
//            byte[] hashBytes = md.digest(password.getBytes());
//            // tao chuoi de chuyen by-> dang hex de luu DB
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error while hashing password");
//        }
//    }
//
//    //  So sánh password khi login
//    public static boolean checkPassword(String inputPassword, String storedPassword) {
//        String hashedInput = hashPassword(inputPassword);
//        return hashedInput.equals(storedPassword);
//    }
}
/*
khi dang ky
String rawPassword = "123456";
String hashed = PasswordUtil.hashPassword(rawPassword);

// lưu hashed vào DB
 */
/*
Khi dang nhap
String inputPassword = "123456";
String storedPassword = user.getPassword();

if (PasswordUtil.checkPassword(inputPassword, storedPassword)) {
    System.out.println("Login success");
} else {
    System.out.println("Sai mật khẩu");
}
 */


/*
Lưu ý: logic mã hóa mật khẩu trên
- 2 mật khẩu giống nhau sẽ đc mã hóa ra kq giống nhau
=> trong thực tế sẽ dễ bị tấn công
 */