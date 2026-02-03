package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistAreaOfExpertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for therapist area of expertise.
 */
@Repository
public interface TherapistAreaOfExpertiseRepository
        extends JpaRepository<TherapistAreaOfExpertise, Long> {

    List<TherapistAreaOfExpertise> findByTherapist_Id(UUID therapistId);

    void deleteByTherapist_Id(UUID therapistId);
}
