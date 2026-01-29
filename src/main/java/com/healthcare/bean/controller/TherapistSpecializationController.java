package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistSpecializationDTO;
import com.healthcare.bean.model.TherapistSpecialization;
import com.healthcare.bean.service.TherapistSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/specializations")
@RequiredArgsConstructor
public class TherapistSpecializationController {

    private final TherapistSpecializationService specializationService;

    /**
     * POST: Add or Update specializations
     * Endpoint: /api/therapist/specializations
     */
    @PostMapping
    public ResponseEntity<?> addOrUpdateSpecializations(
            @RequestBody TherapistSpecializationDTO dto
    ) {
        try {
            System.out.println("🎓 SPECIALIZATION CONTROLLER HIT");
            System.out.println("DTO: " + dto);

            List<TherapistSpecialization> saved =
                    specializationService.addOrUpdateSpecializations(dto);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET: Get all specializations for a therapist
     * Endpoint: /api/therapist/specializations/{therapistId}
     */
    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistSpecialization>> getAllSpecializations(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        return ResponseEntity.ok(specializationService.getAllSpecializations(therapistId));
    }

    /**
     * DELETE: Delete all specializations for a therapist
     * Endpoint: /api/therapist/specializations/{therapistId}
     */
    @DeleteMapping("/{therapistId}")
    public ResponseEntity<String> deleteAllSpecializations(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        specializationService.deleteAllSpecializations(therapistId);
        return ResponseEntity.ok("All specializations deleted successfully");
    }
}