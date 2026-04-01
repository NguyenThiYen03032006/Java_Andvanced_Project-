package ra.meetingroom.service.auth;

import ra.meetingroom.dao.people.UserDAO;
import ra.meetingroom.model.people.Employee;
import ra.meetingroom.model.people.User;
import ra.meetingroom.presentation.AdminMenu;
import ra.meetingroom.presentation.EmployeeMenu;
import ra.meetingroom.presentation.EquipmentMenu;
import ra.meetingroom.presentation.SupportMenu;
import ra.meetingroom.util.PasswordUtil;
import ra.meetingroom.util.Validator;

public class AuthService {
    private static UserDAO userDAO=new UserDAO();
    // dang ky
    public boolean register(String username,String pass,String fullName,String role,String email,String phone){

        // hash pass
        String hash= PasswordUtil.hashPassword(pass);
        // tao user
        User user=new Employee();

        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hash);
        user.setRole(role);
        user.setPhone(phone);

        return userDAO.insert(user);
    }
    // dang nhap
    public User login(String username, String pass) {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            System.out.println("Khong tim thay user!");
            return null;
        }

        if (PasswordUtil.checkPassword(pass, user.getPassword())) {
            System.out.println("Dang nhap thanh cong");
            return user; // ✅ trả user
        } else {
            System.out.println("Sai password");
            return null;
        }
    }

}
