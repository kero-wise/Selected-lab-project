//last update
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentLoginPage {

    public static void display() {
        JFrame frame = new JFrame("Student Sign In");
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
                loginUser(username, password, frame);
            }
        });

        // Add components to the frame
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(submitButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static int studentId;

    private static void loginUser(String username, String password, JFrame frame) {
        try (Connection conn = DatabaseConnectionnnn.getConnection()) {
            String query = "SELECT * FROM students WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                studentId = rs.getInt("student_id");
                JOptionPane.showMessageDialog(frame, "Logged in successfully.");
                frame.dispose();
                showExamChoicePage();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showExamChoicePage() {
        JFrame examChoiceFrame = new JFrame("Choose Exam");
        examChoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        examChoiceFrame.setSize(300, 150);
        examChoiceFrame.setLayout(new GridLayout(2, 1));

        JButton teacherExamButton = new JButton("Teacher's Exam");
        teacherExamButton.addActionListener(e -> {
            showTeacherExamPage();
            examChoiceFrame.dispose();
        });

        examChoiceFrame.add(teacherExamButton);

        examChoiceFrame.setVisible(true);
    }

   private static void showTeacherExamPage() {
    JFrame teacherExamFrame = new JFrame("Teacher's Exam");
    teacherExamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    teacherExamFrame.setSize(500, 400);
    teacherExamFrame.setLayout(new BorderLayout());

    // Fetch the questions from the database
    ArrayList<Question> questions = fetchQuestions();

    if (questions.isEmpty()) {
        JOptionPane.showMessageDialog(teacherExamFrame, "No questions available.", "Error", JOptionPane.ERROR_MESSAGE);
        teacherExamFrame.dispose();
        return;
    }

    // Display questions in a panel
    JPanel quizPanel = new JPanel();
    quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

    ButtonGroup[] optionGroups = new ButtonGroup[questions.size()];
    ArrayList<JRadioButton[]> optionButtons = new ArrayList<>();

    for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));  // Use BoxLayout for vertical layout

        JLabel questionLabel = new JLabel(question.getQuestionText());
        questionPanel.add(questionLabel);

        ButtonGroup optionGroup = new ButtonGroup();
        JRadioButton[] options = new JRadioButton[4];  // We still create 4 options, but use them based on question_type

                if (question.getQuestionType().equals("True/False")) {  // If it's a True/False question
              options[0] = new JRadioButton("True");
              options[1] = new JRadioButton("False");
              optionGroup.add(options[0]);
              optionGroup.add(options[1]);

              questionPanel.add(options[0]);
              questionPanel.add(options[1]);


        } else if (question.getQuestionType().equals("MCQ")) {  // If it's a Multiple Choice question
            options[0] = new JRadioButton(question.getOption1());
            options[1] = new JRadioButton(question.getOption2());
            options[2] = new JRadioButton(question.getOption3());
            options[3] = new JRadioButton(question.getOption4());
            optionGroup.add(options[0]);
            optionGroup.add(options[1]);
            optionGroup.add(options[2]);
            optionGroup.add(options[3]);
            questionPanel.add(options[0]);
            questionPanel.add(options[1]);
            questionPanel.add(options[2]);
            questionPanel.add(options[3]);
        }

        quizPanel.add(questionPanel);
        optionGroups[i] = optionGroup;
        optionButtons.add(options);
    }

    // Add the quiz panel to a scroll pane
    JScrollPane scrollPane = new JScrollPane(quizPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Submit button
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(e -> submitAnswers(questions, optionGroups, optionButtons, teacherExamFrame));

    // Add components to the frame
    teacherExamFrame.add(scrollPane, BorderLayout.CENTER);
    teacherExamFrame.add(submitButton, BorderLayout.SOUTH);

    teacherExamFrame.setVisible(true);
}

    private static ArrayList<Question> fetchQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try (Connection conn = DatabaseConnectionnnn.getConnection()) {
            String query = "SELECT * FROM questions";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                String option4 = rs.getString("option4");
                int correctOption = rs.getInt("correct_option");
                String questionType = rs.getString("question_type");  // T/F or MCQ
                questions.add(new Question(questionId, questionText, option1, option2, option3, option4, correctOption, questionType));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    private static void submitAnswers(ArrayList<Question> questions, ButtonGroup[] optionGroups, ArrayList<JRadioButton[]> optionButtons, JFrame teacherExamFrame) {
        int examId = 1;
        int score = 0;

        try (Connection conn2 = DatabaseConnectionnnn.getConnection()) {
            String selectQuery = "SELECT * FROM student_answers WHERE student_id = ? AND exam_id = ? AND question_id = ?";
            String insertQuery = "INSERT INTO student_answers (student_id, exam_id, question_id, selected_option, is_correct) VALUES (?, ?, ?, ?, ?)";
            String updateQuery = "UPDATE student_answers SET selected_option = ?, is_correct = ? WHERE student_id = ? AND exam_id = ? AND question_id = ?";

            PreparedStatement selectStmt = conn2.prepareStatement(selectQuery);
            PreparedStatement insertStmt = conn2.prepareStatement(insertQuery);
            PreparedStatement updateStmt = conn2.prepareStatement(updateQuery);

            for (int i = 0; i < questions.size(); i++) {
                ButtonGroup group = optionGroups[i];
                JRadioButton selectedOption = null;

                for (JRadioButton option : optionButtons.get(i)) {
                    if (option.isSelected()) {
                        selectedOption = option;
                        break;
                    }
                }

                if (selectedOption != null) {
                    int selectedOptionIndex = -1;
                    if (selectedOption == optionButtons.get(i)[0]) selectedOptionIndex = 1;
                    else if (selectedOption == optionButtons.get(i)[1]) selectedOptionIndex = 2;
                    else if (selectedOption == optionButtons.get(i)[2]) selectedOptionIndex = 3;
                    else if (selectedOption == optionButtons.get(i)[3]) selectedOptionIndex = 4;

                    int correctOption = questions.get(i).getCorrectOption();
                    boolean isCorrect = selectedOptionIndex == correctOption;

                    if (isCorrect) score++;

                    selectStmt.setInt(1, studentId);
                    selectStmt.setInt(2, examId);
                    selectStmt.setInt(3, questions.get(i).getQuestionId());
                    ResultSet rs = selectStmt.executeQuery();

                    if (rs.next()) {
                        updateStmt.setInt(1, selectedOptionIndex);
                        updateStmt.setBoolean(2, isCorrect);
                        updateStmt.setInt(3, studentId);
                        updateStmt.setInt(4, examId);
                        updateStmt.setInt(5, questions.get(i).getQuestionId());
                        updateStmt.executeUpdate();
                    } else {
                        insertStmt.setInt(1, studentId);
                        insertStmt.setInt(2, examId);
                        insertStmt.setInt(3, questions.get(i).getQuestionId());
                        insertStmt.setInt(4, selectedOptionIndex);
                        insertStmt.setBoolean(5, isCorrect);
                        insertStmt.executeUpdate();
                    }
                }
            }

            ScoreManager scoreManager = ScoreManager.getInstance();
            scoreManager.recordScore(String.valueOf(studentId), score);

            JOptionPane.showMessageDialog(teacherExamFrame, "Your Answer has been submitted. Your Score is: " + score);
            teacherExamFrame.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(teacherExamFrame, "Error saving answers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Question {
        private int questionId;
        private String questionText;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private int correctOption;
        private String questionType;  // "T/F" or "MCQ"

        public Question(int questionId, String questionText, String option1, String option2, String option3, String option4, int correctOption, String questionType) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctOption = correctOption;
            this.questionType = questionType;
        }

        public int getQuestionId() {
            return questionId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public int getCorrectOption() {
            return correctOption;
        }

        public String getQuestionType() {
            return questionType;
        }
    }
}
