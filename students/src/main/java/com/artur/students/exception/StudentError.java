package com.artur.students.exception;

public enum StudentError {

    STUDENT_NOT_FOUND("Student does not exists"),
    STUDENT_EMAIL_ALREADY_EXISTS("Student email already exists"),
    STUDENT_IS_NOT_ACTIVE("Student is not active");

    private String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
