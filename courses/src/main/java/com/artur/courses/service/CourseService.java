package com.artur.courses.service;

import com.artur.courses.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course getCourse(String code);

    Course addCourse(Course course);
}
