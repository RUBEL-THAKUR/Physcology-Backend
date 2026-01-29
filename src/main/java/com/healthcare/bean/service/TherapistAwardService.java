package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistAwardDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistAward;
import com.healthcare.bean.repository.TherapistAwardRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistAwardService {

    private final TherapistAwardRepository awardRepository;
    // ✅ TherapistProfileRepository hata ke TherapistRepository
    private final TherapistRepository therapistRepository;

    /**
     * Add new award
     */
    public TherapistAward addAward(TherapistAwardDTO dto) {

        // ✅ Find Therapist (UUID)
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        // Create award entity
        TherapistAward award = new TherapistAward();
        award.setAwardTitle(dto.getAwardTitle());
        award.setAwardingYear(dto.getAwardingYear());
        award.setAwardingBody(dto.getAwardingBody());
        award.setTherapist(therapist); // ✅ Therapist set kiya

        return awardRepository.save(award);
    }

    /**
     * Get all awards for a therapist
     */
    public List<TherapistAward> getAllAwards(UUID therapistId) {
        // ✅ UUID parameter
        return awardRepository.findByTherapistId(therapistId);
    }

    /**
     * Delete an award
     */
    public void deleteAward(Long awardId) {
        awardRepository.deleteById(awardId);
    }

    /**
     * Update an award
     */
    public TherapistAward updateAward(Long awardId, TherapistAwardDTO dto) {
        TherapistAward award = awardRepository.findById(awardId)
                .orElseThrow(() -> new RuntimeException("Award not found with ID: " + awardId));

        award.setAwardTitle(dto.getAwardTitle());
        award.setAwardingYear(dto.getAwardingYear());
        award.setAwardingBody(dto.getAwardingBody());

        return awardRepository.save(award);
    }
}