package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistQualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for therapist qualification entity.
 */
public interface TherapistQualificationRepository
        extends JpaRepository<TherapistQualification, Long> {

    List<TherapistQualification> findByTherapist_Id(UUID therapistId);

    int deleteByIdAndTherapist_Id(Long id, UUID therapistId);
}
