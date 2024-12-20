//DB UPDATE
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Student implements User {
    @Override
    public void register(String id, String username, String password) {
        try {
            // الاتصال بقاعدة البيانات
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/online_quiz_system", "root", "");

            // التحقق من وجود id مسبقًا
            String checkQuery = "SELECT COUNT(*) FROM students WHERE student_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // عرض رسالة تنبيه للمستخدم باستخدام JOptionPane
                JOptionPane.showMessageDialog(null, "This ID is used before. Choose another ID.", "ERROR", JOptionPane.ERROR_MESSAGE);
                rs.close();
                checkStmt.close();
                conn.close();
                return; // إنهاء العملية إذا كان الـ ID مستخدم مسبقًا
            }

            rs.close();
            checkStmt.close();

            // إدخال البيانات الجديدة
            String insertQuery = "INSERT INTO students (student_id, username, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, id);
            insertStmt.setString(2, username);
            insertStmt.setString(3, password);

            insertStmt.executeUpdate();
            // عرض رسالة نجاح
            JOptionPane.showMessageDialog(null, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            insertStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // عرض رسالة خطأ في حالة حدوث استثناء
            JOptionPane.showMessageDialog(null, "An error occurred while connecting to the database.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
