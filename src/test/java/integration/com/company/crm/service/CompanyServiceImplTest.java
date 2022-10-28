package com.company.crm.service;

import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import com.company.crm.exception.ApplicationActivityException;
import com.company.crm.repository.CompanyRepository;
import com.company.crm.rules.RemoveDuplicateThreadsRule;
import com.company.crm.rules.configuration.BusinessRulesConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**Below @SpringJunitConfig annotation bundles in the below two annotations.
*@ContextConfiguration(classes = {CompanyServiceImpl.class})
@ExtendWith(SpringExtension.class)

NB: Mock beans are automatically reset after each test method
* */
@SpringJUnitConfig(CompanyServiceImpl.class)
class CompanyServiceImplTest {
    @MockBean
    private BusinessRulesConfiguration businessRulesConfiguration;

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    /**
     * Method under test: {@link CompanyServiceImpl#save(List)}
     */
    @Test
    void testSaveCompaniesPassesOnValidData() {
        when(companyRepository.saveAll(any())).thenReturn((Iterable<Company>) mock(Iterable.class));
        when(businessRulesConfiguration.findBusinessRule(any()))
                .thenReturn(new RemoveDuplicateThreadsRule());

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        companyServiceImpl.save(companyList);
        verify(companyRepository).saveAll((Iterable<Company>) any());
        verify(businessRulesConfiguration, atLeast(1)).findBusinessRule(any());
        assertEquals(2, companyServiceImpl.rulesToExecute.size());
    }

    /**
     * Method under test: {@link CompanyServiceImpl#save(List)}
     */
    @Test
    void testSaveCompaniesFailsWhenDatabaseOperationThrowsException() {
        when(companyRepository.saveAll(any()))
                .thenThrow(new RuntimeException("Data base table is unavailable"));
        when(businessRulesConfiguration.findBusinessRule(any()))
                .thenReturn(new RemoveDuplicateThreadsRule());

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        assertThrows(RuntimeException.class, () -> companyServiceImpl.save(companyList));
        verify(companyRepository).saveAll(any());
        verify(businessRulesConfiguration, atLeast(1)).findBusinessRule(any());
    }

    /**
     * Method under test: {@link CompanyServiceImpl#viewCompanySummaryDetails(Long)}
     */
    @Test
    void testViewCompanySummaryDetails() {
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId(any())).thenReturn(new ArrayList<>());
        assertThrows(ApplicationActivityException.class, () -> companyServiceImpl.viewCompanySummaryDetails(123L));
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId(any());
    }

    /**
     * Method under test: {@link CompanyServiceImpl#viewCompanySummaryDetails(Long)}
     */
    @Test
    void testWhenValidCompanyIdIsPassedForMultipleConversationsMostPopularCompanyIsreturned() {
        CompanySummaryDto companySummaryDto = mock(CompanySummaryDto.class);
        when(companySummaryDto.getCompanyName()).thenReturn("Company Name");
        when(companySummaryDto.getConversationCount()).thenReturn(3);
        when(companySummaryDto.getMostPopularUser()).thenReturn("42");

        CompanySummaryDto companySummaryDto2 = mock(CompanySummaryDto.class);
        when(companySummaryDto2.getCompanyName()).thenReturn("Company Name");
        when(companySummaryDto2.getConversationCount()).thenReturn(4);
        when(companySummaryDto2.getMostPopularUser()).thenReturn("45");

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(companySummaryDto);
        companySummaryDtoList.add(companySummaryDto2);
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId(any()))
                .thenReturn(companySummaryDtoList);
        CompanySummaryDto actualViewCompanySummaryDetailsResult = companyServiceImpl.viewCompanySummaryDetails(123L);
        assertEquals("Company Name", actualViewCompanySummaryDetailsResult.getCompanyName());
        assertEquals(2, actualViewCompanySummaryDetailsResult.getConversationCount().intValue());
        assertEquals("45", actualViewCompanySummaryDetailsResult.getMostPopularUser());

        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId(any());
        verify(companySummaryDto).getConversationCount();
        verify(companySummaryDto).getCompanyName();
        verify(companySummaryDto).getMostPopularUser();
    }

    /**
     * Method under test: {@link CompanyServiceImpl#viewCompanySummaryDetails(Long)}
     */
    @Test
    void testWhenCompanyIdSuppliedIsNotFoundExceptionIsThrown() {
        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId(any()))
                .thenReturn(companySummaryDtoList);
        assertThrows(ApplicationActivityException.class, () -> companyServiceImpl.viewCompanySummaryDetails(123L));
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId(any());
    }
}