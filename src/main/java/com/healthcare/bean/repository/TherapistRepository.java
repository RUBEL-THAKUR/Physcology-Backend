// Location: src/main/java/com/healthcare/bean/repository/TherapistRepository.java

package com.healthcare.bean.repository;

import com.healthcare.bean.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, UUID> {
    boolean existsByEmailId(String emailId);
    boolean existsByMobileNumber(String mobileNumber);
}