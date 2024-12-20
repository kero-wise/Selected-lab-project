import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    public static void display() {
        // إنشاء الإطار الخاص بتسجيل الدخول
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // زر Student
        JButton studentButton = new JButton("Login as Student");
        studentButton.addActionListener(e -> StudentLoginPage.display());

        // زر Teacher
        JButton teacherButton = new JButton("Login as Teacher");
        teacherButton.addActionListener(e -> TeacherLoginPage.display());

        // إضافة الأزرار للإطار
        frame.add(studentButton);
        frame.add(teacherButton);

        frame.setVisible(true);
    }
}
