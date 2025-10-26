package com.healthcare.bean.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class ExperienceDto {
    private int serialNo;
    private String designation;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String certification;
    private String action;



}