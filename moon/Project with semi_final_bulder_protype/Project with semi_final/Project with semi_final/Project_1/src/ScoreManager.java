//score singliton 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class ScoreManager {
    private static ScoreManager instance;  // النسخة الوحيدة من الكلاس
    private HashMap<String, Integer> studentScores = new HashMap<>();  // لتخزين النتيجة مؤقتًا

    // جعل المُنشئ خاصًا لعدم السماح بإنشاء كائنات جديدة
    private ScoreManager() {}

    // طريقة للوصول إلى النسخة الوحيدة
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public void recordScore(String studentId, int score) {
        studentScores.put(studentId, score);  // إضافة النتيجة إلى الذاكرة المؤقتة

        try (Connection conn = DatabaseConnectionnnn.getConnection()) {
            if (conn != null) {
                System.out.println("Connection successful!");
            }

            String query = "INSERT INTO student_scores (student_id, exam_id, score, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentId);
            stmt.setInt(2, 1);  // فرضًا أن examId هو 1، عدله إذا لزم الأمر
            stmt.setInt(3, score);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Score inserted successfully!");
            } else {
                System.out.println("No rows affected.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
