package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistQualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TherapistQualificationRepository extends JpaRepository<TherapistQualification, Long> {


    List<TherapistQualification> findByTherapistId(UUID therapistId);
}