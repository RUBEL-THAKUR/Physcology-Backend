package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistAwardDTO;
import com.healthcare.bean.dto.TherapistAwardResponse;
import com.healthcare.bean.model.TherapistAward;
import com.healthcare.bean.service.TherapistAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for therapist awards.
 */
@RestController
@RequestMapping("/api/therapist/awards")
@RequiredArgsConstructor
public class TherapistAwardController {

    private final TherapistAwardService awardService;

    /**
     * Add a new award for logged-in therapist.
     */
    @PostMapping
    public ResponseEntity<TherapistAward> addAward(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TherapistAwardDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                awardService.addAward(token, dto)
        );
    }

    /**
     * Get all awards of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<List<TherapistAwardResponse>> getAllAwards(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                awardService.getAllAwards(token)
        );
    }

    /**
     * Update an award owned by logged-in therapist.
     */
    @PutMapping("/{awardId}")
    public ResponseEntity<?> updateAward(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long awardId,
            @RequestBody TherapistAwardDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                awardService.updateAward(token, awardId, dto)
        );
    }

    /**
     * Delete an award owned by logged-in therapist.
     */
    @DeleteMapping("/{awardId}")
    public ResponseEntity<String> deleteAward(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long awardId
    ) {
        String token = authHeader.substring(7);
        awardService.deleteAward(token, awardId);
        return ResponseEntity.ok("Award deleted successfully");
    }
}
