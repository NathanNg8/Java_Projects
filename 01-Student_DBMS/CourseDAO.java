import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Create - Add a new course
    public static boolean addCourse(String courseName, int credits) {
        String sql = "INSERT INTO courses (course_name, credits) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, courseName);
            stmt.setInt(2, credits);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
            return false;
        }
    }

    // Read - Get all courses
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits")
                );
                courses.add(course);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
        }
        
        return courses;
    }

    // Read - Get course by ID
    public static Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving course: " + e.getMessage());
        }
        
        return null;
    }

    // Update - Update course information
    public static boolean updateCourse(int courseId, String courseName, int credits) {
        String sql = "UPDATE courses SET course_name = ?, credits = ? WHERE course_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, courseName);
            stmt.setInt(2, credits);
            stmt.setInt(3, courseId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
            return false;
        }
    }

    // Delete - Remove a course
    public static boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
            return false;
        }
    }

    // Get total number of courses
    public static int getTotalCourses() {
        String sql = "SELECT COUNT(*) as count FROM courses";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("Error counting courses: " + e.getMessage());
        }
        
        return 0;
    }
}
