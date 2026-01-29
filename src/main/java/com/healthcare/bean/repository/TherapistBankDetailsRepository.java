package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TherapistBankDetailsRepository extends JpaRepository<TherapistBankDetails, Long> {


    Optional<TherapistBankDetails> findByTherapistId(UUID therapistId);


    boolean existsByTherapistId(UUID therapistId);
}