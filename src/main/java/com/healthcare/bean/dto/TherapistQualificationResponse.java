package com.healthcare.bean.dto;

import lombok.Data;

@Data
public class TherapistQualificationResponse {

    private Long id;
    private String degreeType;
    private String degreeIn;
    private String instituteUniversity;
    private Integer yearOfPassing;
    private String percentageCgpa;
    private String qualificationDocument;
}
