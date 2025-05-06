package com.improveid.hms.patientservice.Dto.otherService;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class MailDtoClass {
    private String fullName;
    private String doctorName;
    private LocalDate bookedDate;
    private LocalTime slotTime;
    private String mailId;
}
