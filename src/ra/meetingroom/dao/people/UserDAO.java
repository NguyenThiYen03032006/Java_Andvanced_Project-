package ra.meetingroom.dao.people;

import ra.meetingroom.model.people.Admin;
import ra.meetingroom.model.people.Employee;
import ra.meetingroom.model.people.SupportStaff;
import ra.meetingroom.model.people.User;
import ra.meetingroom.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");
                User user;

                switch (role) {
                    case "ADMIN":
                        user = new Admin();
                        break;
                    case "SUPPORT":
                        user = new SupportStaff();
                        break;
                    default:
                        user = new Employee();
                }

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(role);

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public User findByUsername(String username){
        try(
                Connection conn= DBConnection.openConnection();
                PreparedStatement pe=conn.prepareCall("SELECT * FROM users WHERE username= ?")
                ) {
            pe.setString(1,username);
            ResultSet re=pe.executeQuery();
            if(re.next()){
                User user= new Employee();
                user.setId(re.getInt("id"));
                user.setUsername(re.getString("username"));
                user.setPassword(re.getString("password"));
                user.setRole(re.getString("role"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // tim theo email
    public User findByEmail(String email){
        try(
                Connection conn= DBConnection.openConnection();
                PreparedStatement pe=conn.prepareCall("SELECT * FROM users WHERE email= ?")
        ) {
            pe.setString(1,email);
            ResultSet re=pe.executeQuery();
            if(re.next()){
                User user= new Employee();
                user.setId(re.getInt("id"));
                user.setUsername(re.getString("username"));
                user.setPassword(re.getString("password"));
                user.setRole(re.getString("role"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // them user
    public boolean insert(User user){
        try(
                Connection conn=DBConnection.openConnection();
                PreparedStatement pre=conn.prepareCall("INSERT INTO users (username,password,role,full_name,email) VALUES (?,?,?,?,?)");
                ){
            pre.setString(1,user.getUsername());
            pre.setString(2, user.getPassword());
            pre.setString(3, user.getRole());
            pre.setString(4,user.getFullName());
            pre.setString(5,user.getEmail());

            return pre.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 🔹 Update role
    public boolean updateRole(int userId, String role) {
        String sql = "UPDATE users SET role=? WHERE id=?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
                u.setEmail(rs.getString("email"));

                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
