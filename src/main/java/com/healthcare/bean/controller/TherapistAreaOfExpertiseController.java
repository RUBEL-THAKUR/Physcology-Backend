package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistAreaOfExpertiseDTO;
import com.healthcare.bean.model.TherapistAreaOfExpertise;
import com.healthcare.bean.service.TherapistAreaOfExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/expertise")
@RequiredArgsConstructor
public class TherapistAreaOfExpertiseController {

    private final TherapistAreaOfExpertiseService expertiseService;

    /**
     * POST: Add or Update expertise areas
     * Endpoint: /api/therapist/expertise
     * Body: { "therapistId": "550e8400-e29b-41d4-a716-446655440000", "expertiseAreas": ["ANXIETY", "DEPRESSION"] }
     */
    @PostMapping
    public ResponseEntity<?> addOrUpdateExpertiseAreas(
            @RequestBody TherapistAreaOfExpertiseDTO dto
    ) {
        try {
            System.out.println("🔥 EXPERTISE CONTROLLER HIT");
            System.out.println("Therapist ID: " + dto.getTherapistId());
            System.out.println("Selected Areas: " + dto.getExpertiseAreas());

            List<TherapistAreaOfExpertise> saved =
                    expertiseService.addOrUpdateExpertiseAreas(dto);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET: Get all expertise areas for a therapist
     * Endpoint: /api/therapist/expertise/{therapistId}
     */
    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistAreaOfExpertise>> getAllExpertiseAreas(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        List<TherapistAreaOfExpertise> areas =
                expertiseService.getAllExpertiseAreas(therapistId);
        return ResponseEntity.ok(areas);
    }

    /**
     * DELETE: Delete all expertise areas for a therapist
     * Endpoint: /api/therapist/expertise/{therapistId}
     */
    @DeleteMapping("/{therapistId}")
    public ResponseEntity<String> deleteAllExpertiseAreas(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        expertiseService.deleteAllExpertiseAreas(therapistId);
        return ResponseEntity.ok("All expertise areas deleted successfully");
    }
}