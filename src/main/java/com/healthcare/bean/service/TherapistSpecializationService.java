package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistSpecializationDTO;
import com.healthcare.bean.dto.TherapistSpecializationResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistSpecialization;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.repository.TherapistSpecializationRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing therapist specializations.
 * Therapist ownership is resolved securely using JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistSpecializationService {

    private final TherapistSpecializationRepository specializationRepository;
    private final TherapistRepository therapistRepository;
    private final JwtUtil jwtUtil;

    /**
     * Add or update specializations for logged-in therapist.
     * Existing specializations are removed before inserting new ones.
     */
    @Transactional
    public List<TherapistSpecializationResponse> addOrUpdateSpecializations(
            String token,
            TherapistSpecializationDTO dto
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        // Remove existing specializations
        specializationRepository.deleteByTherapist_Id(therapistId);

        // Save new specializations
        List<TherapistSpecialization> saved =
                dto.getSpecializations()
                        .stream()
                        .map(spec -> {
                            TherapistSpecialization s =
                                    new TherapistSpecialization();
                            s.setSpecializationName(spec.toUpperCase());
                            s.setTherapist(therapist);
                            return s;
                        })
                        .toList();

        return specializationRepository.saveAll(saved)
                .stream()
                .map(s -> {
                    TherapistSpecializationResponse r =
                            new TherapistSpecializationResponse();
                    r.setId(s.getId());
                    r.setSpecializationName(s.getSpecializationName());
                    return r;
                })
                .toList();
    }

    /**
     * Get all specializations of logged-in therapist.
     */
    public List<TherapistSpecializationResponse> getAllSpecializations(
            String token
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        return specializationRepository.findByTherapist_Id(therapistId)
                .stream()
                .map(s -> {
                    TherapistSpecializationResponse r =
                            new TherapistSpecializationResponse();
                    r.setId(s.getId());
                    r.setSpecializationName(s.getSpecializationName());
                    return r;
                })
                .toList();
    }

    /**
     * Delete all specializations of logged-in therapist.
     */
    @Transactional
    public void deleteAllSpecializations(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);
        specializationRepository.deleteByTherapist_Id(therapistId);
    }
}
