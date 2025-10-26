package com.healthcare.bean.service;

import com.healthcare.bean.dto.ExperienceDto;
import com.healthcare.bean.dto.PsychologistDto;
import com.healthcare.bean.model.Experience;
import com.healthcare.bean.model.Psychologist;
import com.healthcare.bean.repository.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    public PsychologistDto convertToDto(Psychologist p) {
        PsychologistDto dto = new PsychologistDto();
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setFullName(p.getFullName());
        dto.setCategory(p.getCategory());
        dto.setEmail(p.getEmail());
        dto.setPhone(p.getPhone());
        dto.setSpecialization(p.getSpecialization());
        dto.setDob(p.getDob());
        dto.setLanguage(p.getLanguage());
        dto.setBriefDescription(p.getBriefDescription());
        dto.setCurrentAddress(p.getCurrentAddress());
        dto.setCountry(p.getCountry());
        dto.setTimeZone(p.getTimeZone());

        List<ExperienceDto> expList = p.getExperienceList().stream().map(exp -> {
            ExperienceDto e = new ExperienceDto();
            e.setSerialNo(exp.getSerialNo());
            e.setDesignation(exp.getDesignation());
            e.setStartingDate(exp.getStartingDate());
            e.setEndingDate(exp.getEndingDate());
            e.setCertification(exp.getCertification());
            e.setAction(exp.getAction());
            return e;
        }).toList();

        dto.setExperienceList(expList);
        return dto;
    }

    public PsychologistDto createPsychologist(PsychologistDto dto) {
        Psychologist p = new Psychologist();
        mapDtoToEntity(dto, p);  // reuse karo mapping method
        Psychologist saved = psychologistRepository.save(p);
        return convertToDto(saved); // convert back to DTO
    }

    public List<Psychologist> getAll() {
        return psychologistRepository.findAll();
    }

    public Psychologist getById(Long id) {
        return psychologistRepository.findById(id).orElse(null);
    }



    public Psychologist update(Long id, PsychologistDto dto) {
        Psychologist existing = psychologistRepository.findById(id).orElseThrow();

        // Clear references from old experiences to prevent infinite nesting
        if (existing.getExperienceList() != null) {
            existing.getExperienceList().forEach(exp -> exp.setPsychologist(null));
            existing.getExperienceList().clear();
        }

        mapDtoToEntity(dto, existing);  // This adds new experiences with psychologist set
        return psychologistRepository.save(existing);
    }



    public void deleteById(Long id) {
        psychologistRepository.deleteById(id);
    }


    private void mapDtoToEntity(PsychologistDto dto, Psychologist p) {
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setFullName(dto.getFullName());
        p.setCategory(dto.getCategory());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setSpecialization(dto.getSpecialization());
        p.setDob(dto.getDob());
        p.setLanguage(dto.getLanguage());
        p.setBriefDescription(dto.getBriefDescription());
        p.setCurrentAddress(dto.getCurrentAddress());
        p.setCountry(dto.getCountry());
        p.setTimeZone(dto.getTimeZone());

        // 👇 Experience list convert kar ke psychologist object me set karna
        List<Experience> expList = dto.getExperienceList().stream().map(exp -> {
            Experience e = new Experience();
            e.setAction(exp.getAction());
            e.setCertification(exp.getCertification());
            e.setDesignation(exp.getDesignation());
            e.setEndingDate(exp.getEndingDate());
            e.setStartingDate(exp.getStartingDate());
            e.setSerialNo(exp.getSerialNo());
            e.setPsychologist(p); // important: set parent reference
            return e;
        }).collect(Collectors.toList());

        p.setExperienceList(expList); // 👈 final set
    }
}
