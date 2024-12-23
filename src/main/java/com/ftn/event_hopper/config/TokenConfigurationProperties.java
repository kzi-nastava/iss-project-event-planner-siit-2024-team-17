package com.ftn.event_hopper.config;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class TokenConfigurationProperties {
    @NotEmpty
    private String secretKey;

    @NotNull
    @Positive
    private Long expirationTimeMs;
}
