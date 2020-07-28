package com.artur.students.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@SequenceGenerator(name = "seqIdGen", initialValue = 20000, allocationSize = 1)
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGen")
    private Long id;

    @NotBlank
    private String firstName;

    @NotEmpty
    @Size(min = 3)
    private String lastName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
