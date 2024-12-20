//SINGLITON HERE == QUIZ MANGER
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TeacherExamPage {
    private static TeacherExamPage instance; // Singleton instance
    private ExamPageState currentState; // Current state of the page
    private static ArrayList<TeacherQuestion> teacherQuestions = new ArrayList<>();

    // Private constructor لمنع إنشاء كائنات جديدة
    private TeacherExamPage() {
        this.currentState = new ViewQuestionsState(); // Default state
    }

    // Public method للوصول إلى النسخة الوحيدة من الكلاس
    public static TeacherExamPage getInstance() {
        if (instance == null) {
            instance = new TeacherExamPage();
        }
        return instance;
    }

    // عرض الصفحة بناءً على الحالة الحالية
    public void display() {
        JFrame frame = new JFrame("Teacher Exams");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Choose an action:");
        frame.add(label);

        JButton viewQuestionsButton = new JButton("View Questions");
        JButton addExamButton = new JButton("Add New Exam");

        // عند الضغط على زر عرض الأسئلة
        viewQuestionsButton.addActionListener(e -> {
            setState(new ViewQuestionsState());
            display(); // عرض الواجهة للحالة الجديدة
        });

        // عند الضغط على زر إضافة امتحان جديد
        addExamButton.addActionListener(e -> {
            setState(new AddExamState());
            display(); // عرض الواجهة للحالة الجديدة
        });

        frame.add(viewQuestionsButton);
        frame.add(addExamButton);

        frame.setVisible(true);
    }

    // تغيير الحالة
    public void setState(ExamPageState state) {
        this.currentState = state;
    }

    // استرجاع الأسئلة
    public ArrayList<TeacherQuestion> getTeacherQuestions() {
        return teacherQuestions;
    }

    void addQuestionToDatabase(String question, String option1, String option2, String option3, String option4, int correctOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String formatQuestion(TeacherQuestion question) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
