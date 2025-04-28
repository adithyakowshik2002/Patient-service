package com.improveid.hms.patientservice.feign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotBookedDTO {

    private String slotDate;
    private String slotStartTime;
  private Long patientId;
  private Long scheduleId;
    private String slotEndTime;
    private String status;


}
