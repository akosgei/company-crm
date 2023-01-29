package com.company.crm.configuration;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CrmTestConfiguration {

    @Bean
    WireMockConfiguration wireMockConfiguration() {
        return new WireMockConfiguration().httpsPort(9292);
    }
}
