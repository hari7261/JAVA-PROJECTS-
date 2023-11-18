import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Course class to store course information
class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
}

// Student class to store student information
class Student {
    int id;
    String name;
    List<String> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }
}

// CourseListingFrame to display available courses
class CourseListingFrame extends JFrame {
    private JTextArea courseTextArea;

    public CourseListingFrame(List<Course> courses) {
        setTitle("Course Listing");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        courseTextArea = new JTextArea();
        for (Course course : courses) {
            courseTextArea.append(course.code + " - " + course.title + " (" + course.schedule + ")\n");
        }

        JScrollPane scrollPane = new JScrollPane(courseTextArea);
        add(scrollPane);

        setVisible(true);
    }
}

// StudentRegistrationFrame to allow students to register for courses
class StudentRegistrationFrame extends JFrame {
    private JComboBox<String> courseComboBox;
    private JButton registerButton;
    private Student student;
    private List<Course> availableCourses;

    public StudentRegistrationFrame(Student student, List<Course> availableCourses) {
        this.student = student;
        this.availableCourses = availableCourses;

        setTitle("Student Registration");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        courseComboBox = new JComboBox<>();
        for (Course course : availableCourses) {
            courseComboBox.addItem(course.code + " - " + course.title);
        }

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                student.registeredCourses.add(selectedCourse);
                JOptionPane.showMessageDialog(null, "Registration successful for " + student.name);
            }
        });

        JPanel panel = new JPanel();
        panel.add(courseComboBox);
        panel.add(registerButton);
        add(panel);

        setVisible(true);
    }
}

// CourseRemovalFrame to enable students to drop courses
class CourseRemovalFrame extends JFrame {
    private JComboBox<String> courseComboBox;
    private JButton removeButton;
    private Student student;

    public CourseRemovalFrame(Student student) {
        this.student = student;

        setTitle("Course Removal");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        courseComboBox = new JComboBox<>();
        for (String course : student.registeredCourses) {
            courseComboBox.addItem(course);
        }

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                student.registeredCourses.remove(selectedCourse);
                JOptionPane.showMessageDialog(null, "Course removed successfully for " + student.name);
            }
        });

        JPanel panel = new JPanel();
        panel.add(courseComboBox);
        panel.add(removeButton);
        add(panel);

        setVisible(true);
    }
}

// AddCourseFrame to add a new course to the database
class AddCourseFrame extends JFrame {
    private JTextField codeField;
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField capacityField;
    private JTextField scheduleField;
    private JButton addButton;
    private List<Course> courses;

    public AddCourseFrame(List<Course> courses) {
        this.courses = courses;

        setTitle("Add Course");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        codeField = new JTextField(10);
        titleField = new JTextField(10);
        descriptionField = new JTextField(10);
        capacityField = new JTextField(10);
        scheduleField = new JTextField(10);

        addButton = new JButton("Add Course");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeField.getText();
                String title = titleField.getText();
                String description = descriptionField.getText();
                int capacity = Integer.parseInt(capacityField.getText());
                String schedule = scheduleField.getText();

                Course newCourse = new Course(code, title, description, capacity, schedule);
                courses.add(newCourse);

                JOptionPane.showMessageDialog(null, "Course added successfully.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Code:"));
        panel.add(codeField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Schedule:"));
        panel.add(scheduleField);
        panel.add(addButton);
        add(panel);

        setVisible(true);
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        // Initialize courses
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CSCI101", "Introduction to Programming", "Learn the basics of programming", 30, "Mon/Wed 9:00 AM"));
        courses.add(new Course("MATH201", "Calculus I", "Introduction to calculus", 25, "Tue/Thu 10:30 AM"));

        // Initialize students
        Student student1 = new Student(1, "John Doe");

        // Display course listing
        CourseListingFrame courseListingFrame = new CourseListingFrame(courses);

        // Allow student1 to register for courses
        StudentRegistrationFrame registrationFrame = new StudentRegistrationFrame(student1, courses);

        // Display course listing again after registration
        CourseListingFrame updatedCourseListingFrame = new CourseListingFrame(courses);

        // Allow student1 to remove registered courses
        CourseRemovalFrame removalFrame = new CourseRemovalFrame(student1);

        // Display course listing again after removal
        CourseListingFrame finalCourseListingFrame = new CourseListingFrame(courses);

        // Allow adding a new course to the database
        AddCourseFrame addCourseFrame = new AddCourseFrame(courses);

        // Display course listing after adding a new course
        CourseListingFrame finalCourseListingFrameWithNewCourse = new CourseListingFrame(courses);
    }
}
