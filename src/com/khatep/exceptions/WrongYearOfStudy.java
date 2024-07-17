package com.khatep.exceptions;

public class WrongYearOfStudy extends RuntimeException{
    public WrongYearOfStudy() {
        super("Year of study starts with 1 and finished with 4!");
    }
}
