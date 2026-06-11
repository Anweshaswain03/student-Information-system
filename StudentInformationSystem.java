import java.util.ArrayList;
import java.util.Scanner;

// ==========================================
// 1. STUDENT CLASS DESIGN
// ==========================================
class Student {
    private String name;
    private int age;
    private String grade;
    private String studentId;
    private String contact;

    // Constructor
    public Student(String name, int age, String grade, String studentId, String contact) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.studentId = studentId;
        this.contact = contact;
    }

    // Getters and Setters for Data Access and Updates
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getStudentId() { return studentId; }
    // studentId typically shouldn't change, so we only need a getter

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    // Formatted String representation of a student row
    @Override
    public String toString() {
        return String.format("| %-12s | %-15s | %-5d | %-6s | %-12s |", 
                studentId, name, age, grade, contact);
    }
}

// ==========================================
// 2. MAIN SYSTEM & INTERFACE
// ==========================================
public class StudentInformationSystem {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample baseline data for quick testing
        studentList.add(new Student("Alex Vance", 19, "A", "STU101", "9876543210"));
        studentList.add(new Student("Bella Goth", 21, "B+", "STU102", "8765432109"));

        boolean running = true;
        System.out.println("=== Welcome to the Student Information System ===");

        while (running) {
            printMenu();
            int choice = readValidInteger("Enter your choice (1-7): ");

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: updateStudent(); break;
                case 4: deleteStudent(); break;
                case 5: searchStudent(); break;
                case 6: printSystemStats(); break;
                case 7: 
                    System.out.println("\nExiting system. Data cleared from volatile memory. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("⚠️ Invalid choice! Please select an option between 1 and 7.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("                MAIN MENU                  ");
        System.out.println("-------------------------------------------");
        System.out.println("1. Add New Student Record");
        System.out.println("2. View All Student Records");
        System.out.println("3. Update Existing Student");
        System.out.println("4. Delete Student Record");
        System.out.println("5. Search Student (by ID or Name)");
        System.out.println("6. View System Overview Summary");
        System.out.println("7. Exit Program");
        System.out.println("-------------------------------------------");
    }

    // ==========================================
    // CRUD OPERATIONS & CORE FEATURES
    // ==========================================

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        String id;
        while (true) {
            System.out.print("Enter Unique Student ID: ");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("⚠️ ID cannot be blank.");
                continue;
            }
            if (findStudentById(id) != null) {
                System.out.println("⚠️ Error: A student with ID " + id + " already exists.");
            } else {
                break;
            }
        }

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine().trim();

        // Data Validation: Age must be a positive number
        int age;
        while (true) {
            age = readValidInteger("Enter Age: ");
            if (age > 0 && age < 120) {
                break;
            }
            System.out.println("⚠️ Invalid entry. Please enter a realistic positive age.");
        }

        // Data Validation: Grade matching common academic marks
        String grade;
        while (true) {
            System.out.print("Enter Grade (e.g., A, B+, C, F): ");
            grade = scanner.nextLine().trim().toUpperCase();
            if (grade.matches("^[A-F][+-]?$") || grade.equals("I")) { 
                break;
            }
            System.out.println("⚠️ Invalid grade standard format. Try values like A, B+, C, D-, F.");
        }

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine().trim();

        studentList.add(new Student(name, age, grade, id, contact));
        System.out.println("🎉 Student record successfully saved!");
    }

    private static void viewAllStudents() {
        System.out.println("\n--- Registered Student Records ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found in the system database.");
            return;
        }
        printTableHeader();
        for (Student s : studentList) {
            System.out.println(s);
        }
        printTableFooter();
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        System.out.print("Enter the ID of the student to update: ");
        String id = scanner.nextLine().trim();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("❌ No student found with ID: " + id);
            return;
        }

        System.out.println("\nLeave field blank and hit Enter to retain current values.");
        
        System.out.print("Update Name [" + student.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) student.setName(name);

        while (true) {
            System.out.print("Update Age [" + student.getAge() + "]: ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) break;
            try {
                int age = Integer.parseInt(ageInput);
                if (age > 0 && age < 120) {
                    student.setAge(age);
                    break;
                }
                System.out.println("⚠️ Please enter a valid positive age.");
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Age must be a number.");
            }
        }

        while (true) {
            System.out.print("Update Grade [" + student.getGrade() + "]: ");
            String gradeInput = scanner.nextLine().trim().toUpperCase();
            if (gradeInput.isEmpty()) break;
            if (gradeInput.matches("^[A-F][+-]?$") || gradeInput.equals("I")) {
                student.setGrade(gradeInput);
                break;
            }
            System.out.println("⚠️ Invalid grade standard format.");
        }

        System.out.print("Update Contact [" + student.getContact() + "]: ");
        String contact = scanner.nextLine().trim();
        if (!contact.isEmpty()) student.setContact(contact);

        System.out.println("✨ Student record updated successfully!");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        System.out.print("Enter the ID of the student to remove: ");
        String id = scanner.nextLine().trim();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("❌ No student found with ID: " + id);
            return;
        }

        System.out.print("Are you sure you want to permanently delete " + student.getName() + "? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            studentList.remove(student);
            System.out.println("🗑️ Record deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void searchStudent() {
        System.out.println("\n--- Search Directory ---");
        System.out.println("1. Search by Student ID");
        System.out.println("2. Search by Student Name");
        int method = readValidInteger("Select search criteria: ");

        if (method == 1) {
            System.out.print("Enter exact Student ID: ");
            String id = scanner.nextLine().trim();
            Student s = findStudentById(id);
            if (s != null) {
                printTableHeader();
                System.out.println(s);
                printTableFooter();
            } else {
                System.out.println("❌ Match not found for ID: " + id);
            }
        } else if (method == 2) {
            System.out.print("Enter search name pattern: ");
            String namePart = scanner.nextLine().trim().toLowerCase();
            boolean found = false;

            for (Student s : studentList) {
                if (s.getName().toLowerCase().contains(namePart)) {
                    if (!found) {
                        printTableHeader();
                        found = true;
                    }
                    System.out.println(s);
                }
            }
            if (found) printTableFooter();
            else System.out.println("❌ No student names match the search input.");
        } else {
            System.out.println("⚠️ Selection dropped. Returning to main menu.");
        }
    }

    private static void printSystemStats() {
        System.out.println("\n--- System Metrics ---");
        System.out.println("Total Registered Records: " + studentList.size());
        if(studentList.isEmpty()) return;

        double ageSum = 0;
        for(Student s : studentList) ageSum += s.getAge();
        System.out.printf("Average Cohort Age: %.1f years\n", (ageSum / studentList.size()));
    }

    // ==========================================
    // UTILITY & FORMATTING HELPER METHODS
    // ==========================================

    private static Student findStudentById(String id) {
        for (Student s : studentList) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    private static int readValidInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Input Error: Please enter a valid numerical value.");
            }
        }
    }

    private static void printTableHeader() {
        System.out.println("+--------------+-----------------+-------+--------+--------------+");
        System.out.println("| Student ID   | Name            | Age   | Grade  | Contact      |");
        System.out.println("+--------------+-----------------+-------+--------+--------------+");
    }

    private static void printTableFooter() {
        System.out.println("+--------------+-----------------+-------+--------+--------------+");
    }
}