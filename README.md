# Student Information System (SIS) - Documentation

## 1. Project Overview & Objectives
The Student Information System is a console-based Java application designed to manage student records efficiently. The core objective is to provide an intuitive interface for administrators to perform CRUD operations while enforcing strict data integrity.

## 2. Setup & Installation Instructions
1. Ensure you have Java Development Kit (JDK 11 or higher) installed.
2. Clone this repository: `git clone https://github.com/your-username/repository-name.git`
3. Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, or VS Code).
4. Run the `StudentInformationSystem.java` file.

## 3. Code Structure Explanation
- **Student.java**: An encapsulated blueprint class holding student attributes (`name`, `age`, `grade`, `studentId`, `contact`) along with custom getters, setters, and format validation logic.
- **StudentInformationSystem.java**: The main controller class handling the interactive `Scanner` menu system, input error handling, and the dynamic `ArrayList` database sequence.

## 4. Technical Requirements Fulfillment
- **Encapsulation**: Attributes are hidden via `private` modifiers and only exposed via controlled getters/setters.
- **Data Validation**: Implemented via regex matching for academic grades and boundary constraints for ages.
- **Dynamic Array Tracking**: Uses an `ArrayList` framework to support infinite scalability of student files during operations.

## 5. Application Demo Screenshots

