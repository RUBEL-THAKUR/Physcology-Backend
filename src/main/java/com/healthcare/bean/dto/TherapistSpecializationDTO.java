package com.healthcare.bean.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TherapistSpecializationDTO {


    private UUID therapistId;

    private List<String> specializations; // Multiple specializations at once
}