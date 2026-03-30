package ra.meetingroom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String DRIVER="com.mysql.cj.jdbc.Driver";
    private static String URL="jdbc:mysql://localhost:3306/meeting_room_db";
    private static String USERNAME="root";
    private static String PASSWORD="yen030306.";
    public static Connection openConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Chua cai dat mysql driver");
        } catch (SQLException e) {
            System.err.println("Loi SQL: Ket noi that bai");
            e.printStackTrace();
        }
        return null;
    }
}
