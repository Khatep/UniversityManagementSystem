package com.khatep.services;

import com.khatep.models.Course;
import com.khatep.models.Student;
import com.khatep.models.Teacher;
import static com.khatep.enums.Colors.*;

import java.util.InputMismatchException;
import java.util.Scanner;
public abstract class DepartmentService {
    private DepartmentService() {
    }

    public static int readIntFromConsole(Scanner console, String errorMessage) {
        int result = 0;
        boolean isInputValid = false;

        while (!isInputValid) {
            try {
                result = console.nextInt();
                isInputValid = true; // Если успешно прочитано целое число, завершаем цикл
            } catch (InputMismatchException e) {
                System.err.print(errorMessage + ", try again: ");
                console.nextLine(); // Очистить буфер после ошибочного ввода
            }
        }
        return result;
    }

    public static int readIntFromConsole(Scanner console, String errorMessage, int limit) {
        int result = 0;
        boolean inputValid = false;

        while (!inputValid) {
            try {
                result = console.nextInt();
                if (result < 1 || result > limit) {
                    System.err.print("Error: Enter right number! (1 - " + limit + "), try again: " );
                    console.nextLine(); // Очистить буфер после ошибочного ввода
                    continue;
                }
                inputValid = true; // Если успешно прочитано целое число, завершаем цикл
            } catch (Exception e) {
                System.err.print(errorMessage + ", try again: ");
                console.nextLine(); // Очистить буфер после ошибочного ввода
            }
        }
        return result;
    }

    private static void increaseCountOfStudents(Teacher foundedTeacher, Course foundedCourse) {
        Integer countOfStudents = foundedTeacher.getTakenCourse().get(foundedCourse);
        foundedTeacher.getTakenCourse().put(foundedCourse, ++countOfStudents);
    }

    private static void decreaseCountOfStudents(Teacher foundedTeacher, Course foundedCourse) {
        Integer countOfStudents = foundedTeacher.getTakenCourse().get(foundedCourse);
        if (countOfStudents == null)
            countOfStudents = 0;

        if (countOfStudents != 0)
            foundedTeacher.getTakenCourse().put(foundedCourse, countOfStudents - 1);
    }

    private static void addCreditToStudent(Student foundedStudent, Integer amountOfCreditsFound) {
        Integer creditCount = foundedStudent.getCreditCount() + amountOfCreditsFound;
        foundedStudent.setCreditCount(creditCount);
    }
    public static String assignTeacherToCourse(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();
        System.out.print("Teacher ID: ");
        Integer teacherID = readIntFromConsole(console, "Error: Enter a number for teacher ID!");

        Course foundedCourse = Course.findCourse(courseName);
        //if course doesn't exist.
        if (foundedCourse == null)
            return RED.getCode() + "Error: Course did not find!" + RESET.getCode();

        Teacher foundedTeacher = Teacher.findTeacher(teacherID);
        if (foundedTeacher == null)
            return RED.getCode() + "Error: Teacher did not find!" + RESET.getCode();

        if (foundedTeacher.getTakenCourse().containsKey(foundedCourse))
            return RED.getCode() + "Error: Teacher already signed up for this course!" + RESET.getCode();

        if (foundedTeacher.getTakenCourse().size() == 2)
            return RED.getCode() + "Error: Teacher already signed up for two courses, therefore you cannot give one more!" + RESET.getCode();

        foundedTeacher.getTakenCourse().put(foundedCourse, 0);
        return CYAN.getCode() + "Success: Teacher takes course!" + RESET.getCode();
    }
    public static String enrollStudentToCourse(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();

        System.out.print("Student ID: ");
        Integer studentID = readIntFromConsole(console, "Error: Enter a number for student ID!");

        System.out.print("Teacher ID: ");
        Integer teacherID = readIntFromConsole(console, "Error: Enter a number for teacher ID!");

        //Find Courses
        Course foundedCourse = Course.findCourse(courseName);
        Integer amountOfCreditsFound;
        if (foundedCourse == null)
            return RED.getCode() + "Error: Course did not find!" + RESET.getCode();
        else
            amountOfCreditsFound = foundedCourse.amountOfCredits();

        Student foundedStudent = Student.findStudent(studentID);
        if (foundedStudent == null)
            return RED.getCode() + "Error: Student did not find!" + RESET.getCode();

        Teacher foundedTeacher = Teacher.findTeacher(teacherID);
        if (foundedTeacher == null)
            return RED.getCode() + "Error: Teacher did not find!" + RESET.getCode();

        //check whether the teacher is enrolled to the course
        if (!foundedTeacher.getTakenCourse().containsKey(foundedCourse)) 
            return RED.getCode() + "Error: Teacher did not enroll for this course, so you cannot enroll student!" + RESET.getCode();
        
        //check count of students
        if (foundedTeacher.getTakenCourse().get(foundedCourse) == 20)
            return RED.getCode() + "Error: There are 20 students in course, it is maximum!" + RESET.getCode();

        //check whether the student is enrolled or past this course
        if (foundedStudent.getEnrolledCourses().contains(foundedCourse))
            return RED.getCode() + "Error: Student already enrolled for this course!" + RESET.getCode();

        if (foundedStudent.getPastCourses().containsKey(foundedCourse))
            return RED.getCode() + "Error: Student already passed (had previously done) this course!" + RESET.getCode();

        //check credits of Students
        if (foundedStudent.getCreditCount() == 20)
            return RED.getCode() + "Error: Student already has 20 credits" + RESET.getCode();
        else if (foundedStudent.getCreditCount() + amountOfCreditsFound > 20)
            return RED.getCode() + "Error: You cannot enroll student to this course, because if will do it, student`s credit count will be "
                    + (foundedStudent.getCreditCount() + amountOfCreditsFound) + ", maximum credit count 20!" + RESET.getCode();

        addCreditToStudent(foundedStudent, amountOfCreditsFound);
        increaseCountOfStudents(foundedTeacher, foundedCourse);
        //enroll to Course
        foundedStudent.getEnrolledCourses().add(foundedCourse);
        return CYAN.getCode() + "Success: Student added to course, so there are/is " +
                foundedTeacher.getTakenCourse().get(foundedCourse) + " students in this course!" + RESET.getCode();
    }
    public static String putStudentsMarks(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();

        System.out.print("Student ID: ");
        Integer studentID = readIntFromConsole(console,  "Error: Enter a number for student ID!" + RESET.getCode());

        System.out.print("Teacher ID: ");
        Integer teacherID = readIntFromConsole(console,  "Error: Enter a number for teacher ID!" + RESET.getCode());
        System.out.print("Mark: ");
        int mark = readIntFromConsole(console, "Error: Enter a number for mark!" + RESET.getCode());

        Course foundedCourse = Course.findCourse(courseName);
        //if course doesn't exist.
        if (foundedCourse == null)
            return RED.getCode() + "Error: Course did not find!" + RESET.getCode();

        Student foundedStudent = Student.findStudent(studentID);
        //if student doesn't exist.
        if (foundedStudent == null)
            return RED.getCode() + "Error: Student did not find!" + RESET.getCode();

        Teacher foundedTeacher = Teacher.findTeacher(teacherID);
        //if teacher doesn't exist.
        if (foundedTeacher == null)
            return RED.getCode() + "Error: Teacher did not find!" + RESET.getCode();

        if (foundedStudent.getPastCourses().containsKey(foundedCourse))
            return RED.getCode() + "Error: Student already passed (had previously done) this course!" + RESET.getCode();

        boolean isStudentEnroll = foundedStudent.getEnrolledCourses().contains(foundedCourse);

        if (!isStudentEnroll)
            return RED.getCode() + "Error: Student did not enroll to this course!" + RESET.getCode();

        boolean isTeacherTookCourse = foundedTeacher.getTakenCourse().containsKey(foundedCourse);
        if (!isTeacherTookCourse)
            return RED.getCode() + "Error: Teacher did not take this course! Check the correct teacher ID!" + RESET.getCode();

        //mark can be from 0 to 100
        if (mark < 0)
            return RED.getCode() + "Error: Mark cannot be given because it cannot be less than 0" + RESET.getCode();
        else if (mark > 100)
            return RED.getCode() + "Error: Mark cannot be given because it cannot be more than 100" + RESET.getCode();

        decreaseCountOfStudents(foundedTeacher, foundedCourse);
        foundedStudent.getEnrolledCourses().remove(foundedCourse);
        //put Mark to the course
        foundedStudent.getPastCourses().put(foundedCourse, mark);
        return CYAN.getCode() + "Success: Mark put!" + RESET.getCode();
    }

    public static String addStudent(Scanner console) {
        return new Student().add(console);
    }

    public static String deleteStudent(Scanner console) {
        return new Student().delete(console);
    }
}