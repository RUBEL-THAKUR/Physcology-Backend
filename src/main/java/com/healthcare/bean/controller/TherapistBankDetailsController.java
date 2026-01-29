package com.healthcare.bean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.bean.dto.TherapistBankDetailsDTO;
import com.healthcare.bean.model.TherapistBankDetails;
import com.healthcare.bean.service.TherapistBankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist/bank-details")
@RequiredArgsConstructor
public class TherapistBankDetailsController {

    private final TherapistBankDetailsService bankDetailsService;

    /**
     * POST: Add or Update bank details
     * Endpoint: /api/therapist/bank-details
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addOrUpdateBankDetails(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "bankDocument", required = false) MultipartFile bankDocument,
            @RequestPart(value = "panDocument", required = false) MultipartFile panDocument,
            @RequestPart(value = "aadhaarDocument", required = false) MultipartFile aadhaarDocument
    ) {
        try {
            System.out.println("🏦 BANK DETAILS CONTROLLER HIT");
            System.out.println("JSON received: " + dataJson);

            // Parse JSON to DTO
            ObjectMapper mapper = new ObjectMapper();
            TherapistBankDetailsDTO dto = mapper.readValue(dataJson, TherapistBankDetailsDTO.class);

            System.out.println("DTO: " + dto);
            System.out.println("Bank Document: " + (bankDocument != null ? bankDocument.getOriginalFilename() : "null"));
            System.out.println("PAN Document: " + (panDocument != null ? panDocument.getOriginalFilename() : "null"));
            System.out.println("Aadhaar Document: " + (aadhaarDocument != null ? aadhaarDocument.getOriginalFilename() : "null"));

            TherapistBankDetails saved = bankDetailsService.addOrUpdateBankDetails(
                    dto, bankDocument, panDocument, aadhaarDocument
            );

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET: Get bank details by therapist ID
     * Endpoint: /api/therapist/bank-details/{therapistId}
     */
    @GetMapping("/{therapistId}")
    public ResponseEntity<?> getBankDetails(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        Optional<TherapistBankDetails> details = bankDetailsService.getBankDetails(therapistId);

        if (details.isPresent()) {
            return ResponseEntity.ok(details.get());
        } else {
            return ResponseEntity.ok("No bank details found for this therapist");
        }
    }

    /**
     * DELETE: Delete bank details
     * Endpoint: /api/therapist/bank-details/{therapistId}
     */
    @DeleteMapping("/{therapistId}")
    public ResponseEntity<String> deleteBankDetails(
            @PathVariable UUID therapistId // ✅ UUID parameter
    ) {
        try {
            bankDetailsService.deleteBankDetails(therapistId);
            return ResponseEntity.ok("Bank details deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}