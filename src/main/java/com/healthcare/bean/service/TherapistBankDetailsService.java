package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistBankDetailsDTO;
import com.healthcare.bean.dto.TherapistBankDetailsResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistBankDetails;
import com.healthcare.bean.repository.TherapistBankDetailsRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Service for managing therapist bank details.
 * Therapist ownership is resolved securely using JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistBankDetailsService {

    private final TherapistBankDetailsRepository bankDetailsRepository;
    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;
    private final JwtUtil jwtUtil;

    /**
     * Add or update bank details for logged-in therapist.
     */
    @Transactional
    public TherapistBankDetails addOrUpdateBankDetails(
            String token,
            TherapistBankDetailsDTO dto,
            MultipartFile bankDocument,
            MultipartFile panDocument,
            MultipartFile aadhaarDocument
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        TherapistBankDetails bankDetails =
                bankDetailsRepository.findByTherapist_Id(therapistId)
                        .orElseGet(() -> {
                            TherapistBankDetails bd = new TherapistBankDetails();
                            bd.setTherapist(therapist);
                            return bd;
                        });

        bankDetails.setBankName(dto.getBankName());
        bankDetails.setAccountNumber(dto.getAccountNumber());
        bankDetails.setIfscCode(dto.getIfscCode());
        bankDetails.setPanNumber(dto.getPanNumber());
        bankDetails.setAadhaarNumber(dto.getAadhaarNumber());

        if (bankDocument != null && !bankDocument.isEmpty()) {
            bankDetails.setBankDocument(
                    fileStorageService.storeFile(bankDocument));
        }

        if (panDocument != null && !panDocument.isEmpty()) {
            bankDetails.setPanDocument(
                    fileStorageService.storeFile(panDocument));
        }

        if (aadhaarDocument != null && !aadhaarDocument.isEmpty()) {
            bankDetails.setAadhaarDocument(
                    fileStorageService.storeFile(aadhaarDocument));
        }

        return bankDetailsRepository.save(bankDetails);
    }

    /**
     * Get bank details of logged-in therapist.
     */
    public TherapistBankDetailsResponse getBankDetails(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        TherapistBankDetails bd =
                bankDetailsRepository.findByTherapist_Id(therapistId)
                        .orElseThrow(() ->
                                new RuntimeException("Bank details not found"));

        TherapistBankDetailsResponse r =
                new TherapistBankDetailsResponse();
        r.setId(bd.getId());
        r.setBankName(bd.getBankName());
        r.setAccountNumber(bd.getAccountNumber());
        r.setIfscCode(bd.getIfscCode());
        r.setPanNumber(bd.getPanNumber());
        r.setAadhaarNumber(bd.getAadhaarNumber());
        r.setBankDocument(bd.getBankDocument());
        r.setPanDocument(bd.getPanDocument());
        r.setAadhaarDocument(bd.getAadhaarDocument());

        return r;
    }

    /**
     * Delete bank details of logged-in therapist.
     */
    @Transactional
    public void deleteBankDetails(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        int deleted =
                bankDetailsRepository.deleteByTherapist_Id(therapistId);

        if (deleted == 0) {
            throw new RuntimeException(
                    "Bank details not found or access denied");
        }
    }
}
