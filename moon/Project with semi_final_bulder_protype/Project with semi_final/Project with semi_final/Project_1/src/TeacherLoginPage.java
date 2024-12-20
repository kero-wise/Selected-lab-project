//last update
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherLoginPage {

    public static void display() {
        JFrame frame = new JFrame("Teacher Sign In");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton submitButton = new JButton("Login");
        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try (Connection conn = DatabaseConnectionnnn.getConnection()) {
                    String query = "SELECT * FROM teachers WHERE username = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Logged in successfully.");
                        frame.dispose();  // Close the login window
                        openExamInfoPage();  // Open the exam info page
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    // Open the exam info page
    private static void openExamInfoPage() {
        JFrame examFrame = new JFrame("Enter Exam Information");
        examFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        examFrame.setSize(400, 200);
        examFrame.setLayout(new GridLayout(3, 2));

        JLabel examNameLabel = new JLabel("Enter Exam Name:");
        JTextField examNameField = new JTextField();

        JLabel teacherIdLabel = new JLabel("Enter Teacher ID:");
        JTextField teacherIdField = new JTextField();

        JButton submitButton = new JButton("Proceed");

        submitButton.addActionListener(e -> {
            String examName = examNameField.getText();
            String teacherIdText = teacherIdField.getText();

            if (examName.isEmpty() || teacherIdText.isEmpty()) {
                JOptionPane.showMessageDialog(examFrame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int teacherId = Integer.parseInt(teacherIdText);
                    // Insert exam data into the database
                    try (Connection conn = DatabaseConnectionnnn.getConnection()) {
                        String query = "INSERT INTO exams (exam_name, teacher_id) VALUES (?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, examName);
                        stmt.setInt(2, teacherId);

                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(examFrame, "Exam information added successfully.");

                        // Proceed to the quiz count page after entering exam data
                        examFrame.dispose();
                        openQuizCountPage(); // Open quiz count page
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(examFrame, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(examFrame, "Invalid Teacher ID format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        examFrame.add(examNameLabel);
        examFrame.add(examNameField);
        examFrame.add(teacherIdLabel);
        examFrame.add(teacherIdField);
        examFrame.add(submitButton);

        examFrame.setVisible(true);
    }

    // Open the page to set the number of questions
    private static void openQuizCountPage() {
        JFrame countFrame = new JFrame("Set Number of Questions");
        countFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        countFrame.setSize(300, 150);
        countFrame.setLayout(new GridLayout(2, 2));

        JLabel countLabel = new JLabel("Enter number of questions:");
        JTextField countField = new JTextField();
        JButton proceedButton = new JButton("Proceed");

        proceedButton.addActionListener(e -> {
            String countText = countField.getText();
            if (countText.isEmpty()) {
                JOptionPane.showMessageDialog(countFrame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int questionCount = Integer.parseInt(countText);
                    if (questionCount <= 0) {
                        JOptionPane.showMessageDialog(countFrame, "Number of questions must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        countFrame.dispose();
                        openQuizCreationPage(questionCount);  // Open quiz creation page with the specified number of questions
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(countFrame, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        countFrame.add(countLabel);
        countFrame.add(countField);
        countFrame.add(proceedButton);

        countFrame.setVisible(true);
    }

    // Open the quiz creation page
    private static void openQuizCreationPage(int questionCount) {
        JFrame quizFrame = new JFrame("Quiz Creation");
        quizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quizFrame.setSize(400, 300);

        quizFrame.setLayout(new BoxLayout(quizFrame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Create a new quiz with " + questionCount + " questions:");
        JButton createQuizButton = new JButton("Add New Question");

        quizFrame.add(label);
        quizFrame.add(createQuizButton);

        createQuizButton.addActionListener(e -> openAddQuestionPage(questionCount, 1));  // Open the page to add the first question

        quizFrame.setVisible(true);
    }

    // Open the page to add a question
    private static void openAddQuestionPage(int totalQuestions, int currentQuestion) {
    JFrame questionFrame = new JFrame("Add Question " + currentQuestion);
    questionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    questionFrame.setSize(400, 400);

    questionFrame.setLayout(new BoxLayout(questionFrame.getContentPane(), BoxLayout.Y_AXIS));

    JLabel questionTypeLabel = new JLabel("Select Question Type:");
    String[] questionTypes = {"MCQ", "True/False"};
    JComboBox<String> questionTypeComboBox = new JComboBox<>(questionTypes);

    JLabel questionLabel = new JLabel("Question Text:");
    JTextField questionField = new JTextField();
    questionField.setMaximumSize(new Dimension(300, 30));

    JLabel option1Label = new JLabel("Option 1:");
    JTextField option1Field = new JTextField();
    option1Field.setMaximumSize(new Dimension(300, 30));

    JLabel option2Label = new JLabel("Option 2:");
    JTextField option2Field = new JTextField();
    option2Field.setMaximumSize(new Dimension(300, 30));

    JLabel option3Label = new JLabel("Option 3:");
    JTextField option3Field = new JTextField();
    option3Field.setMaximumSize(new Dimension(300, 30));

    JLabel option4Label = new JLabel("Option 4:");
    JTextField option4Field = new JTextField();
    option4Field.setMaximumSize(new Dimension(300, 30));

    JLabel correctOptionLabel = new JLabel("Correct Option (1-4):");
    JTextField correctOptionField = new JTextField();
    correctOptionField.setMaximumSize(new Dimension(300, 30));

    JButton saveButton = new JButton("Save Question");
    JButton nextButton = new JButton("Next");
    nextButton.setEnabled(false);  // Disable the Next button initially

    // Action Listener to toggle fields based on question type
    questionTypeComboBox.addActionListener(e -> {
        String selectedType = (String) questionTypeComboBox.getSelectedItem();
        if ("True/False".equals(selectedType)) {
            option1Field.setText("True");
            option2Field.setText("False");
            option1Field.setEditable(false);
            option2Field.setEditable(false);
            option3Field.setVisible(false);
            option4Field.setVisible(false);
            correctOptionLabel.setText("Correct Option (1-2):");
        } else {
            option1Field.setEditable(true);
            option2Field.setEditable(true);
            option3Field.setVisible(true);
            option4Field.setVisible(true);
            correctOptionLabel.setText("Correct Option (1-4):");
        }
    });

    saveButton.addActionListener(e -> {
        String questionType = (String) questionTypeComboBox.getSelectedItem();
        String questionText = questionField.getText();
        String option1 = option1Field.getText();
        String option2 = option2Field.getText();
        String option3 = questionType.equals("MCQ") ? option3Field.getText() : "";
        String option4 = questionType.equals("MCQ") ? option4Field.getText() : "";
        String correctOptionText = correctOptionField.getText();

        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || correctOptionText.isEmpty() ||
                (questionType.equals("MCQ") && (option3.isEmpty() || option4.isEmpty()))) {
            JOptionPane.showMessageDialog(questionFrame, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int correctOption = Integer.parseInt(correctOptionText);

                if ((questionType.equals("True/False") && (correctOption < 1 || correctOption > 2)) ||
                        (questionType.equals("MCQ") && (correctOption < 1 || correctOption > 4))) {
                    JOptionPane.showMessageDialog(questionFrame, "Correct option out of range.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection conn = DatabaseConnectionnnn.getConnection()) {
                        String query = "INSERT INTO questions (question_type, question_text, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, questionType);
                        stmt.setString(2, questionText);
                        stmt.setString(3, option1);
                        stmt.setString(4, option2);
                        stmt.setString(5, option3);
                        stmt.setString(6, option4);
                        stmt.setInt(7, correctOption);

                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(questionFrame, "Question added successfully.");

                        if (currentQuestion < totalQuestions) {
                            nextButton.setEnabled(true);
                        } else {
                            nextButton.setText("Finish");
                            nextButton.setEnabled(true);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(questionFrame, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(questionFrame, "Correct Option must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    nextButton.addActionListener(e -> {
        if (currentQuestion < totalQuestions) {
            questionFrame.dispose();
            openAddQuestionPage(totalQuestions, currentQuestion + 1);
        } else {
            questionFrame.dispose();
            JOptionPane.showMessageDialog(null, "Quiz creation completed.");
        }
    });

    questionFrame.add(questionTypeLabel);
    questionFrame.add(questionTypeComboBox);
    questionFrame.add(questionLabel);
    questionFrame.add(questionField);
    questionFrame.add(option1Label);
    questionFrame.add(option1Field);
    questionFrame.add(option2Label);
    questionFrame.add(option2Field);
    questionFrame.add(option3Label);
    questionFrame.add(option3Field);
    questionFrame.add(option4Label);
    questionFrame.add(option4Field);
    questionFrame.add(correctOptionLabel);
    questionFrame.add(correctOptionField);
    questionFrame.add(saveButton);
    questionFrame.add(nextButton);

    questionFrame.setVisible(true);
}

    public static void main(String[] args) {
        display();  // Start the application
    }
}
