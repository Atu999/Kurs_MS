package com.artur.students.service;

import com.artur.students.exception.StudentError;
import com.artur.students.exception.StudentException;
import com.artur.students.model.Student;
import com.artur.students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(Student.Status status) {
        if (status != null) {
            return studentRepository.findAllByStatus(status);
        }
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        if (!Student.Status.ACTIVE.equals(student.getStatus())) {
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        }
        return student;
    }

    public Student addStudent(Student student) {
        validateStudentEmailExists(student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Student.Status.INACTIVE);
        studentRepository.save(student);
    }

    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!studentFromDb.getEmail().equals(student.getEmail()) &&
                            studentRepository.existsByEmail(student.getEmail())) {
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
                    }
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    studentFromDb.setStatus(student.getStatus());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    private void validateStudentEmailExists(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
        }
    }

    public Student patchStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getStatus())) {
                        studentFromDb.setStatus(student.getStatus());
                    }
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    public List<Student> getStudentsByEmails(List<String> emails) {
        return studentRepository.findAllByEmailIn(emails);
    }
}
