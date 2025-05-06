package com.improveid.hms.patientservice.feign;

import com.improveid.hms.patientservice.Dto.otherService.MailDtoClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface MailServiceClient {

    @PostMapping("/mail/send")
    void sendEmail(@RequestBody MailDtoClass mailDto);
}
