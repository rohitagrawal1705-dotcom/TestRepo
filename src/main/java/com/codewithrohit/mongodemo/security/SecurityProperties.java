package com.codewithrohit.mongodemo.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "todo.security")
@Data
public class SecurityProperties {

    private Set<String> allowedClientIds;
}
