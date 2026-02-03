package com.healthcare.bean.controller;

import com.healthcare.bean.constant.AppConstants;
import com.healthcare.bean.dto.AppointmentDTO;
import com.healthcare.bean.model.Appointment;
import com.healthcare.bean.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(service.createAppointment(dto));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAppointmentById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(service.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.ok(AppConstants.DELETED_SUCCESSFULLY);
    }
}
