package com.khatep.exceptions;

public class AverageMarkLowException extends RuntimeException {
    public AverageMarkLowException() {
        super("Average mark is less than 50!");
    }
}