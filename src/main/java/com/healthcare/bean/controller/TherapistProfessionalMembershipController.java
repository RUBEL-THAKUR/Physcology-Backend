package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistProfessionalMembershipDTO;
import com.healthcare.bean.dto.TherapistProfessionalMembershipResponse;
import com.healthcare.bean.model.TherapistProfessionalMembership;
import com.healthcare.bean.service.TherapistProfessionalMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for therapist professional memberships.
 */
@RestController
@RequestMapping("/api/therapist/memberships")
@RequiredArgsConstructor
public class TherapistProfessionalMembershipController {

    private final TherapistProfessionalMembershipService membershipService;

    /**
     * Add new professional membership for logged-in therapist.
     */
    @PostMapping
    public ResponseEntity<TherapistProfessionalMembership> addMembership(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TherapistProfessionalMembershipDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                membershipService.addMembership(token, dto)
        );
    }

    /**
     * Get all professional memberships of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<List<TherapistProfessionalMembershipResponse>>
    getAllMemberships(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                membershipService.getAllMemberships(token)
        );
    }

    /**
     * Update a professional membership owned by logged-in therapist.
     */
    @PutMapping("/{membershipId}")
    public ResponseEntity<?> updateMembership(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long membershipId,
            @RequestBody TherapistProfessionalMembershipDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                membershipService.updateMembership(
                        token, membershipId, dto)
        );
    }

    /**
     * Delete a professional membership owned by logged-in therapist.
     */
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<String> deleteMembership(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long membershipId
    ) {
        String token = authHeader.substring(7);
        membershipService.deleteMembership(token, membershipId);
        return ResponseEntity.ok(
                "Membership deleted successfully");
    }
}
