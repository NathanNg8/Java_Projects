import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // Create - Enroll a student in a course
    public static boolean enrollStudent(int studentId, int courseId, String grade) {
        String sql = "INSERT INTO enrollment (student_id, course_id, grade) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setString(3, grade);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error enrolling student: " + e.getMessage());
            return false;
        }
    }

    // Read - Get all enrollments
    public static List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getString("grade")
                );
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving enrollments: " + e.getMessage());
        }
        
        return enrollments;
    }

    // Read - Get enrollment by ID
    public static Enrollment getEnrollmentById(int enrollmentId) {
        String sql = "SELECT * FROM enrollment WHERE enrollment_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollmentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getString("grade")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving enrollment: " + e.getMessage());
        }
        
        return null;
    }

    // Read - Get enrollments for a specific student
    public static List<Enrollment> getEnrollmentsByStudent(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getString("grade")
                );
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving enrollments: " + e.getMessage());
        }
        
        return enrollments;
    }

    // Read - Get enrollments for a specific course
    public static List<Enrollment> getEnrollmentsByCourse(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE course_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getString("grade")
                );
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving enrollments: " + e.getMessage());
        }
        
        return enrollments;
    }

    // Update - Update enrollment grade
    public static boolean updateGrade(int enrollmentId, String grade) {
        String sql = "UPDATE enrollment SET grade = ? WHERE enrollment_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, grade);
            stmt.setInt(2, enrollmentId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
            return false;
        }
    }

    // Delete - Remove an enrollment
    public static boolean deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollmentId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting enrollment: " + e.getMessage());
            return false;
        }
    }

    // Get total number of enrollments
    public static int getTotalEnrollments() {
        String sql = "SELECT COUNT(*) as count FROM enrollment";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("Error counting enrollments: " + e.getMessage());
        }
        
        return 0;
    }
}
