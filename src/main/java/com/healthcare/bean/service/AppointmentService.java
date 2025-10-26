package com.healthcare.bean.service;

import com.healthcare.bean.dto.AppointmentDTO;
import com.healthcare.bean.exception.ResourceNotFoundException;
import com.healthcare.bean.model.Appointment;
import com.healthcare.bean.repository.AppointmentRepository;
import com.healthcare.bean.util.AppConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment createAppointment(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setPatientName(dto.getPatientName());
        appointment.setDoctorName(dto.getDoctorName());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStatus(dto.getStatus());
        return repository.save(appointment);
    }

    public List<AppointmentDTO> getAllAppointments() {
        return repository.findAll()
                .stream()
                .map(app -> new AppointmentDTO(
                        app.getPatientName(),
                        app.getDoctorName(),
                        app.getAppointmentDate(),
                        app.getStatus()))
                .collect(Collectors.toList());
    }

    public Appointment getAppointmentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.APPOINTMENT_NOT_FOUND));
    }

    public Appointment updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = getAppointmentById(id);
        appointment.setPatientName(dto.getPatientName());
        appointment.setDoctorName(dto.getDoctorName());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStatus(dto.getStatus());
        return repository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        repository.delete(appointment);
    }
}
