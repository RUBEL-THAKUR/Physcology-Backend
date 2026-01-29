package com.healthcare.bean.repository;

import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, UUID> {
    boolean existsByEmailId(String emailId);
    boolean existsByMobileNumber(String mobileNumber);
    Optional<Therapist> findByEmailId(String emailId);
    List<Therapist> findByStatus(TherapistStatus status);
}