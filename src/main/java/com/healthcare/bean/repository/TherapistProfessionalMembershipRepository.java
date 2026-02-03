package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistProfessionalMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for therapist professional memberships.
 */
@Repository
public interface TherapistProfessionalMembershipRepository
        extends JpaRepository<TherapistProfessionalMembership, Long> {

    List<TherapistProfessionalMembership> findByTherapist_Id(UUID therapistId);

    int deleteByIdAndTherapist_Id(Long id, UUID therapistId);
}
