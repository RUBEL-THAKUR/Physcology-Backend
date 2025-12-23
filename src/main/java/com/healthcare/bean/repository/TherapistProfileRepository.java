package com.healthcare.bean.repository;

import com.healthcare.bean.model.TherapistProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TherapistProfileRepository
        extends JpaRepository<TherapistProfile, Long> {
}
