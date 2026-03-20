public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private String grade;

    // Constructor
    public Enrollment(int enrollmentId, int studentId, int courseId, String grade) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    // Getters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getGrade() {
        return grade;
    }

    // Setters
    public void setGrade(String grade) {
        this.grade = grade;
    }

    // toString for easy printing
    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", grade='" + grade + '\'' +
                '}';
    }
}
