package com.artur.notifications.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class NotificationInfoDto {

    private List<String> emails;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseEndDate;
}
