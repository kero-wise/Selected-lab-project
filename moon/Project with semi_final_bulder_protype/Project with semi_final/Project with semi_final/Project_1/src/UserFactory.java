//DB UPDATE
public class UserFactory {
    public static User createUser(String role) {
        if (role.equalsIgnoreCase("student")) {
            return new Student();
        } else if (role.equalsIgnoreCase("teacher")) {
            return new Teacher();
        } else {
            throw new IllegalArgumentException("Invalid role specified");
        }
    }
}
