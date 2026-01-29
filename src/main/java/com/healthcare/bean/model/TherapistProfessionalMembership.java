package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "therapist_professional_memberships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistProfessionalMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professional_body_name", nullable = false)
    private String professionalBodyName;

    @Column(name = "membership_name", nullable = false)
    private String membershipName;

    @Column(name = "membership_number", nullable = false)
    private String membershipNumber;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "validity_period")
    private String validityPeriod;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
}