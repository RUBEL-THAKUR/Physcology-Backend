package com.healthcare.bean.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "therapist_qualifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "degree_type")
    private String degreeType;

    @Column(name = "degree_in")
    private String degreeIn;

    @Column(name = "institute_university")
    private String instituteUniversity;

    @Column(name = "year_of_passing")
    private Integer yearOfPassing;

    @Column(name = "percentage_cgpa")
    private String percentageCgpa;

    @Column(name = "qualification_document")
    private String qualificationDocument;

    // ✅ CHANGE - Ab Therapist se map hoga (UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
}