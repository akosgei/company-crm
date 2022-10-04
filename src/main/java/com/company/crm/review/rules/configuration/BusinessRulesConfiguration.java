package com.company.crm.review.rules.configuration;

import com.company.crm.review.rules.BusinessRules;
import com.company.crm.review.domain.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Service
public class BusinessRulesConfiguration {

    private final Map<BusinessRules.BusinessRule, BusinessRules<CompanyDto>> businessRuleCompanyImportRuleMap = new EnumMap<>(BusinessRules.BusinessRule.class);

    @Autowired
    public BusinessRulesConfiguration(Set<BusinessRules<CompanyDto>> businessRuleCompanyImportRuleMap) {
        this.createBusinessRuleMap(businessRuleCompanyImportRuleMap);
    }

    public BusinessRules<CompanyDto> findBusinessRule(BusinessRules.BusinessRule businessRule) {
        return businessRuleCompanyImportRuleMap.get(businessRule);
    }

    private void createBusinessRuleMap(Set<BusinessRules<CompanyDto>> importRuleSet) {
        importRuleSet.forEach(businessRuleBean -> businessRuleCompanyImportRuleMap.put(businessRuleBean.getBusinessRule(), businessRuleBean));
    }
}
