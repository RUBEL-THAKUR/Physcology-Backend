package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistAreaOfExpertiseDTO;
import com.healthcare.bean.dto.TherapistAreaOfExpertiseResponse;
import com.healthcare.bean.service.TherapistAreaOfExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for therapist expertise onboarding.
 */
@RestController
@RequestMapping("/api/therapist/expertise")
@RequiredArgsConstructor
public class TherapistAreaOfExpertiseController {

    private final TherapistAreaOfExpertiseService expertiseService;

    /**
     * Add or update expertise areas for logged-in therapist.
     */
    @PostMapping
    public ResponseEntity<List<TherapistAreaOfExpertiseResponse>> addOrUpdateExpertiseAreas(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TherapistAreaOfExpertiseDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                expertiseService.addOrUpdateExpertiseAreas(token, dto)
        );
    }

    /**
     * Get all expertise areas of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<List<TherapistAreaOfExpertiseResponse>> getAllExpertiseAreas(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                expertiseService.getAllExpertiseAreas(token)
        );
    }

    /**
     * Delete all expertise areas of logged-in therapist.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAllExpertiseAreas(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        expertiseService.deleteAllExpertiseAreas(token);
        return ResponseEntity.ok("All expertise areas deleted successfully");
    }
}
