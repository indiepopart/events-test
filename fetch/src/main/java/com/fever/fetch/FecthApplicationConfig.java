package com.fever.fetch;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "fever.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class FecthApplicationConfig {
}
