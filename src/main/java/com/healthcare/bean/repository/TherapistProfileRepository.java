package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TherapistProfileRepository extends JpaRepository<TherapistProfile, Long> {

    @Query("SELECT tp FROM TherapistProfile tp WHERE tp.therapist.id = :therapistId")
    Optional<TherapistProfile> findByTherapistId(@Param("therapistId") UUID therapistId);

    @Query("DELETE FROM TherapistProfile tp WHERE tp.therapist.id = :therapistId")
    void deleteByTherapistId(@Param("therapistId") UUID therapistId);
}