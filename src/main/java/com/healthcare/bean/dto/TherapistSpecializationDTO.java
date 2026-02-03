package com.healthcare.bean.dto;

import lombok.Data;

import java.util.List;

@Data
public class TherapistSpecializationDTO {

    // Multiple specializations at once
    private List<String> specializations;
}
