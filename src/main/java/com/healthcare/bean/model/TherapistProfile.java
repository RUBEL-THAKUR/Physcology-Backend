package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "therapist_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "therapist_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true
    )
    private Therapist therapist;

    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String category;
    private String emailId;
    private String userName;
    private String mobile;
    private LocalDate dateOfBirth;
    private String gender;
    private String language;

    @Column(length = 1000)
    private String briefDescription;

    private String presentAddress;
    private String presentCountry;
    private String presentState;
    private String presentCity;
    private String presentDistrict;
    private String presentPinCode;

    private String clinicAddress;
    private String clinicCountry;
    private String clinicState;
    private String clinicCity;
    private String clinicDistrict;
    private String clinicPinCode;

    private String timeZone;
    private Integer experience;
}