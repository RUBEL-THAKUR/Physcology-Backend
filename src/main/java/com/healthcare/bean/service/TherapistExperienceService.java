//package com.healthcare.bean.service;
//
//import com.healthcare.bean.dto.TherapistExperienceDTO;
//import com.healthcare.bean.model.Therapist;
//import com.healthcare.bean.model.TherapistExperienceRequest;
//import com.healthcare.bean.repository.TherapistExperienceRepository;
//import com.healthcare.bean.repository.TherapistRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class TherapistExperienceService {
//
//    private final TherapistExperienceRepository experienceRepository;
//    private final TherapistRepository therapistRepository;
//    private final FileStorageService fileStorageService;
//
//    public TherapistExperienceRequest addExperience(
//            TherapistExperienceDTO dto,
//            MultipartFile certificate
//    ) {
//        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
//                .orElseThrow(() -> new RuntimeException("Therapist not found with ID: " + dto.getTherapistId()));
//
//        TherapistExperienceRequest exp = new TherapistExperienceRequest();
//        exp.setDesignation(dto.getDesignation());
//        exp.setOrganizationName(dto.getOrganizationName());
//        exp.setEmploymentFrom(dto.getEmploymentFrom());
//        exp.setContinuing(dto.getContinuing());
//
//        if (Boolean.TRUE.equals(dto.getContinuing())) {
//            exp.setEmploymentTo(null);
//        } else {
//            exp.setEmploymentTo(dto.getEmploymentTo());
//        }
//
//        if (certificate != null && !certificate.isEmpty()) {
//            String filePath = fileStorageService.storeFile(certificate);
//            exp.setExperienceCertificate(filePath);
//        }
//
//        exp.setTherapist(therapist);
//        return experienceRepository.save(exp);
//    }
//
//    public List<TherapistExperienceRequest> getAllExperience(UUID therapistId) {
//        return experienceRepository.findByTherapistId(therapistId);
//    }
//
//    public void deleteExperience(Long experienceId) {
//        TherapistExperienceRequest experience = experienceRepository.findById(experienceId)
//                .orElseThrow(() -> new RuntimeException("Experience not found with ID: " + experienceId));
//        experienceRepository.delete(experience);
//    }
//}

package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistExperienceDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistExperience;
import com.healthcare.bean.repository.TherapistExperienceRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistExperienceService {

    private final TherapistExperienceRepository experienceRepository;
    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;
    private final JwtUtil jwtUtil;

    // ✅ ADD EXPERIENCE
    @Transactional
    public TherapistExperience addExperience(
            String token,
            TherapistExperienceDTO dto,
            MultipartFile certificate
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

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
            String path = fileStorageService.storeFile(certificate);
            exp.setExperienceCertificate(path);
        }

        exp.setTherapist(therapist);
        return experienceRepository.save(exp);
    }

    // ✅ GET MY EXPERIENCES
    public List<TherapistExperience> getAllExperience(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);
        return experienceRepository.findByTherapist_Id(therapistId);
    }

    // ✅ DELETE EXPERIENCE (ownership check)
    @Transactional
    public void deleteExperience(String token, Long experienceId) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        int deleted = experienceRepository
                .deleteByIdAndTherapist_Id(experienceId, therapistId);

        if (deleted == 0) {
            throw new RuntimeException("Experience not found or access denied");
        }
    }
}
