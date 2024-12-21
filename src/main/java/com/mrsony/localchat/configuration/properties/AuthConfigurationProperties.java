package com.mrsony.localchat.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.auth")
public class AuthConfigurationProperties {
    private TokenTtlInfo mobile;

}
