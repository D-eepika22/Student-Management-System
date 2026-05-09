import java.util.*;
import java.io.*;

class Student {

    int id;
    String name;
    int age;

    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    void show() {
        System.out.println("ID: " + id + "   Name: " + name + "   Age: " + age);
    }
}

public class StudentManagement {

    static Student[] students = new Student[100];

    static int count = 0;

    static final String FILE_NAME = "students.txt";

    // Load data from file
    static void loadStudents() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return;
            }

            BufferedReader br =
                new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);

                students[count] =
                    new Student(id, name, age);

                count++;
            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error loading file.");
        }
    }

    // Save data to file
    static void saveStudents() {

        try {

            BufferedWriter bw =
                new BufferedWriter(new FileWriter(FILE_NAME));

            for (int i = 0; i < count; i++) {

                bw.write(
                    students[i].id + "," +
                    students[i].name + "," +
                    students[i].age
                );

                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {

            System.out.println("Error saving file.");
        }
    }

    // Add student
    static void addStudent(Scanner sc) {

        if (count >= 100) {

            System.out.println("Student limit reached.\n");
            return;
        }

        System.out.print("Enter ID: ");

        int id = sc.nextInt();

        // Duplicate ID check
        for (int i = 0; i < count; i++) {

            if (students[i].id == id) {

                System.out.println("ID already exists.\n");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.next();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        students[count] =
            new Student(id, name, age);

        count++;

        saveStudents();

        System.out.println("Student added successfully.\n");
    }

    // Display students in sorted order
    static void displayStudents() {

        if (count == 0) {

            System.out.println("No student found.\n");
            return;
        }

        // Sort by ID
        for (int i = 0; i < count - 1; i++) {

            for (int j = i + 1; j < count; j++) {

                if (students[i].id > students[j].id) {

                    Student temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }
            }
        }

        System.out.println("\n===== Student List =====\n");

        for (int i = 0; i < count; i++) {

            students[i].show();
        }

        System.out.println();
    }

    // Search student
    static void searchStudent(Scanner sc) {

        System.out.print("Enter ID to search: ");

        int id = sc.nextInt();

        boolean found = false;

        for (int i = 0; i < count; i++) {

            if (students[i].id == id) {

                System.out.println("\nStudent Found:");

                students[i].show();

                found = true;

                break;
            }
        }

        if (!found) {

            System.out.println("Student not found.\n");
        }
    }

    // Delete student
    static void deleteStudent(Scanner sc) {

        System.out.print("Enter ID to delete: ");

        int id = sc.nextInt();

        boolean found = false;

        for (int i = 0; i < count; i++) {

            if (students[i].id == id) {

                for (int j = i; j < count - 1; j++) {

                    students[j] = students[j + 1];
                }

                count--;

                saveStudents();

                System.out.println("Student deleted successfully.\n");

                found = true;

                break;
            }
        }

        if (!found) {

            System.out.println("Student not found.\n");
        }
    }

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        int choice;

        loadStudents();

        do {

            System.out.println("===== Student Management System =====");

            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent(sc);
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    searchStudent(sc);
                    break;

                case 4:
                    deleteStudent(sc);
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice.\n");
            }

        } while (choice != 5);

        sc.close();
    }
}