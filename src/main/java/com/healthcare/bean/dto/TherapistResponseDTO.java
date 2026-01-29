package com.healthcare.bean.dto;

import com.healthcare.bean.model.TherapistStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapistResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;
    private String cvFileName;
    private boolean isAbove18;
    private boolean acceptedTerms;
    private TherapistStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String rejectionReason;
    private LocalDateTime approvedAt;
    private String approvedBy;
}