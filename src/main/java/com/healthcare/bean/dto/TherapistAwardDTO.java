package com.healthcare.bean.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TherapistAwardDTO {


    private UUID therapistId;

    private String awardTitle;
    private Integer awardingYear;
    private String awardingBody;
}