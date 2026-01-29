package com.healthcare.bean.service;

import com.healthcare.bean.dto.*;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistStatus;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TherapistService {

    private final TherapistRepository therapistRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.upload.cv.dir:uploads/cv/}")
    private String uploadDir;

    // SIGNUP
    public TherapistResponseDTO signupTherapist(TherapistSignupRequest request, MultipartFile cvFile) throws IOException {

        if (therapistRepository.existsByEmailId(request.getEmailId())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (therapistRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        if (!request.isAbove18()) {
            throw new IllegalArgumentException("You must be above 18 years");
        }

        if (!request.isAcceptedTerms()) {
            throw new IllegalArgumentException("You must accept terms and conditions");
        }

        if (cvFile == null || cvFile.isEmpty()) {
            throw new IllegalArgumentException("CV is required");
        }

        String cvFileName = saveCV(cvFile);

        Therapist therapist = Therapist.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailId(request.getEmailId())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileNumber(request.getMobileNumber())
                .cvFileName(cvFileName)
                .isAbove18(request.isAbove18())
                .acceptedTerms(request.isAcceptedTerms())
                .status(TherapistStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Therapist saved = therapistRepository.save(therapist);
        return toResponseDTO(saved);
    }

    // LOGIN
    public TherapistResponseDTO loginTherapist(TherapistLoginRequest request) {
        Therapist therapist = therapistRepository.findByEmailId(request.getEmailId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), therapist.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (therapist.getStatus() != TherapistStatus.APPROVED) {
            throw new IllegalArgumentException("Your account is " + therapist.getStatus());
        }

        return toResponseDTO(therapist);
    }

    // GET BY ID
    public TherapistResponseDTO getTherapistById(UUID id) {
        Therapist therapist = therapistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + id));
        return toResponseDTO(therapist);
    }

    // GET ALL
    public List<TherapistResponseDTO> getAllTherapists() {
        return therapistRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // GET BY STATUS
    public List<TherapistResponseDTO> getTherapistsByStatus(TherapistStatus status) {
        return therapistRepository.findByStatus(status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    public TherapistResponseDTO updateTherapist(UUID id, TherapistUpdateRequest request) {
        Therapist therapist = therapistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + id));

        if (request.getFirstName() != null) {
            therapist.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            therapist.setLastName(request.getLastName());
        }
        if (request.getMobileNumber() != null) {
            if (therapistRepository.existsByMobileNumber(request.getMobileNumber()) &&
                    !therapist.getMobileNumber().equals(request.getMobileNumber())) {
                throw new IllegalArgumentException("Mobile number already in use");
            }
            therapist.setMobileNumber(request.getMobileNumber());
        }

        Therapist updated = therapistRepository.save(therapist);
        return toResponseDTO(updated);
    }

    // DELETE
    public void deleteTherapist(UUID id) {
        Therapist therapist = therapistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + id));
        therapistRepository.delete(therapist);
    }

    // UPDATE STATUS (APPROVE/REJECT)
    public TherapistResponseDTO updateStatus(UUID id, TherapistStatus status, String reason, String approvedBy) {
        Therapist therapist = therapistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + id));

        therapist.setStatus(status);

        if (status == TherapistStatus.APPROVED) {
            therapist.setApprovedAt(LocalDateTime.now());
            therapist.setApprovedBy(approvedBy);
            therapist.setRejectionReason(null);
        } else if (status == TherapistStatus.REJECTED) {
            therapist.setRejectionReason(reason);
            therapist.setApprovedAt(null);
            therapist.setApprovedBy(null);
        }

        Therapist updated = therapistRepository.save(therapist);
        return toResponseDTO(updated);
    }

    // HELPER: Save CV
    private String saveCV(MultipartFile file) throws IOException {
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadDir + uniqueFilename);
        Files.write(filePath, file.getBytes());

        return uniqueFilename;
    }

    // HELPER: Convert to DTO
    private TherapistResponseDTO toResponseDTO(Therapist therapist) {
        return TherapistResponseDTO.builder()
                .id(therapist.getId())
                .firstName(therapist.getFirstName())
                .lastName(therapist.getLastName())
                .emailId(therapist.getEmailId())
                .mobileNumber(therapist.getMobileNumber())
                .cvFileName(therapist.getCvFileName())
                .isAbove18(therapist.isAbove18())
                .acceptedTerms(therapist.isAcceptedTerms())
                .status(therapist.getStatus())
                .createdAt(therapist.getCreatedAt())
                .updatedAt(therapist.getUpdatedAt())
                .rejectionReason(therapist.getRejectionReason())
                .approvedAt(therapist.getApprovedAt())
                .approvedBy(therapist.getApprovedBy())
                .build();
    }

    public String getCVFullPath(String fileName) {
        return uploadDir + fileName;
    }
}