package com.khatep.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static com.khatep.services.DepartmentService.readIntFromConsole;
import static com.khatep.enums.Colors.*;
public record Course(String courseName, Integer amountOfCredits) {
    private static final Set<Course> courses = new HashSet<>();
    public static Set<Course> getCourses() {
        return courses;
    }
    public static String addCourse(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();
        System.out.print("Amount of credits: ");
        Integer amountOfCredits = readIntFromConsole(console, RED.getCode() + "Error: Enter a number for amount of credits!" + RESET.getCode());
        Course course = new Course(courseName, amountOfCredits);
        for (Course s : getCourses()) {
            if (course.equals(s)) {
                return RED.getCode() + "Error: This course already exists!" + RESET.getCode();
            }
        }
        getCourses().add(course);
        return CYAN.getCode() + "Success: Course added!" + RESET.getCode();
    }

    public static String showInfoCourse(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();
        if (getCourses().isEmpty()) {
            return RED.getCode() + "Error: There are not courses in Database of System!" + RESET.getCode();
        }
        for (Course course : getCourses()) {
            if (courseName.equals(course.courseName())) {
                return course.toString();
            }
        }
        return RED.getCode() + "Error: Course did not find!" + RESET.getCode();
    }

    public static String showInfoAllCourses() {
        if (getCourses().isEmpty()) {
            return RED.getCode() + "Error: There are not any Courses!" + RESET.getCode();
        }
        for (Course course : getCourses()) {
            System.out.println(course.toString());
        }
        return CYAN.getCode() + "Success: That is all!" + RESET.getCode();
    }

    public static Course findCourse(String courseName) {
        Course foundedCourse = null;
        for (Course course : Course.getCourses()) {
            if (courseName.equals(course.courseName())) {
                foundedCourse = course;
                break;
            }
        }
        return foundedCourse;
    }

    public static String deleteCourse(Scanner console) {
        System.out.print("Course name: ");
        String courseName = console.next();
        Course foundedCourse = findCourse(courseName);
        if (foundedCourse == null)
            return RED.getCode() + "Error: Course did not find!" + RESET.getCode();

        getCourses().removeIf(foundedCourse::equals);
        return CYAN.getCode() + "Success: Course deleted!" + RESET.getCode();
    }

    @Override
    public String toString() {
        return "Course name: " + GREEN.getCode() + courseName() + RESET.getCode() + " " +
                ", Amount of credits: " + GREEN.getCode() + amountOfCredits() + RESET.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseName.equals(course.courseName) && amountOfCredits.equals(course.amountOfCredits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName);
    }
}
