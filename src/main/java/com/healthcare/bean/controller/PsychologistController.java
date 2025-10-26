package com.healthcare.bean.controller;

import com.healthcare.bean.dto.PsychologistDto;
import com.healthcare.bean.model.Psychologist;
import com.healthcare.bean.service.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService service;

    @PostMapping("/create")
    public ResponseEntity<PsychologistDto> create(@RequestBody PsychologistDto dto) {
        return ResponseEntity.ok(service.createPsychologist(dto));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<PsychologistDto> dtoList = service.getAll().stream()
                .map(service::convertToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Psychologist p = service.getById(id);
        if (p == null) {
            return ResponseEntity.status(404).body("Psychologist not found with ID: " + id);
        }
        return ResponseEntity.ok(service.convertToDto(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Psychologist> update(@PathVariable Long id, @RequestBody PsychologistDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Psychologist profile deleted with ID: " + id);
    }

}
