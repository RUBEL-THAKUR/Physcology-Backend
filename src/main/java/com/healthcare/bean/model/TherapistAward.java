package com.healthcare.bean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "therapist_awards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapistAward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "award_title", nullable = false)
    private String awardTitle;

    @Column(name = "awarding_year", nullable = false)
    private Integer awardingYear;

    @Column(name = "awarding_body", nullable = false)
    private String awardingBody;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
}