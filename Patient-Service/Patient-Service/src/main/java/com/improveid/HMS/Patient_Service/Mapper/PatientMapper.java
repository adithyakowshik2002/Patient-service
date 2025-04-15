package com.improveid.HMS.Patient_Service.Mapper;

import com.improveid.HMS.Patient_Service.Dto.response.PatientResponse;
import com.improveid.HMS.Patient_Service.Dto.request.PatientRequest;
import com.improveid.HMS.Patient_Service.Entity.PatientEntity;
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
