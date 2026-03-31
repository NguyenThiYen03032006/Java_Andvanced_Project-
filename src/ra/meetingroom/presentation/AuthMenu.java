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
            int choice=Integer.parseInt(sc.nextLine());
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
                case 3:
                    System.out.println("Bạn đã chọn thoát !!!");
                    break;
                default:
                    System.out.println("Lựa chọn của bạn không hợp lệ");
            }
        }
    }

    private void register(){
        String fullname;
        do{
            System.out.println("Nhap full name: ");
            fullname=sc.nextLine();
        }while (!Validator.requireNotEmpty(fullname, "Username"));

        String username;
        do{
            System.out.println("Nhap username: ");
            username= sc.nextLine();
        }while(!Validator.requireNotEmpty(username, "Username") && userDAO.findByUsername(username)!=null);// validate dulieu dau vao + check trung

        String email;
        do{
            System.out.println("Nhap email: ");
            email=sc.nextLine();
        }while (!Validator.requireValidEmail(email)&& userDAO.findByEmail(email)!=null);// validate dulieu dau vao + check trung

        String pass;
        do{
            System.out.println("Nhap password: ");
            pass=sc.nextLine();
        }while (!Validator.requireValidPassword(pass));

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
        boolean check=authService.register(username,pass,fullname,role,email);
        if(check){
            System.out.println(" Dang ky thanh cong");
        }else{
            System.out.println("Dang ky that bai");
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
