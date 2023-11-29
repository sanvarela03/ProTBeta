package com.example.springbootjwtauthentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.io.Resource;
@ConfigurationProperties(prefix = "gcp.firebase")
@Data
public class FirebaseProperties {
    private Resource serviceAccount;
}
