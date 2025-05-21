package com.improveid.hms.patientservice.feign;

import com.improveid.hms.patientservice.Dto.otherService.MailDtoClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EMAIL-SERVICE")
public interface MailServiceClient {

    @PostMapping("/mail/send")
    void sendEmail(@RequestBody MailDtoClass mailDto);
    @PostMapping("/mail/verify-otp")public boolean verifyOtp
            (@RequestParam String email, @RequestParam String otp);
}
