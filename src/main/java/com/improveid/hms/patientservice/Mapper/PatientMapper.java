package com.improveid.hms.patientservice.Mapper;

import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Entity.PatientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PatientEntity toEntity(PatientRequest request){
        return modelMapper.map(request,PatientEntity.class);
    }

    public PatientResponse toResponse(PatientEntity entity){
        return modelMapper.map(entity,PatientResponse.class);
    }

}
