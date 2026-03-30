package ra.meetingroom.service.people;

import ra.meetingroom.dao.people.UserDAO;
import ra.meetingroom.model.people.Employee;
import ra.meetingroom.model.people.SupportStaff;
import ra.meetingroom.model.people.User;
import ra.meetingroom.util.DBConnection;
import ra.meetingroom.util.PasswordUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private UserDAO userDAO = new UserDAO();

    public boolean createSupport(String username, String password, String fullName,String email) {

        if (userDAO.findByUsername(username) != null) {
            System.out.println("Username đã tồn tại!");
            return false;
        }

        User user = new SupportStaff();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRole("SUPPORT");
        user.setFullName(fullName);
        user.setEmail(email);

        return userDAO.insert(user);
    }
    public List<User> getAllEmployee() {
        return userDAO.findAllEmployee();
    }
    public List<User> findAllEmployee() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'EMPLOYEE'";

        try (Connection conn = DBConnection.openConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User u = new Employee();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setFullName(rs.getString("full_name"));
                u.setRole(rs.getString("role"));

                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean promoteToSupport(int userId) {

        User user = userDAO.findById(userId);

        if (user == null) {
            System.out.println("Không tìm thấy user!");
            return false;
        }

        if (!user.getRole().equals("EMPLOYEE")) {
            System.out.println("Chỉ Employee mới được nâng!");
            return false;
        }

        return userDAO.updateRole(userId, "SUPPORT");
    }
}
