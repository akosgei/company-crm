package com.company.crm.review.service;

import com.company.crm.review.domain.CompanySummaryDto;
import com.company.crm.review.entity.Company;
import com.company.crm.review.repository.CompanyRepository;
import com.company.crm.review.rules.BusinessRules;
import com.company.crm.review.rules.configuration.BusinessRulesConfiguration;
import com.company.crm.review.util.ModelMapperUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @Mock
    BusinessRulesConfiguration rulesConfigurationMock;

    @InjectMocks
    CompanyService companyService;


    @Before
    public void setup() {
        when(rulesConfigurationMock.findBusinessRule(any(BusinessRules.BusinessRule.class))).thenReturn(null);
    }

    @Test
    public void save() {
        List<Company> response = ModelMapperUtil.convertDtoToEntity(CompanyTestData.getCompanyData(), Company.class);
        when(companyRepository.saveAll(anyList())).thenReturn(response);
        List<Company> result = companyService.save(CompanyTestData.getCompanyData());

        Assert.assertEquals("Company_A", result.get(0).getName());
        Assert.assertEquals(Long.valueOf(8000), result.get(0).getId());
    }

    @Test
    public void viewCompanySummary() {
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId(anyLong())).thenReturn(CompanyTestData.getCompanySummaryData());
        CompanySummaryDto result = companyService.viewCompanySummary(1L);
        Assert.assertEquals("DummyCompany", result.getCompanyName());
        Assert.assertEquals(Integer.valueOf(2), result.getConversationCount());
        Assert.assertEquals("2222,4444", result.getMostPopularUser());
    }
}