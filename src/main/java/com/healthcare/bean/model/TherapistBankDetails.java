package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "therapist_bank_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "pan_number", nullable = false)
    private String panNumber;

    @Column(name = "aadhaar_number", nullable = false)
    private String aadhaarNumber;

    @Column(name = "bank_document")
    private String bankDocument;

    @Column(name = "pan_document")
    private String panDocument;

    @Column(name = "aadhaar_document")
    private String aadhaarDocument;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false, unique = true)
    private Therapist therapist;
}