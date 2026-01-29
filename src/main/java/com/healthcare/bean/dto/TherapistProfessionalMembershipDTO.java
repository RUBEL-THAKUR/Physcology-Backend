package com.healthcare.bean.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TherapistProfessionalMembershipDTO {


    private UUID therapistId;

    private String professionalBodyName;
    private String membershipName;
    private String membershipNumber;
    private Integer year;
    private String validityPeriod;
}