package com.healthcare.bean.dto;

import lombok.Data;

@Data
public class TherapistProfessionalMembershipResponse {

    private Long id;
    private String professionalBodyName;
    private String membershipName;
    private String membershipNumber;
    private Integer year;
    private String validityPeriod;
}
