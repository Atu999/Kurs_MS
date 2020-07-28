package com.artur.courses.model;

import com.artur.courses.exception.CourseError;
import com.artur.courses.exception.CourseException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
public class Course {

    @Id
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    @Min(0)
    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long participantsNumber;
    @NotNull
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE,
        FULL
    }

    public void validateCourse(){
        validateCourseDate();
        validateParticipantsLimit();
        validateStatus();
    }

    private void validateCourseDate() {
        if (startDate.isAfter(endDate)) {
            throw new CourseException(CourseError.COURSE_START_DATE_IS_AFTER_END_DATE);
        }
    }

    private void validateParticipantsLimit() {
        if (participantsNumber > participantsLimit) {
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED);
        }
    }

    private void validateStatus() {
        if (Status.FULL.equals(status) && !participantsNumber.equals(participantsLimit)) {
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if(Status.ACTIVE.equals(status) && participantsNumber.equals(participantsLimit) ){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getParticipantsLimit() {
        return participantsLimit;
    }

    public void setParticipantsLimit(Long participantsLimit) {
        this.participantsLimit = participantsLimit;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
