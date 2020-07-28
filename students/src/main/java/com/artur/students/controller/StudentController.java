package com.artur.students.controller;

import com.artur.students.model.Student;
import com.artur.students.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    return ResponseEntity.ok().body(studentRepository.save(studentFromDb));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    return ResponseEntity.ok().body(studentRepository.save(studentFromDb));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
