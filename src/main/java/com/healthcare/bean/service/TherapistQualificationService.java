package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistQualificationDTO;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistQualification;
import com.healthcare.bean.repository.TherapistQualificationRepository;
import com.healthcare.bean.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapistQualificationService {

    private final TherapistQualificationRepository qualificationRepository;
    // ✅ TherapistProfileRepository hata ke TherapistRepository
    private final TherapistRepository therapistRepository;
    private final FileStorageService fileStorageService;

    public TherapistQualification addQualification(
            TherapistQualificationDTO dto,
            MultipartFile document
    ) {
        // ✅ Ab Therapist entity (UUID) fetch karenge
        Therapist therapist = therapistRepository.findById(dto.getTherapistId())
                .orElseThrow(() -> new RuntimeException(
                        "Therapist with ID " + dto.getTherapistId() + " not found"
                ));

        TherapistQualification qualification = new TherapistQualification();
        qualification.setDegreeType(dto.getDegreeType());
        qualification.setDegreeIn(dto.getDegreeIn());
        qualification.setInstituteUniversity(dto.getInstituteUniversity());
        qualification.setYearOfPassing(dto.getYearOfPassing());
        qualification.setPercentageCgpa(dto.getPercentageCgpa());

        if (document != null && !document.isEmpty()) {
            String filePath = fileStorageService.storeFile(document);
            qualification.setQualificationDocument(filePath);
        }

        // ✅ Therapist entity set kari
        qualification.setTherapist(therapist);
        return qualificationRepository.save(qualification);
    }

    public List<TherapistQualification> getAllQualifications(UUID therapistId) {
        // ✅ UUID parameter
        return qualificationRepository.findByTherapistId(therapistId);
    }

    public void deleteQualification(Long qualificationId) {
        qualificationRepository.deleteById(qualificationId);
    }
}