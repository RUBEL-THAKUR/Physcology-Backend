package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistAwardDTO;
import com.healthcare.bean.dto.TherapistAwardResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistAward;
import com.healthcare.bean.repository.TherapistAwardRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing therapist awards.
 * Therapist ownership is resolved securely using JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistAwardService {

    private final TherapistAwardRepository awardRepository;
    private final TherapistRepository therapistRepository;
    private final JwtUtil jwtUtil;

    /**
     * Add a new award for logged-in therapist.
     */
    @Transactional
    public TherapistAward addAward(String token, TherapistAwardDTO dto) {

        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        TherapistAward award = new TherapistAward();
        award.setAwardTitle(dto.getAwardTitle());
        award.setAwardingYear(dto.getAwardingYear());
        award.setAwardingBody(dto.getAwardingBody());
        award.setTherapist(therapist);

        return awardRepository.save(award);
    }

    /**
     * Get all awards of logged-in therapist.
     * Uses response DTO to avoid Hibernate proxy serialization issues.
     */
    public List<TherapistAwardResponse> getAllAwards(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        return awardRepository.findByTherapist_Id(therapistId)
                .stream()
                .map(a -> {
                    TherapistAwardResponse r = new TherapistAwardResponse();
                    r.setId(a.getId());
                    r.setAwardTitle(a.getAwardTitle());
                    r.setAwardingYear(a.getAwardingYear());
                    r.setAwardingBody(a.getAwardingBody());
                    return r;
                })
                .toList();
    }

    /**
     * Update an award owned by logged-in therapist.
     */
    @Transactional
    public TherapistAward updateAward(
            String token,
            Long awardId,
            TherapistAwardDTO dto
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        TherapistAward award = awardRepository.findById(awardId)
                .filter(a -> a.getTherapist().getId().equals(therapistId))
                .orElseThrow(() ->
                        new RuntimeException("Award not found or access denied"));

        award.setAwardTitle(dto.getAwardTitle());
        award.setAwardingYear(dto.getAwardingYear());
        award.setAwardingBody(dto.getAwardingBody());

        return awardRepository.save(award);
    }

    /**
     * Delete an award owned by logged-in therapist.
     */
    @Transactional
    public void deleteAward(String token, Long awardId) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        int deleted =
                awardRepository.deleteByIdAndTherapist_Id(
                        awardId, therapistId);

        if (deleted == 0) {
            throw new RuntimeException(
                    "Award not found or access denied");
        }
    }
}
