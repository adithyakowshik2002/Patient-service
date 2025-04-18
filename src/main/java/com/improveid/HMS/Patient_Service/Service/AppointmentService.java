package com.improveid.HMS.Patient_Service.Service;

import com.improveid.HMS.Patient_Service.Dto.request.AppointmentRequest;
import com.improveid.HMS.Patient_Service.Dto.response.AppointmentResponse;
import com.improveid.HMS.Patient_Service.Entity.Appointment;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse bookAppointment(AppointmentRequest appointment);
    AppointmentResponse updateAppointment(Long id, AppointmentRequest appointment);
    void cancelAppointment(Long id);
    AppointmentResponse getAppointmentById(Long id);
    List<AppointmentResponse> getAppointmentsForPatient(Long patientId);
    List<AppointmentResponse> getAppointmentsByStatus(String status);
}
