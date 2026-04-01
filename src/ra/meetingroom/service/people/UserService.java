package ra.meetingroom.service.people;

import ra.meetingroom.dao.people.UserDAO;
import ra.meetingroom.model.people.User;
import ra.meetingroom.util.PasswordUtil;

public class UserService {
    private static UserDAO userDAO=new UserDAO();
    public User findById(int id) {
        return userDAO.findById(id);
    }
    public boolean updateProfile(User user) {

        User oldUser = userDAO.findById(user.getId());

        if (oldUser == null) {
            System.out.println("User không tồn tại!");
            return false;
        }

        // Check username trùng
        if (userDAO.existsByUsernameExceptId(user.getUsername(), user.getId())) {
            System.out.println("Username đã tồn tại!");
            return false;
        }

        // Check email trùng
        if (userDAO.existsByEmailExceptId(user.getEmail(), user.getId())) {
            System.out.println("Email đã tồn tại!");
            return false;
        }

        // Nếu password có nhập mới thì hash
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        } else {
            user.setPassword(oldUser.getPassword()); // giữ pass cũ
        }

        return userDAO.update(user);
    }
}
