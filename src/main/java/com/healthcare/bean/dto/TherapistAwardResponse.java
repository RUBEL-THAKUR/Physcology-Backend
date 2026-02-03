package com.healthcare.bean.dto;

import lombok.Data;

@Data
public class TherapistAwardResponse {

    private Long id;
    private String awardTitle;
    private Integer awardingYear;
    private String awardingBody;
}
