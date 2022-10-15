package com.company.crm.rules.configuration;

import com.company.crm.entity.Company;
import com.company.crm.rules.BusinessRules;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

@SpringJUnitConfig(BusinessRulesConfiguration.class)
class BusinessRulesConfigurationTest {
    @MockBean
    private BusinessRules<Company> businessRules;

    @MockBean
    private BusinessRulesConfiguration businessRulesConfiguration;

    @Autowired
    private Set<BusinessRules<Company>> setOfBeans;

    /**
     * Method under test: {@link BusinessRulesConfiguration#findBusinessRule(BusinessRules.BusinessRule)}
     */
    @Test
    void testFindBusinessRule() {
        Mockito.when(businessRulesConfiguration.findBusinessRule(Mockito.any())).thenReturn(businessRules);
        businessRulesConfiguration.findBusinessRule(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE);
        Mockito.verify(businessRulesConfiguration).findBusinessRule(Mockito.any());
    }

    /**
     * Method under test: {@link BusinessRulesConfiguration#findBusinessRule(BusinessRules.BusinessRule)}
     */
    @Test
    void testFindBusinessRule2() {
        Mockito.when(businessRulesConfiguration.findBusinessRule(Mockito.any())).thenReturn(businessRules);
        businessRulesConfiguration.findBusinessRule(BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE);
        Mockito.verify(businessRulesConfiguration).findBusinessRule(Mockito.any());
    }
}