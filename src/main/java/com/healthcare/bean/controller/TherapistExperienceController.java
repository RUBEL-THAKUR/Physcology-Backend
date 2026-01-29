package com.healthcare.bean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.healthcare.bean.dto.TherapistExperienceDTO;
import com.healthcare.bean.model.TherapistExperience;
import com.healthcare.bean.service.TherapistExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/experience")
@RequiredArgsConstructor
public class TherapistExperienceController {

    private final TherapistExperienceService experienceService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addExperience(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "certificate", required = false) MultipartFile certificate
    ) {
        try {
            System.out.println("🔥 CONTROLLER HIT");
            System.out.println("JSON received: " + dataJson);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            TherapistExperienceDTO dto = mapper.readValue(dataJson, TherapistExperienceDTO.class);

            System.out.println("DTO: " + dto);
            System.out.println("File: " + (certificate != null ? certificate.getOriginalFilename() : "No file"));

            TherapistExperience saved = experienceService.addExperience(dto, certificate);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistExperience>> getAll(@PathVariable UUID therapistId) {
        List<TherapistExperience> experiences = experienceService.getAllExperience(therapistId);
        return ResponseEntity.ok(experiences);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<String> delete(@PathVariable Long experienceId) {
        experienceService.deleteExperience(experienceId);
        return ResponseEntity.ok("Experience deleted successfully");
    }
}