package com.company.crm.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class CrmTestConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    //TODO: custom injection of properties into the application context
    /*
    class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public PropertiesInitializer() {
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "https://dummyjson.com/=" + "http://localhost:" + wireMockServer.port()
            ).applyTo(applicationContext.getEnvironment());
        }
    }*/
}
