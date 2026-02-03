package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistAreaOfExpertiseDTO;
import com.healthcare.bean.dto.TherapistAreaOfExpertiseResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistAreaOfExpertise;
import com.healthcare.bean.repository.TherapistAreaOfExpertiseRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing therapist areas of expertise.
 * Therapist ownership is resolved securely using JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistAreaOfExpertiseService {

    private final TherapistAreaOfExpertiseRepository expertiseRepository;
    private final TherapistRepository therapistRepository;
    private final JwtUtil jwtUtil;

    /**
     * Add or update expertise areas for logged-in therapist.
     * Existing expertise is removed before inserting new ones.
     */
    @Transactional
    public List<TherapistAreaOfExpertiseResponse> addOrUpdateExpertiseAreas(
            String token,
            TherapistAreaOfExpertiseDTO dto
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        // Remove existing expertise areas
        expertiseRepository.deleteByTherapist_Id(therapistId);

        // Save new expertise areas
        List<TherapistAreaOfExpertise> savedList =
                dto.getExpertiseAreas()
                        .stream()
                        .map(area -> {
                            TherapistAreaOfExpertise expertise =
                                    new TherapistAreaOfExpertise();
                            expertise.setExpertiseArea(area.toUpperCase());
                            expertise.setTherapist(therapist);
                            return expertise;
                        })
                        .toList();

        return expertiseRepository.saveAll(savedList)
                .stream()
                .map(e -> {
                    TherapistAreaOfExpertiseResponse r =
                            new TherapistAreaOfExpertiseResponse();
                    r.setId(e.getId());
                    r.setExpertiseArea(e.getExpertiseArea());
                    return r;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all expertise areas of logged-in therapist.
     */
    public List<TherapistAreaOfExpertiseResponse> getAllExpertiseAreas(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        return expertiseRepository.findByTherapist_Id(therapistId)
                .stream()
                .map(e -> {
                    TherapistAreaOfExpertiseResponse r =
                            new TherapistAreaOfExpertiseResponse();
                    r.setId(e.getId());
                    r.setExpertiseArea(e.getExpertiseArea());
                    return r;
                })
                .toList();
    }

    /**
     * Delete all expertise areas of logged-in therapist.
     */
    @Transactional
    public void deleteAllExpertiseAreas(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);
        expertiseRepository.deleteByTherapist_Id(therapistId);
    }
}
