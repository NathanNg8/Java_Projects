import java.util.List;
import java.util.Scanner;

public class MainDriver {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM");
        System.out.println("========================================\n");

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    enrollmentMenu();
                    break;
                case 4:
                    System.out.println("\nThank you for using Student Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }

        scanner.close();
    }

    // ===== MAIN MENU =====
    public static void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Exit");
    }

    // ===== STUDENT MENU =====
    public static void studentMenu() {
        boolean inStudentMenu = true;

        while (inStudentMenu) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Total Students Count");
            System.out.println("7. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudentMenu();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudentMenu();
                    break;
                case 5:
                    deleteStudentMenu();
                    break;
                case 6:
                    totalStudentsCount();
                    break;
                case 7:
                    inStudentMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Student
    public static void addStudentMenu() {
        System.out.println("\n===== ADD STUDENT =====");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        boolean success = StudentDAO.addStudent(name, email, phone);

        if (success) {
            System.out.println("✓ Student added successfully!");
        } else {
            System.out.println("✗ Failed to add student");
        }
    }

    // View All Students
    public static void viewAllStudents() {
        System.out.println("\n===== ALL STUDENTS =====");
        List<Student> students = StudentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // View Student by ID
    public static void viewStudentById() {
        System.out.println("\n===== VIEW STUDENT BY ID =====");
        int id = getIntInput("Enter student ID: ");

        Student student = StudentDAO.getStudentById(id);

        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Update Student
    public static void updateStudentMenu() {
        System.out.println("\n===== UPDATE STUDENT =====");
        int id = getIntInput("Enter student ID to update: ");

        Student student = StudentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current student: " + student);
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new phone: ");
        String phone = scanner.nextLine();

        boolean success = StudentDAO.updateStudent(id, name, email, phone);

        if (success) {
            System.out.println("✓ Student updated successfully!");
        } else {
            System.out.println("✗ Failed to update student");
        }
    }

    // Delete Student
    public static void deleteStudentMenu() {
        System.out.println("\n===== DELETE STUDENT =====");
        int id = getIntInput("Enter student ID to delete: ");

        Student student = StudentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student to delete: " + student);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = StudentDAO.deleteStudent(id);
            if (success) {
                System.out.println("✓ Student deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete student");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Total Students Count
    public static void totalStudentsCount() {
        int total = StudentDAO.getTotalStudents();
        System.out.println("\n===== TOTAL STUDENTS =====");
        System.out.println("Total number of students: " + total);
    }

    // ===== COURSE MENU =====
    public static void courseMenu() {
        boolean inCourseMenu = true;

        while (inCourseMenu) {
            System.out.println("\n===== COURSE MENU =====");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. View Course by ID");
            System.out.println("4. Update Course");
            System.out.println("5. Delete Course");
            System.out.println("6. Total Courses Count");
            System.out.println("7. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourseMenu();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    viewCourseById();
                    break;
                case 4:
                    updateCourseMenu();
                    break;
                case 5:
                    deleteCourseMenu();
                    break;
                case 6:
                    totalCoursesCount();
                    break;
                case 7:
                    inCourseMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Course
    public static void addCourseMenu() {
        System.out.println("\n===== ADD COURSE =====");
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();

        int credits = getIntInput("Enter credits: ");

        boolean success = CourseDAO.addCourse(name, credits);

        if (success) {
            System.out.println("✓ Course added successfully!");
        } else {
            System.out.println("✗ Failed to add course");
        }
    }

    // View All Courses
    public static void viewAllCourses() {
        System.out.println("\n===== ALL COURSES =====");
        List<Course> courses = CourseDAO.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    // View Course by ID
    public static void viewCourseById() {
        System.out.println("\n===== VIEW COURSE BY ID =====");
        int id = getIntInput("Enter course ID: ");

        Course course = CourseDAO.getCourseById(id);

        if (course != null) {
            System.out.println(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    // Update Course
    public static void updateCourseMenu() {
        System.out.println("\n===== UPDATE COURSE =====");
        int id = getIntInput("Enter course ID to update: ");

        Course course = CourseDAO.getCourseById(id);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("Current course: " + course);
        System.out.print("Enter new course name: ");
        String name = scanner.nextLine();

        int credits = getIntInput("Enter new credits: ");

        boolean success = CourseDAO.updateCourse(id, name, credits);

        if (success) {
            System.out.println("✓ Course updated successfully!");
        } else {
            System.out.println("✗ Failed to update course");
        }
    }

    // Delete Course
    public static void deleteCourseMenu() {
        System.out.println("\n===== DELETE COURSE =====");
        int id = getIntInput("Enter course ID to delete: ");

        Course course = CourseDAO.getCourseById(id);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("Course to delete: " + course);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = CourseDAO.deleteCourse(id);
            if (success) {
                System.out.println("✓ Course deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete course");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Total Courses Count
    public static void totalCoursesCount() {
        int total = CourseDAO.getTotalCourses();
        System.out.println("\n===== TOTAL COURSES =====");
        System.out.println("Total number of courses: " + total);
    }

    // ===== ENROLLMENT MENU =====
    public static void enrollmentMenu() {
        boolean inEnrollmentMenu = true;

        while (inEnrollmentMenu) {
            System.out.println("\n===== ENROLLMENT MENU =====");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. View Enrollment by ID");
            System.out.println("4. View Student's Enrollments");
            System.out.println("5. View Course Enrollments");
            System.out.println("6. Update Grade");
            System.out.println("7. Delete Enrollment");
            System.out.println("8. Total Enrollments Count");
            System.out.println("9. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    enrollStudentMenu();
                    break;
                case 2:
                    viewAllEnrollments();
                    break;
                case 3:
                    viewEnrollmentById();
                    break;
                case 4:
                    viewStudentEnrollments();
                    break;
                case 5:
                    viewCourseEnrollments();
                    break;
                case 6:
                    updateGradeMenu();
                    break;
                case 7:
                    deleteEnrollmentMenu();
                    break;
                case 8:
                    totalEnrollmentsCount();
                    break;
                case 9:
                    inEnrollmentMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Enroll Student
    public static void enrollStudentMenu() {
        System.out.println("\n===== ENROLL STUDENT IN COURSE =====");
        int studentId = getIntInput("Enter student ID: ");

        Student student = StudentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int courseId = getIntInput("Enter course ID: ");

        Course course = CourseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter grade (A, B, C, etc.): ");
        String grade = scanner.nextLine();

        boolean success = EnrollmentDAO.enrollStudent(studentId, courseId, grade);

        if (success) {
            System.out.println("✓ Student enrolled successfully!");
            System.out.println("  Student: " + student.getName());
            System.out.println("  Course: " + course.getCourseName());
            System.out.println("  Grade: " + grade);
        } else {
            System.out.println("✗ Failed to enroll student");
        }
    }

    // View All Enrollments
    public static void viewAllEnrollments() {
        System.out.println("\n===== ALL ENROLLMENTS =====");
        List<Enrollment> enrollments = EnrollmentDAO.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }
        }
    }

    // View Enrollment by ID
    public static void viewEnrollmentById() {
        System.out.println("\n===== VIEW ENROLLMENT BY ID =====");
        int id = getIntInput("Enter enrollment ID: ");

        Enrollment enrollment = EnrollmentDAO.getEnrollmentById(id);

        if (enrollment != null) {
            System.out.println(enrollment);
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    // View Student's Enrollments
    public static void viewStudentEnrollments() {
        System.out.println("\n===== STUDENT'S ENROLLMENTS =====");
        int studentId = getIntInput("Enter student ID: ");

        Student student = StudentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<Enrollment> enrollments = EnrollmentDAO.getEnrollmentsByStudent(studentId);

        if (enrollments.isEmpty()) {
            System.out.println("Student " + student.getName() + " has no enrollments.");
        } else {
            System.out.println("Enrollments for " + student.getName() + ":");
            for (Enrollment enrollment : enrollments) {
                Course course = CourseDAO.getCourseById(enrollment.getCourseId());
                System.out.println("  " + enrollment);
                if (course != null) {
                    System.out.println("    Course: " + course.getCourseName());
                }
            }
        }
    }

    // View Course Enrollments
    public static void viewCourseEnrollments() {
        System.out.println("\n===== COURSE ENROLLMENTS =====");
        int courseId = getIntInput("Enter course ID: ");

        Course course = CourseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        List<Enrollment> enrollments = EnrollmentDAO.getEnrollmentsByCourse(courseId);

        if (enrollments.isEmpty()) {
            System.out.println("Course " + course.getCourseName() + " has no enrollments.");
        } else {
            System.out.println("Enrollments for " + course.getCourseName() + ":");
            for (Enrollment enrollment : enrollments) {
                Student student = StudentDAO.getStudentById(enrollment.getStudentId());
                System.out.println("  " + enrollment);
                if (student != null) {
                    System.out.println("    Student: " + student.getName());
                }
            }
        }
    }

    // Update Grade
    public static void updateGradeMenu() {
        System.out.println("\n===== UPDATE GRADE =====");
        int enrollmentId = getIntInput("Enter enrollment ID: ");

        Enrollment enrollment = EnrollmentDAO.getEnrollmentById(enrollmentId);
        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        System.out.println("Current enrollment: " + enrollment);
        System.out.print("Enter new grade: ");
        String grade = scanner.nextLine();

        boolean success = EnrollmentDAO.updateGrade(enrollmentId, grade);

        if (success) {
            System.out.println("✓ Grade updated successfully!");
        } else {
            System.out.println("✗ Failed to update grade");
        }
    }

    // Delete Enrollment
    public static void deleteEnrollmentMenu() {
        System.out.println("\n===== DELETE ENROLLMENT =====");
        int enrollmentId = getIntInput("Enter enrollment ID to delete: ");

        Enrollment enrollment = EnrollmentDAO.getEnrollmentById(enrollmentId);
        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        System.out.println("Enrollment to delete: " + enrollment);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = EnrollmentDAO.deleteEnrollment(enrollmentId);
            if (success) {
                System.out.println("✓ Enrollment deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete enrollment");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Total Enrollments Count
    public static void totalEnrollmentsCount() {
        int total = EnrollmentDAO.getTotalEnrollments();
        System.out.println("\n===== TOTAL ENROLLMENTS =====");
        System.out.println("Total number of enrollments: " + total);
    }

    // ===== HELPER METHOD =====
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
