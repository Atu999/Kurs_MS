package com.artur.notifications.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class EmailDto {

    @NotBlank
    @Email
    private String to;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
