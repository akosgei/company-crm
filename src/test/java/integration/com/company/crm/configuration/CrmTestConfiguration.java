package com.company.crm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@TestConfiguration
public class CrmTestConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    WireMockConfigurationCustomizer optionsCustomizer() {
        return options -> {
            // perform your customization here
            log.info("Loading custom wiremock configurations ...");
        };
    }
    //TODO: custom injection of properties into the application context
    /*
    class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "https://dummyjson.com/=" + "http://localhost:" + wireMockServer.port()
            ).applyTo(applicationContext.getEnvironment());
        }
    }*/
}
