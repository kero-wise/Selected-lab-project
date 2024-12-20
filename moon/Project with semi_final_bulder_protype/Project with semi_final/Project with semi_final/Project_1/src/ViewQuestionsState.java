import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
class ViewQuestionsState implements ExamPageState {
    @Override
    public void display(TeacherExamPage context) {
        JFrame frame = new JFrame("View Questions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Questions:");
        frame.add(label);

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));

        for (TeacherQuestion question : context.getTeacherQuestions()) {
            JTextArea questionArea = new JTextArea(3, 30);
            questionArea.setText(context.formatQuestion(question));
            questionArea.setWrapStyleWord(true);
            questionArea.setLineWrap(true);
            questionArea.setCaretPosition(0);
            questionArea.setEditable(false);
            questionsPanel.add(questionArea);
        }

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}
