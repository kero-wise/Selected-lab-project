import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // إنشاء الإطار الرئيسي
        JFrame frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // زر Login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> LoginPage.display());

        // زر Sign Up
        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> SignUpPage.display());

        // إضافة الأزرار للإطار
        frame.add(loginButton);
        frame.add(signupButton);

        frame.setVisible(true);
    }
}
