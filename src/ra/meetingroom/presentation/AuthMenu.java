package ra.meetingroom.presentation;

import ra.meetingroom.dao.people.UserDAO;
import ra.meetingroom.model.people.User;
import ra.meetingroom.service.auth.AuthService;
import ra.meetingroom.util.Validator;

import java.util.Scanner;

public class AuthMenu {
    private AuthService authService=new AuthService();
    private static UserDAO userDAO=new UserDAO();
    private Scanner sc=new Scanner(System.in);

    public User showMenuAuth(){
        while (true){
            System.out.println("""
            ============ DANG KY || DANG NHAP ============
            |  1. DANG KY  |  2. DANG NHAP  |  0. THOAT  |
            ==============================================        """);
            System.out.print("Lựa chọn của bạn: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn hợp lệ!");
                continue;
            }
            switch (choice){
                case 1:
                    register();
                    break;
                case 2:
                    User user=login();
                    if(user!=null){
                        return user;
                    }
                    break;
                case 0:
                    System.out.println("Bạn đã chọn dừng chương trình !!!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn của bạn không hợp lệ");
            }
        }
    }

    private void register(){
        String fullname;
        do{
            System.out.println("Nhập full name: ");
            fullname=sc.nextLine();
        }while (!Validator.requireNotEmpty(fullname, "Username"));

        String username;
        while (true){
            System.out.println("Nhập username: ");
            username= sc.nextLine();
            //trong
            if(!Validator.requireNotEmpty(username, "Username")){
                continue;
            }
            // trung username
            if(userDAO.findByUsername(username)!=null){
                System.out.println("Username đã tồn tại!!");
                continue;
            }
            break;
        };

        String email;
        while (true){
            System.out.println("Nhập email: ");
            email=sc.nextLine();
            //trong
            if(!Validator.requireValidEmail(email)){
                continue;
            }
            // trung email
            if(userDAO.findByEmail(email)!=null){
                System.out.println("Email đã được đăng ký");
                continue;
            }
            break;
        }

        String pass;
        do{
            System.out.println("Nhập mật khẩu: ");
            pass=sc.nextLine();
        }while (!Validator.requireValidPassword(pass));

        String phone;
        while (true){
            System.out.println("Nhập số điện thoại: ");
            phone=sc.nextLine();
            // trong
            if(!Validator.requireNotEmpty(phone, "Phone")){
                continue;
            }
            // trung phone
            if(userDAO.findByPhone(phone)!=null){
                System.out.println("Số điện thoại đã được đăng ký");
                continue;
            }
            // định dạng
            if (!Validator.requireValidPhone(phone)) {
                continue;
            }
            break;
        }

//        System.out.println("Chọn chức vụ:");
//        System.out.println("1. Employee");
//        System.out.println("2. Support");
//        System.out.println("3. Admin");
//
//        int choice = Integer.parseInt(sc.nextLine());
//
//        String role;
//        switch (choice) {
//            case 1:
//                role="EMPLOYEE";
//            case 2:
//                role = "SUPPORT";
//                break;
//            default:
//                role = "ADMIN";
//        }

        // mac dinh khi dang ky la employee
        String role="EMPLOYEE";
        boolean check=authService.register(username,pass,fullname,role,email,phone);
        if(check){
            System.out.println("Bạn đã đang ký thành công");
        }else{
            System.out.println("Đăng ký thất bại");
        }
    }

    private User login(){
        String username;
        do{
            System.out.println("Nhap username: ");
            username= sc.nextLine();
        }while(!Validator.requireNotEmpty(username, "Username"));

        String pass;
        do{
            System.out.println("Nhap password: ");
            pass=sc.nextLine();
        }while (!Validator.requireValidPassword(pass));

        return authService.login(username,pass);
    }

}
