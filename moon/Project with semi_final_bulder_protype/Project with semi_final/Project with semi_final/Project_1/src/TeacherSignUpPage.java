//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class TeacherSignUpPage {
//    public static void display() {
//        JFrame frame = new JFrame("Teacher Sign Up");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setSize(300, 250);
//        frame.setLayout(new GridLayout(4, 2));
//
//        JLabel idLabel = new JLabel("ID:");
//        JTextField idField = new JTextField();
//
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField();
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton submitButton = new JButton("Submit");
//        submitButton.addActionListener(e -> {
//            String id = idField.getText();
//            String username = usernameField.getText();
//            String password = new String(passwordField.getPassword());
//
//            if (id.isEmpty() || username.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                try (Connection conn = DatabaseConnectionnnn.getConnection()) {
//                    String query = "INSERT INTO teachers (id, username, password) VALUES (?, ?, ?)";
//                    PreparedStatement stmt = conn.prepareStatement(query);
//                    stmt.setString(1, id);
//                    stmt.setString(2, username);
//                    stmt.setString(3, password);
//
//                    int rowsInserted = stmt.executeUpdate();
//                    if (rowsInserted > 0) {
//                        JOptionPane.showMessageDialog(frame, "Teacher registered successfully!");
//                        frame.dispose();
//                    }
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(frame, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
//        frame.add(idLabel);
//        frame.add(idField);
//        frame.add(usernameLabel);
//        frame.add(usernameField);
//        frame.add(passwordLabel);
//        frame.add(passwordField);
//        frame.add(submitButton);
//
//        frame.setVisible(true);
//    }
//}
