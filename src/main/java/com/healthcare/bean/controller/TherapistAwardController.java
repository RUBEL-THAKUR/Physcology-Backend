package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistAwardDTO;
import com.healthcare.bean.model.TherapistAward;
import com.healthcare.bean.service.TherapistAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/awards")
@RequiredArgsConstructor
public class TherapistAwardController {

    private final TherapistAwardService awardService;

    /**
     * POST: Add new award
     * Endpoint: /api/therapist/awards
     */
    @PostMapping
    public ResponseEntity<?> addAward(@RequestBody TherapistAwardDTO dto) {
        try {
            System.out.println("🏆 AWARD CONTROLLER HIT");
            System.out.println("DTO: " + dto);

            TherapistAward saved = awardService.addAward(dto);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET: Get all awards for a therapist
     * Endpoint: /api/therapist/awards/{therapistId}
     */
    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistAward>> getAllAwards(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        return ResponseEntity.ok(awardService.getAllAwards(therapistId));
    }

    /**
     * PUT: Update an award
     * Endpoint: /api/therapist/awards/{awardId}
     */
    @PutMapping("/{awardId}")
    public ResponseEntity<?> updateAward(
            @PathVariable Long awardId,
            @RequestBody TherapistAwardDTO dto
    ) {
        try {
            TherapistAward updated = awardService.updateAward(awardId, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * DELETE: Delete an award
     * Endpoint: /api/therapist/awards/{awardId}
     */
    @DeleteMapping("/{awardId}")
    public ResponseEntity<String> deleteAward(@PathVariable Long awardId) {
        awardService.deleteAward(awardId);
        return ResponseEntity.ok("Award deleted successfully");
    }
}