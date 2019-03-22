package com.student.rating.exception;

public class DuplicateRatingException extends RuntimeException {

    private final int status;

    public DuplicateRatingException(int status,String message) {
        super(message);
        this.status = status;
    }
}
