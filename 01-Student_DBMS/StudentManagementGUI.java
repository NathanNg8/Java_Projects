import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class StudentManagementGUI extends Application {
    private TabPane tabPane;
    private Stage primaryStage;

    // Student Tab Components
    private TableView<Student> studentTable;
    private TextField studentNameField;
    private TextField studentEmailField;
    private TextField studentPhoneField;

    // Course Tab Components
    private TableView<Course> courseTable;
    private TextField courseNameField;
    private TextField courseCreditsField;

    // Enrollment Tab Components
    private TableView<Enrollment> enrollmentTable;
    private ComboBox<Student> studentComboBox;
    private ComboBox<Course> courseComboBox;
    private TextField gradeField;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Student Management System");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        // Create main layout
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create tabs
        tabPane.getTabs().addAll(
                createStudentTab(),
                createCourseTab(),
                createEnrollmentTab()
        );

        Scene scene = new Scene(tabPane);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial data
        refreshStudentTable();
        refreshCourseTable();
        refreshEnrollmentTable();
    }

    // ===== STUDENT TAB =====
    private Tab createStudentTab() {
        VBox studentVBox = new VBox(10);
        studentVBox.setPadding(new Insets(15));

        // Title
        Label titleLabel = new Label("Student Management");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #1976D2;");

        // Form Section
        GridPane formGrid = createStudentForm();

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addBtn = new Button("Add Student");
        addBtn.setPrefWidth(120);
        addBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        addBtn.setOnAction(e -> addStudent());

        Button updateBtn = new Button("Update");
        updateBtn.setPrefWidth(120);
        updateBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        updateBtn.setOnAction(e -> updateStudent());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(120);
        deleteBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        deleteBtn.setOnAction(e -> deleteStudent());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setPrefWidth(120);
        refreshBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        refreshBtn.setOnAction(e -> refreshStudentTable());

        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, refreshBtn);

        // Table
        studentTable = createStudentTable();

        studentVBox.getChildren().addAll(titleLabel, formGrid, buttonBox, new Separator(), studentTable);
        VBox.setVgrow(studentTable, Priority.ALWAYS);

        Tab tab = new Tab("Students", studentVBox);
        tab.setStyle("-fx-text-fill: #333;");
        return tab;
    }

    private GridPane createStudentForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-padding: 15;");

        Label nameLabel = new Label("Name:");
        studentNameField = new TextField();
        studentNameField.setPromptText("Enter student name");

        Label emailLabel = new Label("Email:");
        studentEmailField = new TextField();
        studentEmailField.setPromptText("Enter email");

        Label phoneLabel = new Label("Phone:");
        studentPhoneField = new TextField();
        studentPhoneField.setPromptText("Enter phone number");

        grid.add(nameLabel, 0, 0);
        grid.add(studentNameField, 1, 0);
        grid.add(emailLabel, 2, 0);
        grid.add(studentEmailField, 3, 0);
        grid.add(phoneLabel, 4, 0);
        grid.add(studentPhoneField, 5, 0);

        ColumnConstraints col = new ColumnConstraints(150);
        grid.getColumnConstraints().addAll(col, new ColumnConstraints(150), 
                col, new ColumnConstraints(150), col, new ColumnConstraints(150));

        return grid;
    }

    private TableView<Student> createStudentTable() {
        TableView<Student> table = new TableView<>();

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStudentId()));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEmail()));

        TableColumn<Student, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPhone()));

        table.getColumns().addAll(idCol, nameCol, emailCol, phoneCol);
        table.setPrefHeight(300);

        return table;
    }

    private void addStudent() {
        String name = studentNameField.getText();
        String email = studentEmailField.getText();
        String phone = studentPhoneField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        boolean success = StudentDAO.addStudent(name, email, phone);
        if (success) {
            showAlert("Success", "Student added successfully!");
            studentNameField.clear();
            studentEmailField.clear();
            studentPhoneField.clear();
            refreshStudentTable();
        } else {
            showAlert("Error", "Failed to add student");
        }
    }

    private void updateStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to update!");
            return;
        }

        String name = studentNameField.getText();
        String email = studentEmailField.getText();
        String phone = studentPhoneField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        boolean success = StudentDAO.updateStudent(selected.getStudentId(), name, email, phone);
        if (success) {
            showAlert("Success", "Student updated successfully!");
            studentNameField.clear();
            studentEmailField.clear();
            studentPhoneField.clear();
            refreshStudentTable();
        } else {
            showAlert("Error", "Failed to update student");
        }
    }

    private void deleteStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirm Delete", 
                "Are you sure you want to delete " + selected.getName() + "?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = StudentDAO.deleteStudent(selected.getStudentId());
            if (success) {
                showAlert("Success", "Student deleted successfully!");
                refreshStudentTable();
            } else {
                showAlert("Error", "Failed to delete student");
            }
        }
    }

    private void refreshStudentTable() {
        List<Student> students = StudentDAO.getAllStudents();
        ObservableList<Student> observableStudents = FXCollections.observableArrayList(students);
        studentTable.setItems(observableStudents);
    }

    // ===== COURSE TAB =====
    private Tab createCourseTab() {
        VBox courseVBox = new VBox(10);
        courseVBox.setPadding(new Insets(15));

        // Title
        Label titleLabel = new Label("Course Management");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #388E3C;");

        // Form Section
        GridPane formGrid = createCourseForm();

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addBtn = new Button("Add Course");
        addBtn.setPrefWidth(120);
        addBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        addBtn.setOnAction(e -> addCourse());

        Button updateBtn = new Button("Update");
        updateBtn.setPrefWidth(120);
        updateBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        updateBtn.setOnAction(e -> updateCourse());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(120);
        deleteBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        deleteBtn.setOnAction(e -> deleteCourse());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setPrefWidth(120);
        refreshBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        refreshBtn.setOnAction(e -> refreshCourseTable());

        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, refreshBtn);

        // Table
        courseTable = createCourseTable();

        courseVBox.getChildren().addAll(titleLabel, formGrid, buttonBox, new Separator(), courseTable);
        VBox.setVgrow(courseTable, Priority.ALWAYS);

        Tab tab = new Tab("Courses", courseVBox);
        tab.setStyle("-fx-text-fill: #333;");
        return tab;
    }

    private GridPane createCourseForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-padding: 15;");

        Label nameLabel = new Label("Course Name:");
        courseNameField = new TextField();
        courseNameField.setPromptText("Enter course name");

        Label creditsLabel = new Label("Credits:");
        courseCreditsField = new TextField();
        courseCreditsField.setPromptText("Enter credits");

        grid.add(nameLabel, 0, 0);
        grid.add(courseNameField, 1, 0);
        grid.add(creditsLabel, 2, 0);
        grid.add(courseCreditsField, 3, 0);

        return grid;
    }

    private TableView<Course> createCourseTable() {
        TableView<Course> table = new TableView<>();

        TableColumn<Course, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCourseId()));

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCourseName()));

        TableColumn<Course, Integer> creditsCol = new TableColumn<>("Credits");
        creditsCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCredits()));

        table.getColumns().addAll(idCol, nameCol, creditsCol);
        table.setPrefHeight(300);

        return table;
    }

    private void addCourse() {
        String name = courseNameField.getText();
        String creditsStr = courseCreditsField.getText();

        if (name.isEmpty() || creditsStr.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        try {
            int credits = Integer.parseInt(creditsStr);
            boolean success = CourseDAO.addCourse(name, credits);
            if (success) {
                showAlert("Success", "Course added successfully!");
                courseNameField.clear();
                courseCreditsField.clear();
                refreshCourseTable();
            } else {
                showAlert("Error", "Failed to add course");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Credits must be a number!");
        }
    }

    private void updateCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to update!");
            return;
        }

        String name = courseNameField.getText();
        String creditsStr = courseCreditsField.getText();

        if (name.isEmpty() || creditsStr.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        try {
            int credits = Integer.parseInt(creditsStr);
            boolean success = CourseDAO.updateCourse(selected.getCourseId(), name, credits);
            if (success) {
                showAlert("Success", "Course updated successfully!");
                courseNameField.clear();
                courseCreditsField.clear();
                refreshCourseTable();
            } else {
                showAlert("Error", "Failed to update course");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Credits must be a number!");
        }
    }

    private void deleteCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirm Delete", 
                "Are you sure you want to delete " + selected.getCourseName() + "?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = CourseDAO.deleteCourse(selected.getCourseId());
            if (success) {
                showAlert("Success", "Course deleted successfully!");
                refreshCourseTable();
            } else {
                showAlert("Error", "Failed to delete course");
            }
        }
    }

    private void refreshCourseTable() {
        List<Course> courses = CourseDAO.getAllCourses();
        ObservableList<Course> observableCourses = FXCollections.observableArrayList(courses);
        courseTable.setItems(observableCourses);
    }

    // ===== ENROLLMENT TAB =====
    private Tab createEnrollmentTab() {
        VBox enrollmentVBox = new VBox(10);
        enrollmentVBox.setPadding(new Insets(15));

        // Title
        Label titleLabel = new Label("Enrollment Management");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #D32F2F;");

        // Form Section
        GridPane formGrid = createEnrollmentForm();

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button enrollBtn = new Button("Enroll");
        enrollBtn.setPrefWidth(120);
        enrollBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        enrollBtn.setOnAction(e -> enrollStudent());

        Button updateGradeBtn = new Button("Update Grade");
        updateGradeBtn.setPrefWidth(120);
        updateGradeBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        updateGradeBtn.setOnAction(e -> updateGrade());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(120);
        deleteBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        deleteBtn.setOnAction(e -> deleteEnrollment());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setPrefWidth(120);
        refreshBtn.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        refreshBtn.setOnAction(e -> refreshEnrollmentTable());

        buttonBox.getChildren().addAll(enrollBtn, updateGradeBtn, deleteBtn, refreshBtn);

        // Table
        enrollmentTable = createEnrollmentTable();

        enrollmentVBox.getChildren().addAll(titleLabel, formGrid, buttonBox, new Separator(), enrollmentTable);
        VBox.setVgrow(enrollmentTable, Priority.ALWAYS);

        Tab tab = new Tab("Enrollments", enrollmentVBox);
        tab.setStyle("-fx-text-fill: #333;");
        return tab;
    }

    private GridPane createEnrollmentForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-padding: 15;");

        Label studentLabel = new Label("Student:");
        studentComboBox = new ComboBox<>();
        studentComboBox.setPrefWidth(200);
        refreshStudentComboBox();

        Label courseLabel = new Label("Course:");
        courseComboBox = new ComboBox<>();
        courseComboBox.setPrefWidth(200);
        refreshCourseComboBox();

        Label gradeLabel = new Label("Grade:");
        gradeField = new TextField();
        gradeField.setPromptText("Enter grade (A, B, C, etc.)");
        gradeField.setPrefWidth(100);

        grid.add(studentLabel, 0, 0);
        grid.add(studentComboBox, 1, 0);
        grid.add(courseLabel, 2, 0);
        grid.add(courseComboBox, 3, 0);
        grid.add(gradeLabel, 4, 0);
        grid.add(gradeField, 5, 0);

        return grid;
    }

    private TableView<Enrollment> createEnrollmentTable() {
        TableView<Enrollment> table = new TableView<>();

        TableColumn<Enrollment, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEnrollmentId()));

        TableColumn<Enrollment, Integer> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStudentId()));

        TableColumn<Enrollment, Integer> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCourseId()));

        TableColumn<Enrollment, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGrade()));

        table.getColumns().addAll(idCol, studentIdCol, courseIdCol, gradeCol);
        table.setPrefHeight(300);

        return table;
    }

    private void enrollStudent() {
        Student student = studentComboBox.getValue();
        Course course = courseComboBox.getValue();
        String grade = gradeField.getText();

        if (student == null || course == null || grade.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        boolean success = EnrollmentDAO.enrollStudent(student.getStudentId(), course.getCourseId(), grade);
        if (success) {
            showAlert("Success", "Student enrolled successfully!");
            gradeField.clear();
            refreshEnrollmentTable();
        } else {
            showAlert("Error", "Failed to enroll student");
        }
    }

    private void updateGrade() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select an enrollment to update!");
            return;
        }

        String grade = gradeField.getText();
        if (grade.isEmpty()) {
            showAlert("Error", "Please enter a grade!");
            return;
        }

        boolean success = EnrollmentDAO.updateGrade(selected.getEnrollmentId(), grade);
        if (success) {
            showAlert("Success", "Grade updated successfully!");
            gradeField.clear();
            refreshEnrollmentTable();
        } else {
            showAlert("Error", "Failed to update grade");
        }
    }

    private void deleteEnrollment() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select an enrollment to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirm Delete", 
                "Are you sure you want to delete this enrollment?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = EnrollmentDAO.deleteEnrollment(selected.getEnrollmentId());
            if (success) {
                showAlert("Success", "Enrollment deleted successfully!");
                refreshEnrollmentTable();
            } else {
                showAlert("Error", "Failed to delete enrollment");
            }
        }
    }

    private void refreshEnrollmentTable() {
        List<Enrollment> enrollments = EnrollmentDAO.getAllEnrollments();
        ObservableList<Enrollment> observableEnrollments = FXCollections.observableArrayList(enrollments);
        enrollmentTable.setItems(observableEnrollments);
    }

    private void refreshStudentComboBox() {
        List<Student> students = StudentDAO.getAllStudents();
        ObservableList<Student> observableStudents = FXCollections.observableArrayList(students);
        studentComboBox.setItems(observableStudents);
        studentComboBox.setConverter(new javafx.util.StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getName() + " (ID: " + student.getStudentId() + ")";
            }

            @Override
            public Student fromString(String string) {
                return null;
            }
        });
    }

    private void refreshCourseComboBox() {
        List<Course> courses = CourseDAO.getAllCourses();
        ObservableList<Course> observableCourses = FXCollections.observableArrayList(courses);
        courseComboBox.setItems(observableCourses);
        courseComboBox.setConverter(new javafx.util.StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course == null ? "" : course.getCourseName() + " (ID: " + course.getCourseId() + ")";
            }

            @Override
            public Course fromString(String string) {
                return null;
            }
        });
    }

    // ===== HELPER METHODS =====
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
