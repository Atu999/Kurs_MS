package com.artur.students.exception;

public class StudentException extends RuntimeException {

    private StudentError studentError;

    public StudentException(StudentError studentError) {
        this.studentError = studentError;
    }

    public StudentError getStudentError() {
        return studentError;
    }
}
