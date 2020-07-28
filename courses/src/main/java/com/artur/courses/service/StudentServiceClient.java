package com.artur.courses.service;

import com.artur.courses.model.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "STUDENT-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {

    @GetMapping("/{studentId}")
    Student getStudentById(@PathVariable Long studentId);

}
