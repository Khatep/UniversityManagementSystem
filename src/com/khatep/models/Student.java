package com.khatep.models;

import com.khatep.exceptions.AverageMarkLowException;
import com.khatep.exceptions.CreditCountLowException;
import com.khatep.enums.*;
import com.khatep.exceptions.WrongYearOfStudy;
import com.khatep.interfaces.Transferable;

import java.util.*;

import static com.khatep.services.DepartmentService.readIntFromConsole;
import static com.khatep.enums.Colors.*;

public class Student extends User implements Transferable {
    private static final List<Student> students = new ArrayList<>();
    private String group;
    private Integer yearOfStudy;
    private Integer creditCount = 0;
    private Faculties faculty;
    private Majors major;
    private final List<Course> enrolledCourses = new ArrayList<>();
    private final Map<Course, Integer> pastCourses = new HashMap<>();

    public Student() {
        super();
        this.group = "Default";
        this.faculty = Faculties.FCTC;
        this.major = Majors.MANAGEMENT;
    }

    public Student(String name, String surname, Integer id, String group, Integer yearOfStudy, Faculties faculty, Majors major) {
        super(name, surname, id);
        this.group = group;
        this.yearOfStudy = yearOfStudy;
        this.faculty = faculty;
        this.major = major;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public String getGroup() {
        return group;
    }


    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        try {
             if (yearOfStudy < 1 || yearOfStudy > 4) {
                 throw new WrongYearOfStudy();
             } else {
                this.yearOfStudy = yearOfStudy;
             }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        if (creditCount >= 0 && creditCount <= 20) {
            this.creditCount = creditCount;
        }
    }

    public Faculties getFaculty() {
        return faculty;
    }

    public Majors getMajor() {
        return major;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    public Map<Course, Integer> getPastCourses() {
        return pastCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(group, student.group) && Objects.equals(yearOfStudy, student.yearOfStudy) && Objects.equals(creditCount, student.creditCount) && faculty == student.faculty && major == student.major && Objects.equals(enrolledCourses, student.enrolledCourses) && Objects.equals(pastCourses, student.pastCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), group, yearOfStudy, creditCount, faculty, major, enrolledCourses, pastCourses);
    }

    public String add(Scanner console) {
        System.out.print("Name: ");
        String name = console.next();
        System.out.print("Surname: ");
        String surname = console.next();
        System.out.print("id: ");
        int id = readIntFromConsole(console, "Error: Enter a number for id!");

        for (Student s : Student.getStudents()) {
            if (s.checkID(id))
                return RED.getCode() + "Error: This id is occupied!" + RESET.getCode();
        }

        for (Teacher t : Teacher.getTeachers()) {
            if (t.checkID(id))
                return RED.getCode() + "Error: This id is occupied!" + RESET.getCode();
        }

        System.out.print("Group: ");
        group = console.next();
        System.out.print("Year of study: ");
        yearOfStudy = readIntFromConsole(console, "Error: Enter a number for year of study!", 4);
        if (1 > yearOfStudy || 4 < yearOfStudy)
            return RED.getCode() + "Error: Year of study starts with 1 and finished with 4!" + RESET.getCode();

        System.out.print("""
                     Choose faculty:\s
                     \t 1. FACULTY OF BUSINESS, MEDIA AND MANAGEMENT\s
                     \t 2. FACULTY OF COMPUTER TECHNOLOGIES AND CYBER SECURITY
                     """);

        int command = readIntFromConsole(console, "Error: Enter a number (1 or 2) for command!", 2);

        if (command == 1)
            faculty = Faculties.FBMM;
        else if (command == 2)
            faculty = Faculties.FCTC;

        System.out.print("""
                Choose major:
                \t 1. Information systems
                \t 2. Software engineering
                \t 3. Computer science
                \t 4. Data science
                \t 5. Computer security
                \t 6. Management
                \t 7. Finance management
                \t 8. Digital journalism
                """);
        command = readIntFromConsole(console, "Error: Enter a number (1 - 8) for command!", 8);

        switch (command) {
            case 1 -> major = Majors.INFORMATION_SYSTEMS;
            case 2 -> major = Majors.SOFTWARE_ENGINEERING;
            case 3 -> major = Majors.COMPUTER_SCIENCE;
            case 4 -> major = Majors.DATA_SCIENCE;
            case 5 -> major = Majors.COMPUTER_SECURITY;
            case 6 -> major = Majors.MANAGEMENT;
            case 7 -> major = Majors.FINANCE_MANAGEMENT;
            case 8 -> major = Majors.DIGITAL_JOURNALISM;
            default -> major = Majors.UNKNOWN;
        }

        Student student = new Student(name, surname, id, group, yearOfStudy, faculty, major);
        getStudents().add(student);
        return CYAN.getCode() + "Success: Student added!" + RESET.getCode();
    }

    public static String showInfoStudent(Scanner console) {
        System.out.print("Student ID: ");
        Integer id = readIntFromConsole(console, "Error: Enter a number for student ID!");
        if (getStudents().isEmpty())
            return RED.getCode() + "Error: There are not students in DataBase System!" + RESET.getCode();

        for (User student : getStudents()) {
            if (id.equals(student.getId()))
                return student.toString();
        }
        return RED.getCode() + "Error: Student did not find!" + RESET.getCode();
    }

    public static String showInfoAllStudents() {
        if (getStudents().isEmpty())
            return RED.getCode() + "Error: There are not students in DataBase System!" + RESET.getCode();

        for (Student student : getStudents()) {
            System.out.println(student.toString());
        }
        return CYAN.getCode() + "Success: That is all!" + RESET.getCode();
    }

    public static Student findStudent(Integer id) {
        Student foundedStudent = null;
        for (Student student : getStudents()) {
            if (id.equals(student.getId())) {
                foundedStudent = student;
                break;
            }
        }
        return foundedStudent;
    }

    public String delete(Scanner console) {
        System.out.print("Student ID: ");
        Integer studentID = readIntFromConsole(console,  "Error: Enter a number for student ID!");
        User foundedStudent = findStudent(studentID);

        if (foundedStudent == null)
            return RED.getCode() + "Error: Student did not find!" + RESET.getCode();
        
        getStudents().remove(foundedStudent);
        return CYAN.getCode() + "Success: Student deleted!" + RESET.getCode();
    }

    public StringBuilder printEnrolledCourses() {
        StringBuilder res = new StringBuilder();
        for (Course c : getEnrolledCourses()) {
            res.append("[").append(c).append("], ");
        }
        if (res.isEmpty()) return new StringBuilder("{}, ");
        return res;
    }

    public StringBuilder printPastCourses() {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Course, Integer> set : getPastCourses().entrySet()) {
            res
               .append("[")
               .append(set.getKey())
               .append(", Mark: ")
               .append(GREEN.getCode())
               .append(set.getValue())
               .append(RESET.getCode())
               .append("], ");
        }
        if (res.isEmpty()) return new StringBuilder("[], ");
        return res;
    }
    @Override
    public String toString() {
        String green = String.valueOf(GREEN.getCode());
        return "Name: " + green + getName() + RESET.getCode() +
                ", Surname: " + green + getSurname() + RESET.getCode() +
                ", ID: " + green + getId() + RESET.getCode() +
                ", Group: " + green + getGroup() + RESET.getCode() +
                ", Year of the study: " + green + getYearOfStudy() + RESET.getCode() +
                ", Credit number: " + green + getCreditCount() + RESET.getCode() +
                ", Enrolled courses: " + printEnrolledCourses() + "Past courses = " + printPastCourses() +
                "Student faculty is: " + green + getFaculty() + RESET.getCode() +
                ", Student major is: " + green + getMajor() + RESET.getCode();
    }

    public static String transferStudent(Scanner console) {
        System.out.print("Student ID: ");
        Integer studentID = readIntFromConsole(console, "Error: Enter a number for student ID!" + RESET.getCode());
        Student foundedStudent = findStudent(studentID);
        if (foundedStudent == null)
            return RED.getCode() + "Error: Student did not find!" + RESET.getCode();
        return foundedStudent.transfer(studentID);
    }

    @Override
    public String transfer(Integer id) {
        Integer sumOfMarks = 0;
        int countOfCourses = 0;
        for (Map.Entry<Course, Integer> set : getPastCourses().entrySet()) {
            sumOfMarks += set.getValue();
            countOfCourses++;
        }

        double avg = 1.0 * sumOfMarks/countOfCourses;
        if (avg >= 50 && getCreditCount() == 20 && getYearOfStudy() <= 4) {
            setCreditCount(0);
            setYearOfStudy(getYearOfStudy() + 1);
            return CYAN.getCode() + "Success: Student transferred!" + RESET.getCode();
        }
        else {
            try {
                if (avg < 50)
                    throw new AverageMarkLowException();
                else if (getCreditCount() != 20)
                    throw new CreditCountLowException();
            } catch (AverageMarkLowException | CreditCountLowException ex) {
                return RED.getCode() + "Error: Student cannot transferred!, because " + ex.getMessage() + RESET.getCode();
            }
        }
        return RED.getCode() + "Error: Student cannot transferred!" + RESET.getCode();
    }
}