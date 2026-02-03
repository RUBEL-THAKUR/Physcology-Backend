package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TherapistExperienceRepository
        extends JpaRepository<TherapistExperience, Long> {

    List<TherapistExperience> findByTherapist_Id(UUID therapistId);

    int deleteByIdAndTherapist_Id(Long id, UUID therapistId);
}
