package com.improveid.hms.patientservice.Service;
import com.improveid.hms.patientservice.Dto.request.AppointmentRequest;
import com.improveid.hms.patientservice.Dto.response.AppointmentResponse;
import com.improveid.hms.patientservice.Dto.response.AppointmentsDto;
import com.improveid.hms.patientservice.Entity.Appointment;
import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
import com.improveid.hms.patientservice.Enums.RoomType;
import com.improveid.hms.patientservice.Exception.CustomException;
import com.improveid.hms.patientservice.Exception.EntityNotFoundException;
import com.improveid.hms.patientservice.Mapper.AppointmentMapper;
import com.improveid.hms.patientservice.Repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService{



    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;


    //  it will fetch the patient
    @Override
    public List<AppointmentResponse> getAppointmentsForPatient(Long patientId) {
        log.info("Fetching appointments for patientId: {}", patientId);
        if(patientId == null|| patientId <=0){
            throw new CustomException("Invalid patient ID", HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        if(appointments == null||appointments.isEmpty()){
            throw new CustomException("No appointments found for patient ID:"+patientId,HttpStatus.NOT_FOUND);

        }


        return appointmentMapper.toResponse(appointments);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByStatus(String status) {
        AppointmentStatus appointmentStatus;

        try{
            appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException("Invalid appointment status: "+status,HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointments = appointmentRepository.findByStatus(appointmentStatus);

        return appointments.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public AppointmentResponse convertToIP(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(()->new RuntimeException("Appointment not found"));

        if(appointment.getAppointmentType()== AppointmentType.IP){
            throw new RuntimeException("Appointment is already IP:");
        }
        appointment.setAppointmentType(AppointmentType.IP);

        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {

       if(request == null || id == null)
       {
           throw new CustomException("Appointment ID and details are required",HttpStatus.BAD_REQUEST);
       }
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Appointment not found",HttpStatus.NOT_FOUND));
        request.setAppointmentId(existing.getAppointmentId());

        Appointment updateEntity = appointmentMapper.toEntity(request);

        updateEntity.setAppointmentId(existing.getAppointmentId());

        Appointment savedAppointment  = appointmentRepository.save(updateEntity);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public void cancelAppointment(Long id) {
    Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new CustomException("Appointment not found with id: ",HttpStatus.NOT_FOUND));

    appointment.setStatus(AppointmentStatus.CANCELLED);
    appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {

    Appointment appointment =  appointmentRepository.findById(id).orElseThrow(()->new CustomException("Appointment not found",HttpStatus.NOT_FOUND));
        return appointmentMapper.toResponse(appointment);

    }

    @Override
    public Long getPatientIdByAppointmentId(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> appointment.getPatient().getId())
                .orElse(null);
    }


@Override
    public String getRoomTypeByAppointmentId(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + appointmentId));

        return appointment.getRoomType().toString(); // assuming you have a field called 'roomType' in AppointmentEntity
    }

    @Override
    public List<AppointmentsDto> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        return appointments.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private AppointmentsDto mapToResponse(Appointment appointment) {
        return AppointmentsDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .patientName(appointment.getPatient().getFirstName()+appointment.getPatient().getLastName())
                .appointmentDate(appointment.getAppointmentDate())
                .timeslot(appointment.getTimeslot())
                .appointmentType(appointment.getAppointmentType())
                .build();
    }
}
