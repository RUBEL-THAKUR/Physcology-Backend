package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistQualificationDTO;
import com.healthcare.bean.dto.TherapistQualificationResponse;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistQualification;
import com.healthcare.bean.repository.TherapistQualificationRepository;
import com.healthcare.bean.repository.TherapistRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Service layer for therapist qualification management.
 * All therapist ownership is resolved from JWT.
 */
@Service
@RequiredArgsConstructor
public class TherapistQualificationService {

    private final TherapistQualificationRepository qualificationRepository;
    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;
    private final JwtUtil jwtUtil;

    /**
     * Add qualification for logged-in therapist.
     */
    @Transactional
    public TherapistQualification addQualification(
            String token,
            TherapistQualificationDTO dto,
            MultipartFile document
    ) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));

        TherapistQualification qualification = new TherapistQualification();
        qualification.setDegreeType(dto.getDegreeType());
        qualification.setDegreeIn(dto.getDegreeIn());
        qualification.setInstituteUniversity(dto.getInstituteUniversity());
        qualification.setYearOfPassing(dto.getYearOfPassing());
        qualification.setPercentageCgpa(dto.getPercentageCgpa());

        if (document != null && !document.isEmpty()) {
            String path = fileStorageService.storeFile(document);
            qualification.setQualificationDocument(path);
        }

        qualification.setTherapist(therapist);
        return qualificationRepository.save(qualification);
    }

    /**
     * Get all qualifications of logged-in therapist.
     * Uses response DTO to avoid Hibernate proxy serialization issues.
     */
    public List<TherapistQualificationResponse> getAllQualifications(String token) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        return qualificationRepository.findByTherapist_Id(therapistId)
                .stream()
                .map(q -> {
                    TherapistQualificationResponse r =
                            new TherapistQualificationResponse();
                    r.setId(q.getId());
                    r.setDegreeType(q.getDegreeType());
                    r.setDegreeIn(q.getDegreeIn());
                    r.setInstituteUniversity(q.getInstituteUniversity());
                    r.setYearOfPassing(q.getYearOfPassing());
                    r.setPercentageCgpa(q.getPercentageCgpa());
                    r.setQualificationDocument(q.getQualificationDocument());
                    return r;
                })
                .toList();
    }

    /**
     * Delete qualification owned by logged-in therapist.
     */
    @Transactional
    public void deleteQualification(String token, Long qualificationId) {
        UUID therapistId = jwtUtil.extractTherapistId(token);

        int deleted = qualificationRepository
                .deleteByIdAndTherapist_Id(qualificationId, therapistId);

        if (deleted == 0) {
            throw new RuntimeException("Qualification not found or access denied");
        }
    }
}
