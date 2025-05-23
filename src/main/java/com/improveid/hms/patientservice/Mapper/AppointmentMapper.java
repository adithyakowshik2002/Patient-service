package com.improveid.hms.patientservice.Mapper;

import com.improveid.hms.patientservice.Dto.request.AppointmentRequest;
import com.improveid.hms.patientservice.Dto.response.AppointmentResponse;
import com.improveid.hms.patientservice.Entity.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Appointment toEntity(AppointmentRequest request){
        return modelMapper.map(request,Appointment.class);
    }

    public AppointmentResponse toResponse(Appointment entity){
        return modelMapper.map(entity,AppointmentResponse.class);
    }

    public List<AppointmentResponse> toResponse(List<Appointment> entities){
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
