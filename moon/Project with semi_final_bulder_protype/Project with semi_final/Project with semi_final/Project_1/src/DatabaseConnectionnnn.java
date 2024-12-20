import java.sql.*;
public class DatabaseConnectionnnn {
    



    private static Connection connection;

    // هذه الطريقة تقوم بإنشاء أو إرجاع الاتصال الموجود بقاعدة البيانات
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // إعداد بيانات الاتصال
            String url = "jdbc:mysql://localhost:3306/online_quiz_system"; // اسم قاعدة البيانات
            String username = "root"; // اسم المستخدم
            String password = ""; // كلمة المرور (افترض كلمة مرور فارغة هنا)

            // إنشاء الاتصال
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}




