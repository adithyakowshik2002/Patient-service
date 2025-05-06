package com.improveid.hms.patientservice.Dto.otherService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Long id;
    private String name;
    private String qualifications;
    private String email;
    private Long userId;
}
