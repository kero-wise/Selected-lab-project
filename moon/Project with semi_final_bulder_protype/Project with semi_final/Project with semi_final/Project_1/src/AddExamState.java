import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
class AddExamState implements ExamPageState {
    @Override
    public void display(TeacherExamPage context) {
        JFrame frame = new JFrame("Add New Exam");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Exam Details:");
        frame.add(label);

        JTextField questionField = new JTextField(30);
        frame.add(new JLabel("Question:"));
        frame.add(questionField);

        JTextField option1Field = new JTextField(20);
        frame.add(new JLabel("Option 1:"));
        frame.add(option1Field);

        JTextField option2Field = new JTextField(20);
        frame.add(new JLabel("Option 2:"));
        frame.add(option2Field);

        JTextField option3Field = new JTextField(20);
        frame.add(new JLabel("Option 3:"));
        frame.add(option3Field);

        JTextField option4Field = new JTextField(20);
        frame.add(new JLabel("Option 4:"));
        frame.add(option4Field);

        JTextField correctOptionField = new JTextField(5);
        frame.add(new JLabel("Correct Option (1-4):"));
        frame.add(correctOptionField);

        JButton saveButton = new JButton("Save Question");
        saveButton.addActionListener(e -> {
            String question = questionField.getText();
            String option1 = option1Field.getText();
            String option2 = option2Field.getText();
            String option3 = option3Field.getText();
            String option4 = option4Field.getText();
            int correctOption = Integer.parseInt(correctOptionField.getText());

            context.addQuestionToDatabase(question, option1, option2, option3, option4, correctOption);
        });

        frame.add(saveButton);
        frame.setVisible(true);
    }
}
