package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for therapist specializations.
 */
@Repository
public interface TherapistSpecializationRepository
        extends JpaRepository<TherapistSpecialization, Long> {

    List<TherapistSpecialization> findByTherapist_Id(UUID therapistId);

    void deleteByTherapist_Id(UUID therapistId);
}
