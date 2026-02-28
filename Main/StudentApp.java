package com.capgemini.jdbc.Main;


import java.util.Scanner;

public class StudentApp {

    public static void validate(Student student) throws InvalidStudentDataException {

        if (!student.getEmail().contains("@"))
            throw new InvalidStudentDataException("Email must contain @");

        if (student.getAge() <= 0)
            throw new InvalidStudentDataException("Age must be positive");

        if (!student.getMobile().matches("\\d{10}"))
            throw new InvalidStudentDataException("Mobile must be exactly 10 digits");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO dao = new PostgreStudentDAO();   

        try {
            dao.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {

            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Mobile: ");
                        String mobile = sc.nextLine();

                        Student s = new Student(name, email, age, mobile);

                        validate(s);
                        dao.addStudent(s);
                        break;

                    case 2:
                        dao.viewStudents();
                        break;

                    case 3:

                        System.out.print("Enter ID to update: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("New Email: ");
                        String newEmail = sc.nextLine();

                        System.out.print("New Age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Mobile: ");
                        String newMobile = sc.nextLine();

                        Student updated = new Student(id, newName, newEmail, newAge, newMobile);

                        validate(updated);
                        dao.updateStudent(updated);
                        break;

                    case 4:

                        System.out.print("Enter ID to delete: ");
                        int deleteId = sc.nextInt();

                        dao.deleteStudent(deleteId);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (InvalidStudentDataException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }
    }
}