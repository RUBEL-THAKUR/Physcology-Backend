package com.healthcare.bean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.healthcare.bean.dto.TherapistQualificationDTO;
import com.healthcare.bean.dto.TherapistQualificationResponse;
import com.healthcare.bean.model.TherapistQualification;
import com.healthcare.bean.service.TherapistQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for therapist qualification onboarding.
 */
@RestController
@RequestMapping("/api/therapist/qualification")
@RequiredArgsConstructor
public class TherapistQualificationController {

    private final TherapistQualificationService qualificationService;

    /**
     * Add qualification for logged-in therapist.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TherapistQualification> addQualification(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("data") String dataJson,
            @RequestPart(value = "document", required = false) MultipartFile document
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        TherapistQualificationDTO dto =
                mapper.readValue(dataJson, TherapistQualificationDTO.class);

        String token = authHeader.substring(7);

        return ResponseEntity.ok(
                qualificationService.addQualification(token, dto, document)
        );
    }

    /**
     * Get all qualifications of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<List<TherapistQualificationResponse>> getAllQualifications(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                qualificationService.getAllQualifications(token)
        );
    }

    /**
     * Delete qualification owned by logged-in therapist.
     */
    @DeleteMapping("/{qualificationId}")
    public ResponseEntity<String> deleteQualification(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long qualificationId
    ) {
        String token = authHeader.substring(7);
        qualificationService.deleteQualification(token, qualificationId);
        return ResponseEntity.ok("Qualification deleted successfully");
    }
}
