package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_no")
    private int serialNo;

    @Column(name = "designation")
    private String designation;

    @Column(name = "starting_date")
    private LocalDate startingDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @Column(name = "certification")
    private String certification;

    @Column(name = "action")
    private String action;




    @ManyToOne
    @JoinColumn(name = "psychologist_id", nullable = false)
    @JsonBackReference
    private Psychologist psychologist;

}
