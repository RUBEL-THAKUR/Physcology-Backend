package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistSpecializationDTO;
import com.healthcare.bean.dto.TherapistSpecializationResponse;
import com.healthcare.bean.service.TherapistSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for therapist specializations.
 */
@RestController
@RequestMapping("/api/therapist/specializations")
@RequiredArgsConstructor
public class TherapistSpecializationController {

    private final TherapistSpecializationService specializationService;

    /**
     * Add or update specializations for logged-in therapist.
     */
    @PostMapping
    public ResponseEntity<List<TherapistSpecializationResponse>>
    addOrUpdateSpecializations(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TherapistSpecializationDTO dto
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                specializationService.addOrUpdateSpecializations(token, dto)
        );
    }

    /**
     * Get all specializations of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<List<TherapistSpecializationResponse>>
    getAllSpecializations(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                specializationService.getAllSpecializations(token)
        );
    }

    /**
     * Delete all specializations of logged-in therapist.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAllSpecializations(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        specializationService.deleteAllSpecializations(token);
        return ResponseEntity.ok(
                "All specializations deleted successfully");
    }
}
