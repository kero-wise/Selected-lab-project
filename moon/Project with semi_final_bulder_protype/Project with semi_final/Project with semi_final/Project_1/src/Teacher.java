//DB UPDATE
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Teacher implements User {
    @Override
    public void register(String id, String username, String password) {
        try {
            // الاتصال بقاعدة البيانات
            String url = "jdbc:mysql://localhost:3306/online_quiz_system";
            String dbUser = "root"; // اسم المستخدم
            String dbPassword = ""; // كلمة المرور

            Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);

            // التحقق من وجود id مسبقًا
            String checkQuery = "SELECT COUNT(*) FROM teachers WHERE teacher_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // عرض رسالة تنبيه للمستخدم باستخدام JOptionPane
                JOptionPane.showMessageDialog(null, "This ID is used Before Choose another ID", "ERROR", JOptionPane.ERROR_MESSAGE);
                rs.close();
                checkStmt.close();
                conn.close();
                return; // إنهاء العملية
            }

            rs.close();
            checkStmt.close();

            // إدخال البيانات الجديدة
            String insertQuery = "INSERT INTO teachers (teacher_id, username, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, id);
            insertStmt.setString(2, username);
            insertStmt.setString(3, password);

            insertStmt.executeUpdate();
            // عرض رسالة نجاح للمستخدم
            JOptionPane.showMessageDialog(null, "تم تسجيل المعلم بنجاح!", "نجاح", JOptionPane.INFORMATION_MESSAGE);

            insertStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // عرض رسالة خطأ في حالة حدوث استثناء
            JOptionPane.showMessageDialog(null, "حدث خطأ أثناء الاتصال بقاعدة البيانات.", "خطأ", JOptionPane.ERROR_MESSAGE);
        }
    }
}
