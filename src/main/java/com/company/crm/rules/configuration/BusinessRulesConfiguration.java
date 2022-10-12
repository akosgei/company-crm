package com.company.crm.rules.configuration;


import com.company.crm.entity.Company;
import com.company.crm.rules.BusinessRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;


@Service
public class BusinessRulesConfiguration {

    private final Map<BusinessRules.BusinessRule, BusinessRules<Company>> businessRuleCompanyImportRuleMap = new EnumMap<>(BusinessRules.BusinessRule.class);

    @Autowired
    public BusinessRulesConfiguration(Set<BusinessRules<Company>> businessRuleCompanyImportRuleMap) {
        this.createBusinessRuleMap(businessRuleCompanyImportRuleMap);
    }

    public BusinessRules<Company> findBusinessRule(BusinessRules.BusinessRule businessRule) {
        return businessRuleCompanyImportRuleMap.get(businessRule);
    }

    private void createBusinessRuleMap(Set<BusinessRules<Company>> importRuleSet) {
        importRuleSet.forEach(businessRuleBean -> businessRuleCompanyImportRuleMap.put(businessRuleBean.getBusinessRule(), businessRuleBean));
    }
}
