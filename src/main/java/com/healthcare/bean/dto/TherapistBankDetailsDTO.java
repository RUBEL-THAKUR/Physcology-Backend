package com.healthcare.bean.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TherapistBankDetailsDTO {


    private UUID therapistId;

    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String panNumber;
    private String aadhaarNumber;
}