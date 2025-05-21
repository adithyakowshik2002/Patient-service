package com.improveid.hms.patientservice.config;



import com.improveid.hms.patientservice.config.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private  JwtValidator jwtValidator;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permit all for patient registration (you may also permit POST to /api/patients if it's a registration endpoint)
                        .requestMatchers(HttpMethod.POST, "/api/patients/register","/api/patients/book-existing").permitAll()
                        .requestMatchers("/api/patients/aadhar/**","api/patients/{id}").permitAll()

                        // DOCTOR-only endpoint
                        .requestMatchers(HttpMethod.PUT,"/api/appointments/converttoip/**","/api/appointments/update-status/**").hasAuthority("DOCTOR")


                        // ADMIN & DOCTOR shared access
                        .requestMatchers(
                                "/api/appointments/appointment/**",                  // any depth
                                "/api/appointments/room-type/*",                    // * for {appointmentId}
                                "/api/appointments/fetch-appointments/**",          // wildcard for nested fetch
                                "/api/appointments/appointments/*",                 // * for {patientId}
                                "/api/patients/getallpatients"
                        ).hasAnyAuthority("ADMIN", "DOCTOR")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

