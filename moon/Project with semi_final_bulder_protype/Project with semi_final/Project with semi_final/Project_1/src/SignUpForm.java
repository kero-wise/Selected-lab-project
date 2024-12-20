//DB UPDATE
import javax.swing.*;
import java.awt.*;

public class SignUpForm {
    public static void display(String role) {
        JFrame frame = new JFrame(role + " Sign Up");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLayout(new GridLayout(4, 2));

        JLabel idLabel;

        // التحقق من قيمة الدور وتعيين النص المناسب
        if (role.equalsIgnoreCase("student")) { // تجاهل حالة الأحرف
            idLabel = new JLabel("Student ID:");
        } else if (role.equalsIgnoreCase("teacher")) {
            idLabel = new JLabel("Teacher ID:");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid role specified.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // الخروج إذا كان الدور غير صحيح
        }

        JTextField idField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String id = idField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (id.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // إنشاء كائن المستخدم بناءً على الدور
                User user = UserFactory.createUser(role.toLowerCase()); // تحويل الدور إلى أحرف صغيرة
                user.register(id, username, password); // تسجيل المستخدم
                JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        frame.add(idLabel);
        frame.add(idField);
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); // ترك خانة فارغة
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
