package com.healthcare.bean.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TherapistQualificationDTO {


    private UUID therapistId;

    private String degreeType;
    private String degreeIn;
    private String instituteUniversity;
    private Integer yearOfPassing;
    private String percentageCgpa;
}