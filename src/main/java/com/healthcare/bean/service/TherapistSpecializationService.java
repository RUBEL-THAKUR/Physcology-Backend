package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistSpecializationDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistSpecialization;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.repository.TherapistSpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TherapistSpecializationService {

    private final TherapistSpecializationRepository specializationRepository;
    // ✅ TherapistProfileRepository hata ke TherapistRepository
    private final TherapistRepository therapistRepository;

    /**
     * Add or Update specializations
     * Deletes old ones and adds new ones to avoid duplicates
     */
    @Transactional
    public List<TherapistSpecialization> addOrUpdateSpecializations(
            TherapistSpecializationDTO dto
    ) {
        // ✅ Find Therapist (UUID)
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        // ✅ Delete existing specializations
        specializationRepository.deleteByTherapistId(dto.getTherapistId());

        // ✅ Create new specializations
        List<TherapistSpecialization> specializationList = dto.getSpecializations()
                .stream()
                .map(spec -> {
                    TherapistSpecialization specialization = new TherapistSpecialization();
                    specialization.setSpecializationName(spec.toUpperCase());
                    specialization.setTherapist(therapist); // ✅ Therapist set kiya
                    return specialization;
                })
                .collect(Collectors.toList());

        return specializationRepository.saveAll(specializationList);
    }

    /**
     * Get all specializations for a therapist
     */
    public List<TherapistSpecialization> getAllSpecializations(UUID therapistId) {
        // ✅ UUID parameter
        return specializationRepository.findByTherapistId(therapistId);
    }

    /**
     * Delete all specializations for a therapist
     */
    @Transactional
    public void deleteAllSpecializations(UUID therapistId) {
        // ✅ UUID parameter
        specializationRepository.deleteByTherapistId(therapistId);
    }
}