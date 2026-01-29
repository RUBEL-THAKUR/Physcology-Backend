package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistExperienceDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistExperience;
import com.healthcare.bean.repository.TherapistExperienceRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistExperienceService {

    private final TherapistExperienceRepository experienceRepository;
    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;

    public TherapistExperience addExperience(
            TherapistExperienceDTO dto,
            MultipartFile certificate
    ) {
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + dto.getTherapistId()));

        TherapistExperience exp = new TherapistExperience();
        exp.setDesignation(dto.getDesignation());
        exp.setOrganizationName(dto.getOrganizationName());
        exp.setEmploymentFrom(dto.getEmploymentFrom());
        exp.setContinuing(dto.getContinuing());

        if (Boolean.TRUE.equals(dto.getContinuing())) {
            exp.setEmploymentTo(null);
        } else {
            exp.setEmploymentTo(dto.getEmploymentTo());
        }

        if (certificate != null && !certificate.isEmpty()) {
            String filePath = fileStorageService.storeFile(certificate);
            exp.setExperienceCertificate(filePath);
        }

        exp.setTherapist(therapist);
        return experienceRepository.save(exp);
    }

    public List<TherapistExperience> getAllExperience(UUID therapistId) {
        return experienceRepository.findByTherapistId(therapistId);
    }

    public void deleteExperience(Long experienceId) {
        TherapistExperience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("Experience not found with ID: " + experienceId));
        experienceRepository.delete(experience);
    }
}