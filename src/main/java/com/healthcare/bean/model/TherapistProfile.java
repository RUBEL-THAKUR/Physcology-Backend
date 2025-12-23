package com.healthcare.bean.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
