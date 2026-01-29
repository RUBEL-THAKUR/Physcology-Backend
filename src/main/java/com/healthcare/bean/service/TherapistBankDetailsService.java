package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistBankDetailsDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistBankDetails;
import com.healthcare.bean.repository.TherapistBankDetailsRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistBankDetailsService {

    private final TherapistBankDetailsRepository bankDetailsRepository;

    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;

    /**
     * Add or Update bank details
     */
    public TherapistBankDetails addOrUpdateBankDetails(
            TherapistBankDetailsDTO dto,
            MultipartFile bankDocument,
            MultipartFile panDocument,
            MultipartFile aadhaarDocument
    ) {
        // ✅ Find Therapist (UUID)
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        // ✅ Check if bank details already exist
        Optional<TherapistBankDetails> existingDetails =
                bankDetailsRepository.findByTherapistId(dto.getTherapistId());

        TherapistBankDetails bankDetails;

        if (existingDetails.isPresent()) {
            // Update existing
            bankDetails = existingDetails.get();
        } else {
            // Create new
            bankDetails = new TherapistBankDetails();
            bankDetails.setTherapist(therapist); // ✅ Therapist set kiya
        }

        // Set basic details
        bankDetails.setBankName(dto.getBankName());
        bankDetails.setAccountNumber(dto.getAccountNumber());
        bankDetails.setIfscCode(dto.getIfscCode());
        bankDetails.setPanNumber(dto.getPanNumber());
        bankDetails.setAadhaarNumber(dto.getAadhaarNumber());

        // Handle file uploads
        if (bankDocument != null && !bankDocument.isEmpty()) {
            String filePath = fileStorageService.storeFile(bankDocument);
            bankDetails.setBankDocument(filePath);
        }

        if (panDocument != null && !panDocument.isEmpty()) {
            String filePath = fileStorageService.storeFile(panDocument);
            bankDetails.setPanDocument(filePath);
        }

        if (aadhaarDocument != null && !aadhaarDocument.isEmpty()) {
            String filePath = fileStorageService.storeFile(aadhaarDocument);
            bankDetails.setAadhaarDocument(filePath);
        }

        return bankDetailsRepository.save(bankDetails);
    }

    /**
     * Get bank details by therapist ID
     */
    public Optional<TherapistBankDetails> getBankDetails(UUID therapistId) {
        // ✅ UUID parameter
        return bankDetailsRepository.findByTherapistId(therapistId);
    }

    /**
     * Delete bank details
     */
    public void deleteBankDetails(UUID therapistId) {
        // ✅ UUID parameter
        TherapistBankDetails details = bankDetailsRepository.findByTherapistId(therapistId)
                .orElseThrow(() -> new RuntimeException(
                        "Bank details not found for therapist ID: " + therapistId
                ));
        bankDetailsRepository.delete(details);
    }
}