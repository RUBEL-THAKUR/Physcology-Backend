package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistAreaOfExpertiseDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistAreaOfExpertise;
import com.healthcare.bean.repository.TherapistAreaOfExpertiseRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TherapistAreaOfExpertiseService {

    private final TherapistAreaOfExpertiseRepository expertiseRepository;
    // ✅ TherapistProfileRepository hata ke TherapistRepository
    private final TherapistRepository therapistRepository;

    /**
     * Add or Update expertise areas for a therapist
     * This method deletes old areas and adds new ones to avoid duplicates
     */
    @Transactional
    public List<TherapistAreaOfExpertise> addOrUpdateExpertiseAreas(
            TherapistAreaOfExpertiseDTO dto
    ) {
        // ✅ Step 1: Find Therapist (UUID)
        Therapist therapist = therapistRepository
                .findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        // ✅ Step 2: Delete existing expertise areas for this therapist
        expertiseRepository.deleteByTherapistId(dto.getTherapistId());

        // ✅ Step 3: Create new expertise area objects
        List<TherapistAreaOfExpertise> expertiseList = dto.getExpertiseAreas()
                .stream()
                .map(area -> {
                    TherapistAreaOfExpertise expertise = new TherapistAreaOfExpertise();
                    expertise.setExpertiseArea(area.toUpperCase());
                    expertise.setTherapist(therapist); // ✅ Therapist set kiya
                    return expertise;
                })
                .collect(Collectors.toList());

        // Step 4: Save all areas at once
        return expertiseRepository.saveAll(expertiseList);
    }

    /**
     * Get all expertise areas for a therapist
     */
    public List<TherapistAreaOfExpertise> getAllExpertiseAreas(UUID therapistId) {
        // ✅ UUID parameter
        return expertiseRepository.findByTherapistId(therapistId);
    }

    /**
     * Delete all expertise areas for a therapist
     */
    @Transactional
    public void deleteAllExpertiseAreas(UUID therapistId) {
        // ✅ UUID parameter
        expertiseRepository.deleteByTherapistId(therapistId);
    }
}