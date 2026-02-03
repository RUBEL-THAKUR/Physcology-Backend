package com.healthcare.bean.dto;

import lombok.Data;

@Data
public class TherapistBankDetailsResponse {

    private Long id;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String panNumber;
    private String aadhaarNumber;
    private String bankDocument;
    private String panDocument;
    private String aadhaarDocument;
}
