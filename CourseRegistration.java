import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public boolean isFull() {
        return registeredStudents.size() >= capacity;
    }

    public boolean registerStudent(Student student) {
        if (!isFull()) {
            registeredStudents.add(student);
            return true;
        }
        return false;
    }

    public void removeStudent(Student student) {
        registeredStudents.remove(student);
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents.size();
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
               ", Capacity: " + capacity + ", Schedule: " + schedule + ", Available Slots: " + getAvailableSlots();
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.registerStudent(this);
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent(this);
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name;
    }
}

public class CourseRegistration {
    private List<Course> courses;
    private List<Student> students;

    public CourseRegistration() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayAvailableCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public void registerStudentToCourse(String studentId, String courseCode) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);
        if (student != null && course != null) {
            if (course.registerStudent(student)) {
                student.registerCourse(course);
                System.out.println("Student registered successfully.");
            } else {
                System.out.println("Course is full.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void removeStudentFromCourse(String studentId, String courseCode) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);
        if (student != null && course != null) {
            student.dropCourse(course);
            System.out.println("Student dropped the course successfully.");
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistration cr = new CourseRegistration();

        // Adding some courses with simpler names
        cr.addCourse(new Course("C101", "Math", "Basic math concepts", 30, "MWF 10-11"));
        cr.addCourse(new Course("C102", "Science", "Introduction to science", 25, "TTh 9-10:30"));
        cr.addCourse(new Course("C103", "History", "World history overview", 20, "MWF 11-12"));

        // Adding some students
        cr.addStudent(new Student("S001", "Alice"));
        cr.addStudent(new Student("S002", "Bob"));

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Display Available Courses");
            System.out.println("2. Register Student for Course");
            System.out.println("3. Remove Student from Course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    cr.displayAvailableCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    cr.registerStudentToCourse(studentId, courseCode);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    courseCode = scanner.nextLine();
                    cr.removeStudentFromCourse(studentId, courseCode);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
