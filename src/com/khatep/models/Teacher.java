package com.khatep.models;

import java.util.*;

import static com.khatep.services.DepartmentService.readIntFromConsole;
import static com.khatep.enums.Colors.*;
public class Teacher extends User {
    protected static final List<Teacher> teachers = new ArrayList<>();

    //key: Course course, value: int amountOfEnrolledStudents
    private final Map<Course, Integer> takenCourse = new HashMap<>();

    private final String degree;
    public Teacher(String name, String surname, Integer id, String degree) {
        super(name, surname, id);
        this.degree = degree;
    }

    public static List<Teacher> getTeachers() {
        return teachers;
    }

    public Map<Course, Integer> getTakenCourse() {
        return takenCourse;
    }

    public String getDegree() {
        return degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(takenCourse, teacher.takenCourse) && Objects.equals(degree, teacher.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), takenCourse, degree);
    }

    public static String addTeacher(Scanner console) {
        System.out.print("Name: ");
        String name = console.next();
        System.out.print("Surname: ");
        String surname = console.next();
        System.out.print("ID: ");
        Integer id = readIntFromConsole(console, "Error: Enter a number for ID!");

        String degree = "";

        for (Teacher t : getTeachers()) {
            if (t.checkID(id))
                return RED.getCode() + "Error: This ID is occupied!" + RESET.getCode();
        }

        for (Student s : Student.getStudents()) {
            if (s.checkID(id))
                return RED.getCode() + "Error: This ID is occupied!" + RESET.getCode();
        }

        Teacher teacher = new Teacher(name, surname, id, degree);
        getTeachers().add(teacher);
        return CYAN.getCode() + "Success: Teacher added!" + RESET.getCode();
    }
    public static String showInfoTeachers(Scanner console) {
        System.out.print("Teachers ID: ");
        Integer id = readIntFromConsole(console, "Error: Enter a number for teacher ID!");

        if (getTeachers().isEmpty())
            return RED.getCode() + "Error: There are not any teachers in Database of System!" + RESET.getCode();
        for (Teacher teacher : getTeachers()) {
            if (id.equals(teacher.getId()))
                return teacher.toString();
        }
        return RED.getCode() + "Error: Teacher did not find!" + RESET.getCode();
    }
    public static String showInfoAllTeachers() {
        if (getTeachers().isEmpty())
            return RED.getCode() + "Error: There are not any teachers!" + RESET.getCode();
        for (Teacher teacher : getTeachers()) {
            System.out.println(teacher);
        }
        return CYAN.getCode() + "Success: That is all!" + RESET.getCode();
    }
    public static Teacher findTeacher(Integer id) {
        Teacher foundedTeacher = null;
        for (Teacher teacher : getTeachers()) {
            if (id.equals(teacher.getId())) {
                foundedTeacher = teacher;
                break;
            }
        }
        return foundedTeacher;
    }

    public static String deleteTeacher(Scanner console) {
        System.out.print("Teacher ID: ");
        Integer teacherID = readIntFromConsole(console, "Error: Enter a number for teacher ID!");
        User foundedTeacher = findTeacher(teacherID);
        if (foundedTeacher == null) {
            return RED.getCode() + "Error: Teacher did not find!" + RESET.getCode();
        }

        getTeachers().remove(foundedTeacher);
        return CYAN.getCode() + "Success: Teacher deleted!" + RESET.getCode();
    }

    public StringBuilder printTakenCourse() {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Course, Integer> set : takenCourse.entrySet()) {
            res
               .append("[")
               .append(set.getKey())
               .append(", Students count: ")
               .append(GREEN.getCode())
               .append(set.getValue())
               .append(RESET.getCode())
               .append("], ");
        }
        if (res.isEmpty()) return new StringBuilder("[]");
        res.deleteCharAt(res.lastIndexOf(","));
        return res;
    }

    @Override
    public String toString() {
        return "Name: " + GREEN.getCode() + getName() + RESET.getCode() +
                ", Surname: " + GREEN.getCode() + getSurname() + RESET.getCode() +
                ", ID: " + GREEN.getCode() + getId() + RESET.getCode() +
                ", Taken courses: " + this.printTakenCourse();
    }

    @Override
    public String add(Scanner console) {
        return null;
    }

    @Override
    public String delete(Scanner console) {
        return null;
    }
}