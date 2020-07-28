package com.artur.courses.service;

import com.artur.courses.exception.CourseError;
import com.artur.courses.exception.CourseException;
import com.artur.courses.model.Course;
import com.artur.courses.model.CourseMember;
import com.artur.courses.model.dto.StudentDto;
import com.artur.courses.repostiory.CourseRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public List<Course> getCourses(Course.Status status) {
        if (status != null) {
            return courseRepository.findAllByStatus(status);
        }
        return courseRepository.findAll();
    }

    public Course getCourse(String code) {
        return courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    public Course addCourse(Course course) {
        course.validateCourse();
        return courseRepository.save(course);
    }

    public void courseEnrollment(String courseCode, Long studentId) {
        Course course = getCourse(courseCode);
        validateCourseStatus(course);
        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrollment(course, studentDto);
        course.incrementParticipantsNumber();
        course.getCourseMembers().add(new CourseMember(studentDto.getEmail()));
        courseRepository.save(course);
    }

    public List<StudentDto> getCourseMembers(String courseCode) {
        Course course = getCourse(courseCode);
        List<@NotNull String> emailsMembers = course.getCourseMembers().stream()
                .map(CourseMember::getEmail).collect(Collectors.toList());
        return studentServiceClient.getStudentsByEmails(emailsMembers);
    }

    private void validateStudentBeforeCourseEnrollment(Course course, StudentDto studentDto) {
        if (!StudentDto.Status.ACTIVE.equals(studentDto.getStatus())) {
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }

        if (course.getCourseMembers().stream()
                .anyMatch(member -> studentDto.getEmail().equals(member.getEmail()))) {
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private void validateCourseStatus(Course course) {
        if (!Course.Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }

}
