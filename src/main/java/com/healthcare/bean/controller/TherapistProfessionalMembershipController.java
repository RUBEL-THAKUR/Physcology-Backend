package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistProfessionalMembershipDTO;
import com.healthcare.bean.model.TherapistProfessionalMembership;
import com.healthcare.bean.service.TherapistProfessionalMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/memberships")
@RequiredArgsConstructor
public class TherapistProfessionalMembershipController {

    private final TherapistProfessionalMembershipService membershipService;

    /**
     * POST: Add new professional membership
     * Endpoint: /api/therapist/memberships
     */
    @PostMapping
    public ResponseEntity<?> addMembership(
            @RequestBody TherapistProfessionalMembershipDTO dto
    ) {
        try {
            System.out.println("🎓 MEMBERSHIP CONTROLLER HIT");
            System.out.println("DTO: " + dto);

            TherapistProfessionalMembership saved = membershipService.addMembership(dto);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET: Get all memberships for a therapist
     * Endpoint: /api/therapist/memberships/{therapistId}
     */
    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistProfessionalMembership>> getAllMemberships(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        return ResponseEntity.ok(membershipService.getAllMemberships(therapistId));
    }

    /**
     * PUT: Update a membership
     * Endpoint: /api/therapist/memberships/{membershipId}
     */
    @PutMapping("/{membershipId}")
    public ResponseEntity<?> updateMembership(
            @PathVariable Long membershipId,
            @RequestBody TherapistProfessionalMembershipDTO dto
    ) {
        try {
            TherapistProfessionalMembership updated =
                    membershipService.updateMembership(membershipId, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * DELETE: Delete a membership
     * Endpoint: /api/therapist/memberships/{membershipId}
     */
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<String> deleteMembership(@PathVariable Long membershipId) {
        membershipService.deleteMembership(membershipId);
        return ResponseEntity.ok("Membership deleted successfully");
    }
}