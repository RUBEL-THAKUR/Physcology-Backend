package com.healthcare.bean.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PsychologistDto {
    private String firstName;
    private String lastName;
    private String fullName;
    private String category;
    private String email;
    private String phone;
    private String specialization;
    private LocalDate dob;

    private String language;
    private String briefDescription;
    private String currentAddress;
    private String country;
    private String timeZone;
    private List<ExperienceDto> experienceList;
}