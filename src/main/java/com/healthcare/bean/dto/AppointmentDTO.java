package com.healthcare.bean.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String status;
}
