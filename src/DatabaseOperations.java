import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseOperations {
    static Connection conn = null;
    static String server = "localhost";
    static String port = "3306";
    static String database = "student_database";
    static String username = "root";
    static String password = "";
    
    public static Connection initDatabase()
    {
        try {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           conn =
                DriverManager.getConnection(
                        "jdbc:mysql://" + 
                        server + 
                        ":" + 
                        port +
                        "/" + 
                        database + 
                        "?zeroDateTimeBehavior=convertToNull",
               username, 
                    password);
           return conn;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {            
            JOptionPane.showMessageDialog(null, "Unable to connect to local database.");
        }
        return null;
    }
    
    public static boolean addEntry(
        String table,
        String primary,
        String uniqueId,
        String firstName,
        String lastName,
        String position,
        String birthday,
        String mobile,
        String sex,
        String permanent,
        String present
    ) {
        PreparedStatement pst;
        try {
            String sql="insert into " + table +" (" + primary + ",First_name,Last_name,Position,Birth_day,Mobile_no,Sex,Permanent,Present) values(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, uniqueId);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, position);
            pst.setString(5, birthday);
            pst.setString(6, mobile);
            pst.setString(7, sex);
            pst.setString(8, permanent);
            pst.setString(9, present);

            pst.execute();
            return true;
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
    
    public static boolean addStudent(
        String rollNo,
        String firstName,
        String lastName,
        String className,
        String year,
        String birthday,
        String mobile,
        String sex,
        String permanent,
        String present
    )  {
        PreparedStatement pst;
        try {
            String sql="insert into student_info (Roll,First_name,Last_name,Class,Year,Birth_date,Mobile_no,Sex,permanent,Present) values(?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, rollNo);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, className);
            pst.setString(5, year);
            pst.setString(6, birthday);
            pst.setString(7, mobile);
            pst.setString(8, sex);
            pst.setString(9, permanent);
            pst.setString(10, present);
            
            pst.execute();
            return true;
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
    
    public static boolean updateEntry(
        String table,
        String primary,
        String uniqueId,
        String firstName,
        String lastName,
        String position,
        String birthday,
        String mobile,
        String sex,
        String permanent,
        String present
    )  {
        PreparedStatement pst;
        try {
            String sql = "Update " + table + " set " +
                    primary + "='" + uniqueId + 
                    "',First_name='" + firstName + 
                    "',Last_name='" + lastName + 
                    "',Position='" + position + 
                    "',Birth_day='" + birthday + 
                    "',Mobile_no='" + mobile + 
                    "',Sex='" + sex + 
                    "',permanent='" + permanent + 
                    "',Present='" + present + 
                    "' where " + primary + "='" + uniqueId + 
                    "'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
    
    public static boolean updateStudent(
        String rollNo,
        String firstName,
        String lastName,
        String className,
        String year,
        String birthday,
        String mobile,
        String sex,
        String permanent,
        String present
    )  {
        PreparedStatement pst;
        try {
            String sql =
                    "Update student_info set" + 
                    " Roll='"+rollNo+
                    "',First_name='"+firstName+
                    "',Last_name='"+lastName+
                    "',Class='"+className+
                    "', Year='"+year+
                    "',Birth_date='"+birthday+
                    "',Mobile_no='"+mobile+
                    "',Sex='"+sex+
                    "',permanent='"+permanent+
                    "',Present='"+present+
                    "' where Roll='"+rollNo+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
    
    public static ResultSet fetchStudent(String query) {
        ResultSet rs;
        PreparedStatement pst;
        
        String[] keys = new String[] { "Roll", "First_name", "Last_name", "Birth_date", "Mobile_no" };
        for (String key : keys) {
            try {
                String sql = "select * from student_info where " + key + "=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, query);
                rs = pst.executeQuery();
                if (rs.next()) { 
                    return rs;
                } else {
                    continue;
                }
            } catch (SQLException e) {
                continue;
            }
        }
        return null;
    }
}
  