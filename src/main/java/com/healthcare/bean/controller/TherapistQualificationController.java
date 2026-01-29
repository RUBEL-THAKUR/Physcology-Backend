package com.healthcare.bean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.healthcare.bean.dto.TherapistQualificationDTO;
import com.healthcare.bean.model.TherapistQualification;
import com.healthcare.bean.service.TherapistQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/qualification")
@RequiredArgsConstructor
public class TherapistQualificationController {

    private final TherapistQualificationService qualificationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addQualification(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "document", required = false) MultipartFile document
    ) {
        try {
            System.out.println("🔥 QUALIFICATION CONTROLLER HIT");
            System.out.println("JSON received: " + dataJson);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            TherapistQualificationDTO dto = mapper.readValue(dataJson, TherapistQualificationDTO.class);

            System.out.println("DTO: " + dto);
            System.out.println("Document: " + (document != null ? document.getOriginalFilename() : "No file"));

            TherapistQualification saved = qualificationService.addQualification(dto, document);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ UUID parameter
    @GetMapping("/{therapistId}")
    public ResponseEntity<List<TherapistQualification>> getAllQualifications(
            @PathVariable UUID therapistId
    ) {
        return ResponseEntity.ok(qualificationService.getAllQualifications(therapistId));
    }

    @DeleteMapping("/{qualificationId}")
    public ResponseEntity<String> deleteQualification(@PathVariable Long qualificationId) {
        qualificationService.deleteQualification(qualificationId);
        return ResponseEntity.ok("Qualification deleted successfully");
    }
}