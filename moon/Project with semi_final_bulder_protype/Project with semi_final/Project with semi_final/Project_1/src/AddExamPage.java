
//builder here 
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddExamPage {
    private ArrayList<TeacherQuestion> teacherQuestions = new ArrayList<>();
    private int currentQuestion = 1;

    public static class AddExamPageBuilder {
        private int numberOfQuestions;
        private ArrayList<TeacherQuestion> teacherQuestions = new ArrayList<>();
        private int currentQuestion = 1;

        // Set the number of questions
        public AddExamPageBuilder setNumberOfQuestions(int numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }

        // Add question to the list
        public AddExamPageBuilder addQuestion(String questionText, String option1, String option2, String option3, String option4, int correctOption) {
            teacherQuestions.add(new TeacherQuestion(questionText, option1, option2, option3, option4, correctOption));
            this.currentQuestion++; // Increment the current question after adding
            return this;
        }

        public ArrayList<TeacherQuestion> getTeacherQuestions() {
            return teacherQuestions;
        }

        public int getNumberOfQuestions() {
            return numberOfQuestions;
        }

        public int getCurrentQuestion() {
            return currentQuestion;
        }
    }

    private AddExamPage(AddExamPageBuilder builder) {
        this.teacherQuestions = builder.getTeacherQuestions();
        this.currentQuestion = builder.getCurrentQuestion();
    }

    // Display method for the page
    public static void display(ArrayList<TeacherQuestion> teacherQuestions) {
        JFrame frame = new JFrame("Add New Exam");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(10, 2));

        JLabel numberOfQuestionsLabel = new JLabel("Enter number of questions:");
        JTextField numberOfQuestionsField = new JTextField();

        JLabel questionLabel = new JLabel("Question:");
        JTextField questionField = new JTextField();

        JLabel option1Label = new JLabel("Option 1:");
        JTextField option1Field = new JTextField();

        JLabel option2Label = new JLabel("Option 2:");
        JTextField option2Field = new JTextField();

        JLabel option3Label = new JLabel("Option 3:");
        JTextField option3Field = new JTextField();

        JLabel option4Label = new JLabel("Option 4:");
        JTextField option4Field = new JTextField();

        JLabel correctOptionLabel = new JLabel("Correct Option (1-4):");
        JTextField correctOptionField = new JTextField();
        JButton addButton = new JButton("Add Question");

        addButton.addActionListener(e -> {
            try {
                if (numberOfQuestionsField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the number of questions.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int numberOfQuestions = Integer.parseInt(numberOfQuestionsField.getText());
                if (numberOfQuestions <= 0) {
                    throw new NumberFormatException();
                }

                AddExamPageBuilder builder = new AddExamPageBuilder().setNumberOfQuestions(numberOfQuestions);

                if (builder.getCurrentQuestion() <= builder.getNumberOfQuestions()) {
                    String questionText = questionField.getText();
                    String option1 = option1Field.getText();
                    String option2 = option2Field.getText();
                    String option3 = option3Field.getText();
                    String option4 = option4Field.getText();
                    int correctOption;

                    try {
                        correctOption = Integer.parseInt(correctOptionField.getText());
                        if (correctOption < 1 || correctOption > 4) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number for the correct option (1-4).", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    builder.addQuestion(questionText, option1, option2, option3, option4, correctOption);

                    teacherQuestions.addAll(builder.getTeacherQuestions());

                    if (builder.getCurrentQuestion() > builder.getNumberOfQuestions()) {
                        int choice = JOptionPane.showConfirmDialog(frame, "All questions have been added. Do you want to close the window?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            frame.dispose();
                        }
                    } else {
                        questionField.setText("");
                        option1Field.setText("");
                        option2Field.setText("");
                        option3Field.setText("");
                        option4Field.setText("");
                        correctOptionField.setText("");
                        questionLabel.setText("Question " + builder.getCurrentQuestion() + ":");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for the number of questions.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(numberOfQuestionsLabel);
        frame.add(numberOfQuestionsField);
        frame.add(questionLabel);
        frame.add(questionField);
        frame.add(option1Label);
        frame.add(option1Field);
        frame.add(option2Label);
        frame.add(option2Field);
        frame.add(option3Label);
        frame.add(option3Field);
        frame.add(option4Label);
        frame.add(option4Field);
        frame.add(correctOptionLabel);
        frame.add(correctOptionField);
        frame.add(addButton);

        frame.setVisible(true);
    }
}
