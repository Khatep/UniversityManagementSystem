package com.khatep.exceptions;

public class CreditCountLowException extends RuntimeException {
    public CreditCountLowException() {
        super("Credit count does not equal to 20!");
    }
}
