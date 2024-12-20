import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
// factory and protype
class QuestionFactory {
    // طريقة لإنشاء الأسئلة
    public static Question createQuestion(String type, Object... params) {
        switch (type) {
            case "MultipleChoice":
                return new MultipleChoiceQuestion(
                        (String) params[0],
                        (String) params[1],
                        (String) params[2],
                        (String) params[3],
                        (String) params[4],
                        (int) params[5]
                );
            case "TrueFalse":
                return new TrueFalseQuestion(
                        (String) params[0],
                        (boolean) params[1]
                );
            default:
                throw new IllegalArgumentException("Unknown question type: " + type);
        }
    }

    // طريقة لحفظ السؤال في قاعدة البيانات
    public static boolean saveQuestionToDatabase(Question question) {
        try (Connection conn = DatabaseConnectionnnn.getConnection()) {
            String query = "INSERT INTO questions (question_text, question_type) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getClass().getSimpleName());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
