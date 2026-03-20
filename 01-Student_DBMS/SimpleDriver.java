public class SimpleDriver {
    public static void main(String[] args) {
        System.out.println("===== Simple Student Addition =====\n");

        // Add one student
        String name = "Alice Johnson";
        String email = "alice@example.com";
        String phone = "5551234567";

        System.out.println("Adding student: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println();

        // Call the DAO method
        boolean success = StudentDAO.addStudent(name, email, phone);

        // Check if it was successful
        if (success) {
            System.out.println("✓ Student added successfully!");
        } else {
            System.out.println("✗ Failed to add student");
        }

        System.out.println();
        System.out.println("===== Done =====");
    }
}
