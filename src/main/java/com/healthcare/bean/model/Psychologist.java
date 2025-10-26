
package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String category;
    private String specialization;
    private String email;
    private String phone;
    private LocalDate dob;

    private String language;
    private String gender;
    private String briefDescription;
    private String currentAddress;
    private String country;
    private String timeZone;

    // On Psychologist.java
    @OneToMany(mappedBy = "psychologist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
//    @JsonIgnoreProperties("psychologist")
    private List<Experience> experienceList;




}
