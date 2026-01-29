package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "therapist_experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    @Column(name = "organization_name")
    private String organizationName;

    private LocalDate employmentFrom;

    private LocalDate employmentTo;

    private Boolean continuing;

    private String experienceCertificate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
}