package ra.meetingroom.model.people;
// abstract class
public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String role;
    protected String fullName;
    protected String email;
    protected String phone;
    protected String department;

    public User() {
    }

    public User(String department, String email, String fullName, int id, String password, String phone, String role, String username) {
        this.department = department;
        this.email = email;
        this.fullName = fullName;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
