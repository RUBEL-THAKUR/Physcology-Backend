package com.healthcare.bean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.bean.dto.TherapistBankDetailsDTO;
import com.healthcare.bean.dto.TherapistBankDetailsResponse;
import com.healthcare.bean.model.TherapistBankDetails;
import com.healthcare.bean.service.TherapistBankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for therapist bank details.
 */
@RestController
@RequestMapping("/api/therapist/bank-details")
@RequiredArgsConstructor
public class TherapistBankDetailsController {

    private final TherapistBankDetailsService bankDetailsService;

    /**
     * Add or update bank details for logged-in therapist.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TherapistBankDetails> addOrUpdateBankDetails(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("data") String dataJson,
            @RequestPart(value = "bankDocument", required = false) MultipartFile bankDocument,
            @RequestPart(value = "panDocument", required = false) MultipartFile panDocument,
            @RequestPart(value = "aadhaarDocument", required = false) MultipartFile aadhaarDocument
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TherapistBankDetailsDTO dto =
                mapper.readValue(dataJson, TherapistBankDetailsDTO.class);

        String token = authHeader.substring(7);

        return ResponseEntity.ok(
                bankDetailsService.addOrUpdateBankDetails(
                        token, dto,
                        bankDocument, panDocument, aadhaarDocument)
        );
    }

    /**
     * Get bank details of logged-in therapist.
     */
    @GetMapping
    public ResponseEntity<TherapistBankDetailsResponse> getBankDetails(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(
                bankDetailsService.getBankDetails(token)
        );
    }

    /**
     * Delete bank details of logged-in therapist.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteBankDetails(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        bankDetailsService.deleteBankDetails(token);
        return ResponseEntity.ok(
                "Bank details deleted successfully");
    }
}
