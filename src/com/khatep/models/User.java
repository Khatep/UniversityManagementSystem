package com.khatep.models;

import java.util.Objects;
import java.util.Scanner;

public abstract class User  {
    private final String name;
    private final String surname;
    private Integer id;

    protected User() {
        this.name = "Default";
        this.surname = "Default";
    }

    protected User(String name, String surname, Integer id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        //It is checking only id param (and doesn`t check class), cuz we have Teacher and Student classes.
        if (this == o) return true;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public boolean checkID(Integer id) {
        return Objects.equals(this.id, id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User {" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", ID=" + id +
                '}';
    }
    public abstract String add(Scanner console);
    public abstract String delete(Scanner console);

}
