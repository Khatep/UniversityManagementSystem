package com.khatep;

import com.khatep.services.DepartmentService;
import com.khatep.models.*;
import java.util.Scanner;
import static com.khatep.enums.Colors.*;
public class UniversityManagementSystemApplication {
    private static final Scanner console = new Scanner(System.in);
    private static int command = -1;
    public static void main(String[] args) {
        runApp();
    }

    private static void runApp() {
        while (command != 0) {
            System.out.println(GREEN.getCode() + "************************" + RESET.getCode());
            printMainMenu();
            try {
                System.out.print(PURPLE.getCode() + "Enter command number: " + RESET.getCode());
                command = console.nextInt();
            } catch (Exception e) {
                System.out.println(RED.getCode() + "Error: Enter a number! (1-5)" + RESET.getCode());
                console.next();
                continue;
            }

            if (command == 1) {
                printAddMenu();
                try {
                    System.out.print(PURPLE.getCode() + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-4)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> System.out.println(DepartmentService.addStudent(console));
                    case 2 -> System.out.println(Course.addCourse(console));
                    case 3 -> System.out.println(Teacher.addTeacher(console));
                    case 4 -> {}
                    default -> System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 4)" + RESET.getCode());
                }
            } else if (command == 2) {
                printControlMenu();
                try {
                    System.out.print(PURPLE.getCode() + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-5)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> System.out.println(DepartmentService.assignTeacherToCourse(console));
                    case 2 -> System.out.println(DepartmentService.enrollStudentToCourse(console));
                    case 3 -> System.out.println(DepartmentService.putStudentsMarks(console));
                    case 4 -> System.out.println(Student.transferStudent(console));
                    case 5 -> {}
                    default -> System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 5)" + RESET.getCode());
                }
            } else if (command == 3) {
                printGetInformationMenu();
                try {
                    System.out.print(PURPLE.getCode() + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-7)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> System.out.println(Student.showInfoStudent(console));
                    case 2 -> System.out.println(Course.showInfoCourse(console));
                    case 3 -> System.out.println(Teacher.showInfoTeachers(console));
                    case 4 -> System.out.println(Student.showInfoAllStudents());
                    case 5 -> System.out.println(Course.showInfoAllCourses());
                    case 6 -> System.out.println(Teacher.showInfoAllTeachers());
                    case 7 -> {}
                    default -> System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 7)" + RESET.getCode());
                }
            } else if (command == 4) {
                printDeleteMenu();
                try {
                    System.out.print(PURPLE.getCode() + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-4)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> System.out.println(DepartmentService.deleteStudent(console));
                    case 2 -> System.out.println(Course.deleteCourse(console));
                    case 3 -> System.out.println(Teacher.deleteTeacher(console));
                    case 4 -> {}
                    default -> System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 4)" + RESET.getCode());
                }
            } else if (command == 5) {
                System.exit(0);
            } else {
                System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 5)" + RESET.getCode());
            }
        }
    }

    public static void printMainMenu() {
        System.out.print(BLUE.getCode() + """
                1. Add menu
                2. Control menu
                3. Get information menu
                4. Delete menu
                5. Exit from system
                """ + RESET.getCode());
    }
    public static void printAddMenu() {
        System.out.print(BLUE.getCode() + """
                1. Add student
                2. Add course
                3. Add teacher
                4. Back
                """ + RESET.getCode());
    }
    public static void printControlMenu() {
        System.out.print(BLUE.getCode() + """
                1. Assign teacher to course
                2. Enroll student to course
                3. Put marks to student`s course
                4. Transfer student to next course
                5. Back
                """ + RESET.getCode());
    }
    public static void printGetInformationMenu() {
        System.out.print(BLUE.getCode() + """
                1. Show information about certain student
                2. Show information about certain course
                3. Show information about certain teacher
                4. Show information about all students
                5. Show information about all courses
                6. Show information about all teachers
                7. Back
                """ + RESET.getCode());
    }
    public static void printDeleteMenu() {
        System.out.print(BLUE.getCode() + """
                1. Delete student
                2. Delete course
                3. Delete teacher
                4. Back
                """ + RESET.getCode());
    }
}