// عدلت هنا عشان teacherexampage يتربط ويبقي سنجلتون 
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentExamPage {
    public static void display() {
        JFrame frame = new JFrame("Student Exams");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton teacherExamButton = new JButton("Teacher Exams");
        teacherExamButton.addActionListener(e -> startTeacherExam());

        frame.add(teacherExamButton);

        frame.setVisible(true);
    }

    public static void startTeacherExam() {
        // الحصول على النسخة الوحيدة من TeacherExamPage
        TeacherExamPage teacherExamPage = TeacherExamPage.getInstance();

        // استرجاع قائمة الأسئلة
        ArrayList<TeacherQuestion> teacherQuestions = teacherExamPage.getTeacherQuestions();

        if (teacherQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No teacher exams available.");
            return;
        }

        // إنشاء نافذة الامتحان
        JFrame examFrame = new JFrame("Teacher Exam");
        examFrame.setSize(400, 600);
        examFrame.setLayout(new BoxLayout(examFrame.getContentPane(), BoxLayout.Y_AXIS));

        AtomicInteger currentQuestionIndex = new AtomicInteger(0); // للسؤال الحالي
        AtomicInteger score = new AtomicInteger(0); // النتيجة النهائية

        // إظهار السؤال الأول
        final TeacherQuestion[] currentQuestion = {teacherQuestions.get(currentQuestionIndex.get())};
        final JPanel[] questionPanel = {createQuestionPanel(currentQuestion[0], score, currentQuestionIndex.get())};
        examFrame.add(questionPanel[0]);

        // إضافة Timer
        Timer timer = new Timer(1000, e -> {
            // يمكنك إضافة معالج الوقت مثل عرض الوقت المتبقي
        });

        timer.setInitialDelay(0);
        timer.start();

        // زر Next للانتقال للسؤال التالي
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            // التحقق من الإجابة على السؤال الحالي
            currentQuestionIndex.incrementAndGet(); // زيادة الفهرس
            if (currentQuestionIndex.get() < teacherQuestions.size()) {
                currentQuestion[0] = teacherQuestions.get(currentQuestionIndex.get());
                examFrame.remove(questionPanel[0]); // إزالة السؤال القديم
                questionPanel[0] = createQuestionPanel(currentQuestion[0], score, currentQuestionIndex.get()); // إضافة السؤال الجديد
                examFrame.add(questionPanel[0]);
                examFrame.revalidate();
                examFrame.repaint();
            } else {
                // إذا انتهت الأسئلة، إظهار النتيجة
                timer.stop();
                JOptionPane.showMessageDialog(examFrame, "Your Score: " + score.get());
                examFrame.dispose();
            }
        });

        examFrame.add(nextButton);
        examFrame.setVisible(true);
    }

    private static JPanel createQuestionPanel(TeacherQuestion question, AtomicInteger score, int currentIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(question.getQuestionText()));

        // إنشاء أزرار الخيارات
        JRadioButton option1 = new JRadioButton("1. " + question.getOption1());
        JRadioButton option2 = new JRadioButton("2. " + question.getOption2());
        JRadioButton option3 = new JRadioButton("3. " + question.getOption3());
        JRadioButton option4 = new JRadioButton("4. " + question.getOption4());

        // إضافة الأزرار إلى مجموعة لضمان اختيار واحد فقط
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        // عند اختيار الإجابة
        option1.addActionListener(e -> {
            if (question.getCorrectOption() == 1) {
                score.incrementAndGet();
            }
        });
        option2.addActionListener(e -> {
            if (question.getCorrectOption() == 2) {
                score.incrementAndGet();
            }
        });
        option3.addActionListener(e -> {
            if (question.getCorrectOption() == 3) {
                score.incrementAndGet();
            }
        });
        option4.addActionListener(e -> {
            if (question.getCorrectOption() == 4) {
                score.incrementAndGet();
            }
        });

        // إضافة الأزرار إلى الواجهة
        panel.add(option1);
        panel.add(option2);
        panel.add(option3);
        panel.add(option4);

        return panel;
    }
}