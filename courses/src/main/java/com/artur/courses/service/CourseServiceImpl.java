package com.artur.courses.service;

import com.artur.courses.exception.CourseError;
import com.artur.courses.exception.CourseException;
import com.artur.courses.model.Course;
import com.artur.courses.model.CourseMember;
import com.artur.courses.model.dto.NotificationInfoDto;
import com.artur.courses.model.dto.StudentDto;
import com.artur.courses.repostiory.CourseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    public static final String EXCHANGE_ENROLL_FINISH = "enroll_finish";
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient, RabbitTemplate rabbitTemplate) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
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
        List<@NotNull String> emailsMembers = getCourseMembersEmails(course);
        return studentServiceClient.getStudentsByEmails(emailsMembers);
    }

    public void courseFinishEnroll(String courseCode) {
        Course course = getCourse(courseCode);

        if (Course.Status.INACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_INACTIVE);
        }
        course.setStatus(Course.Status.INACTIVE);
        courseRepository.save(course);

        sendMessageToRabbitMq(course);
    }

    private void sendMessageToRabbitMq(Course course) {
        NotificationInfoDto notificationInfo = createNotificationInfo(course);

        rabbitTemplate.convertAndSend(EXCHANGE_ENROLL_FINISH, notificationInfo);
    }

    private NotificationInfoDto createNotificationInfo(Course course) {
        List<@NotNull String> emailsMembers = getCourseMembersEmails(course);

        return NotificationInfoDto.builder()
                .courseCode(course.getCode())
                .courseName(course.getName())
                .courseDescription(course.getDescription())
                .courseStartDate(course.getStartDate())
                .courseEndDate(course.getEndDate())
                .emails(emailsMembers)
                .build();
    }

    private List<@NotNull String> getCourseMembersEmails(Course course) {
        return course.getCourseMembers().stream()
                .map(CourseMember::getEmail).collect(Collectors.toList());
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
