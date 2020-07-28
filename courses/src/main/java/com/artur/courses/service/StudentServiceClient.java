package com.artur.courses.service;

import com.artur.courses.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {

    @GetMapping("/{studentId}")
    StudentDto getStudentById(@PathVariable Long studentId);

    @PostMapping("/emails")
    List<StudentDto> getStudentsByEmails(@RequestBody List<String> emails);

}
