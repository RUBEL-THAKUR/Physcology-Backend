package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistProfessionalMembershipDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistProfessionalMembership;
import com.healthcare.bean.repository.TherapistProfessionalMembershipRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistProfessionalMembershipService {

    private final TherapistProfessionalMembershipRepository membershipRepository;
    // ✅ TherapistProfileRepository hata ke TherapistRepository
    private final TherapistRepository therapistRepository;

    /**
     * Add new professional membership
     */
    public TherapistProfessionalMembership addMembership(TherapistProfessionalMembershipDTO dto) {

        // ✅ Find Therapist (UUID)
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        // Create membership entity
        TherapistProfessionalMembership membership = new TherapistProfessionalMembership();
        membership.setProfessionalBodyName(dto.getProfessionalBodyName());
        membership.setMembershipName(dto.getMembershipName());
        membership.setMembershipNumber(dto.getMembershipNumber());
        membership.setYear(dto.getYear());
        membership.setValidityPeriod(dto.getValidityPeriod());
        membership.setTherapist(therapist); // ✅ Therapist set kiya

        return membershipRepository.save(membership);
    }

    /**
     * Get all memberships for a therapist
     */
    public List<TherapistProfessionalMembership> getAllMemberships(UUID therapistId) {
        // ✅ UUID parameter
        return membershipRepository.findByTherapistId(therapistId);
    }

    /**
     * Delete a membership
     */
    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    /**
     * Update a membership
     */
    public TherapistProfessionalMembership updateMembership(
            Long membershipId,
            TherapistProfessionalMembershipDTO dto
    ) {
        TherapistProfessionalMembership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException(
                        "Membership not found with ID: " + membershipId
                ));

        membership.setProfessionalBodyName(dto.getProfessionalBodyName());
        membership.setMembershipName(dto.getMembershipName());
        membership.setMembershipNumber(dto.getMembershipNumber());
        membership.setYear(dto.getYear());
        membership.setValidityPeriod(dto.getValidityPeriod());

        return membershipRepository.save(membership);
    }
}