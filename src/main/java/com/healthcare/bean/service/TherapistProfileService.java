package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistProfileBasicInfoRequest;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistProfile;
import com.healthcare.bean.repository.TherapistProfileRepository;
import com.healthcare.bean.repository.TherapistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TherapistProfileService {

    private final TherapistProfileRepository repository;
    private final TherapistRepository therapistRepository;

    public TherapistProfileService(
            TherapistProfileRepository repository,
            TherapistRepository therapistRepository) {
        this.repository = repository;
        this.therapistRepository = therapistRepository;
    }

    @Transactional
    public TherapistProfile saveBasicInfo(
            UUID therapistId,
            TherapistProfileBasicInfoRequest req) {

        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() ->
                        new RuntimeException("Therapist not found with ID: " + therapistId));

        // Check if profile already exists for this therapist
        TherapistProfile profile = repository.findByTherapistId(therapistId)
                .orElse(new TherapistProfile());

        // Set therapist (for new profile)
        if (profile.getId() == null) {
            profile.setTherapist(therapist);
        }

        profile.setPrefix(req.getPrefix());
        profile.setFirstName(req.getFirstName());
        profile.setMiddleName(req.getMiddleName());
        profile.setLastName(req.getLastName());
        profile.setCategory(req.getCategory());
        profile.setEmailId(req.getEmailId());
        profile.setUserName(req.getUserName());
        profile.setMobile(req.getMobile());
        profile.setDateOfBirth(req.getDateOfBirth());
        profile.setGender(req.getGender());
        profile.setLanguage(req.getLanguage());
        profile.setBriefDescription(req.getBriefDescription());

        profile.setPresentAddress(req.getPresentAddress());
        profile.setPresentCountry(req.getPresentCountry());
        profile.setPresentState(req.getPresentState());
        profile.setPresentCity(req.getPresentCity());
        profile.setPresentDistrict(req.getPresentDistrict());
        profile.setPresentPinCode(req.getPresentPinCode());

        if (req.isSameAsPresentAddress()) {
            profile.setClinicAddress(req.getPresentAddress());
            profile.setClinicCountry(req.getPresentCountry());
            profile.setClinicState(req.getPresentState());
            profile.setClinicCity(req.getPresentCity());
            profile.setClinicDistrict(req.getPresentDistrict());
            profile.setClinicPinCode(req.getPresentPinCode());
        } else {
            profile.setClinicAddress(req.getClinicAddress());
            profile.setClinicCountry(req.getClinicCountry());
            profile.setClinicState(req.getClinicState());
            profile.setClinicCity(req.getClinicCity());
            profile.setClinicDistrict(req.getClinicDistrict());
            profile.setClinicPinCode(req.getClinicPinCode());
        }

        profile.setTimeZone(req.getTimeZone());
        profile.setExperience(req.getExperience());

        return repository.save(profile);
    }

    public TherapistProfile getBasicInfo(UUID therapistId) {
        return repository.findByTherapistId(therapistId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for therapist ID: " + therapistId));
    }

    @Transactional
    public void deleteBasicInfo(UUID therapistId) {
        TherapistProfile profile = repository.findByTherapistId(therapistId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for therapist ID: " + therapistId));
        repository.delete(profile);
    }
}