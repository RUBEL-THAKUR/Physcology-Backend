package com.healthcare.bean.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistProfileBasicInfoRequest {

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
    private String briefDescription;

    private String presentAddress;
    private String presentCountry;
    private String presentState;
    private String presentCity;
    private String presentDistrict;
    private String presentPinCode;

    // 🔥 EXACT NAME (NO "is" PREFIX)
    private boolean sameAsPresentAddress;

    private String clinicAddress;
    private String clinicCountry;
    private String clinicState;
    private String clinicCity;
    private String clinicDistrict;
    private String clinicPinCode;

    private String timeZone;
    private Integer experience;
}
