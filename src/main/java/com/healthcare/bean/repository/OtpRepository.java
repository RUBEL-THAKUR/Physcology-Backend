package com.healthcare.bean.repository;

import com.healthcare.bean.model.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findTopByIdentifierAndIsUsedFalseOrderByExpiryTimeDesc(String identifier);


}
