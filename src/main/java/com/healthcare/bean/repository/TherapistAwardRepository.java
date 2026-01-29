package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TherapistAwardRepository extends JpaRepository<TherapistAward, Long> {


    List<TherapistAward> findByTherapistId(UUID therapistId);
}