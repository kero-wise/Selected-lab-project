//DB UPDATE
import javax.swing.*;
import java.awt.*;

public class SignUpPage {
    public static void display() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel questionLabel = new JLabel("Sign up as:");
        JButton studentButton = new JButton("Student");
        JButton teacherButton = new JButton("Teacher");

        studentButton.addActionListener(e -> {
            frame.dispose();
            SignUpForm.display("Student");
        });

        teacherButton.addActionListener(e -> {
            frame.dispose();
            SignUpForm.display("Teacher");
        });

        frame.add(questionLabel);
        frame.add(studentButton);
        frame.add(teacherButton);

        frame.setVisible(true);
    }
}
