package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistProfessionalMembershipDTO;
import com.healthcare.bean.dto.TherapistProfessionalMembershipResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistProfessionalMembership;
import com.healthcare.bean.repository.TherapistProfessionalMembershipRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing therapist professional memberships.
 * Therapist ownership is resolved securely using JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistProfessionalMembershipService {

    private final TherapistProfessionalMembershipRepository membershipRepository;
    private final TherapistRepository therapistRepository;
    private final JwtUtil jwtUtil;

    /**
     * Add a new professional membership for logged-in therapist.
     */
    @Transactional
    public TherapistProfessionalMembership addMembership(
            String token,
            TherapistProfessionalMembershipDTO dto
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        TherapistProfessionalMembership membership =
                new TherapistProfessionalMembership();
        membership.setProfessionalBodyName(dto.getProfessionalBodyName());
        membership.setMembershipName(dto.getMembershipName());
        membership.setMembershipNumber(dto.getMembershipNumber());
        membership.setYear(dto.getYear());
        membership.setValidityPeriod(dto.getValidityPeriod());
        membership.setTherapist(therapist);

        return membershipRepository.save(membership);
    }

    /**
     * Get all memberships of logged-in therapist.
     * Uses response DTO to avoid Hibernate proxy serialization issues.
     */
    public List<TherapistProfessionalMembershipResponse> getAllMemberships(
            String token
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        return membershipRepository.findByTherapist_Id(therapistId)
                .stream()
                .map(m -> {
                    TherapistProfessionalMembershipResponse r =
                            new TherapistProfessionalMembershipResponse();
                    r.setId(m.getId());
                    r.setProfessionalBodyName(m.getProfessionalBodyName());
                    r.setMembershipName(m.getMembershipName());
                    r.setMembershipNumber(m.getMembershipNumber());
                    r.setYear(m.getYear());
                    r.setValidityPeriod(m.getValidityPeriod());
                    return r;
                })
                .toList();
    }

    /**
     * Update a membership owned by logged-in therapist.
     */
    @Transactional
    public TherapistProfessionalMembership updateMembership(
            String token,
            Long membershipId,
            TherapistProfessionalMembershipDTO dto
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        TherapistProfessionalMembership membership =
                membershipRepository.findById(membershipId)
                        .filter(m ->
                                m.getTherapist().getId().equals(therapistId))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Membership not found or access denied"));

        membership.setProfessionalBodyName(dto.getProfessionalBodyName());
        membership.setMembershipName(dto.getMembershipName());
        membership.setMembershipNumber(dto.getMembershipNumber());
        membership.setYear(dto.getYear());
        membership.setValidityPeriod(dto.getValidityPeriod());

        return membershipRepository.save(membership);
    }

    /**
     * Delete a membership owned by logged-in therapist.
     */
    @Transactional
    public void deleteMembership(String token, Long membershipId) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        int deleted =
                membershipRepository.deleteByIdAndTherapist_Id(
                        membershipId, therapistId);

        if (deleted == 0) {
            throw new RuntimeException(
                    "Membership not found or access denied");
        }
    }
}
