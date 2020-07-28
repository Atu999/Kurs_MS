package com.artur.courses.service;

import com.artur.courses.exception.CourseError;
import com.artur.courses.exception.CourseException;
import com.artur.courses.model.Course;
import com.artur.courses.repostiory.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(String code) {
        return courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
}
