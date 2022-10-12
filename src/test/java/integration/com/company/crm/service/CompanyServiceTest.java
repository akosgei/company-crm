package com.company.crm.service;

import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import com.company.crm.exception.ApplicationActivityException;
import com.company.crm.exception.ApplicationErrorMessages;
import com.company.crm.repository.CompanyRepository;
import com.company.crm.rules.BusinessRules;
import com.company.crm.rules.FilterCompanyBySignUpDateWithinRule;
import com.company.crm.rules.configuration.BusinessRulesConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CompanyService.class})
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {
    @MockBean
    private BusinessRulesConfiguration businessRulesConfiguration;

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    /**
     * Method under test: {@link CompanyService#save(List)}
     */
    @Test
    void testSave() {
        assertThrows(ApplicationActivityException.class, () -> companyService.save(new ArrayList<>()));
    }

    /**
     * Method under test: {@link CompanyService#save(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.company.crm.exception.ApplicationActivityException
        //       at com.company.crm.rules.FilterCompanyBySignUpDateWithinRule.execute(FilterCompanyBySignUpDateWithinRule.java:38)
        //       at com.company.crm.service.CompanyService.lambda$executeBusinessRules$0(CompanyService.java:45)
        //       at java.util.LinkedHashMap.forEach(LinkedHashMap.java:684)
        //       at com.company.crm.service.CompanyService.executeBusinessRules(CompanyService.java:40)
        //       at com.company.crm.service.CompanyService.save(CompanyService.java:56)
        //   In order to prevent save(List)
        //   from throwing ApplicationActivityException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   save(List).
        //   See https://diff.blue/R013 to resolve this issue.

        when(companyRepository.saveAll((Iterable<Company>) any())).thenReturn((Iterable<Company>) mock(Iterable.class));
        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        when(businessRulesConfiguration.findBusinessRule((BusinessRules.BusinessRule) any()))
                .thenReturn(new FilterCompanyBySignUpDateWithinRule(startDate, LocalDateTime.of(1, 1, 1, 1, 1)));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        companyService.save(companyList);
    }

    /**
     * Method under test: {@link CompanyService#save(List)}
     */
    @Test
    void testSave3() {
        when(companyRepository.saveAll((Iterable<Company>) any())).thenReturn((Iterable<Company>) mock(Iterable.class));
        when(businessRulesConfiguration.findBusinessRule((BusinessRules.BusinessRule) any()))
                .thenThrow(new ApplicationActivityException(ApplicationErrorMessages.COMPANY_NOT_FOUND, HttpStatus.CONTINUE));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        assertThrows(ApplicationActivityException.class, () -> companyService.save(companyList));
        verify(businessRulesConfiguration).findBusinessRule((BusinessRules.BusinessRule) any());
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    void testViewCompanySummary() {
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(ApplicationActivityException.class, () -> companyService.viewCompanySummary(123L));
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId((Long) any());
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testViewCompanySummary2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: element cannot be mapped to a null key
        //       at java.util.Objects.requireNonNull(Objects.java:246)
        //       at java.util.stream.Collectors.lambda$groupingBy$53(Collectors.java:1127)
        //       at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1655)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:913)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:578)
        //       at com.company.crm.service.CompanyService.viewCompanySummary(CompanyService.java:69)
        //   In order to prevent viewCompanySummary(Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   viewCompanySummary(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(new CompanySummaryDto());
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenReturn(companySummaryDtoList);
        companyService.viewCompanySummary(123L);
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    void testViewCompanySummary3() {
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenThrow(new ApplicationActivityException(ApplicationErrorMessages.COMPANY_NOT_FOUND, HttpStatus.CONTINUE));
        assertThrows(ApplicationActivityException.class, () -> companyService.viewCompanySummary(123L));
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId((Long) any());
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testViewCompanySummary4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NumberFormatException: For input string: "Most Popular User"
        //       at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
        //       at java.lang.Integer.parseInt(Integer.java:652)
        //       at java.lang.Integer.valueOf(Integer.java:983)
        //       at com.company.crm.service.CompanyService.lambda$viewCompanySummary$1(CompanyService.java:69)
        //       at java.util.stream.Collectors.lambda$mapping$13(Collectors.java:461)
        //       at java.util.stream.Collectors.lambda$groupingBy$53(Collectors.java:1129)
        //       at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1655)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:913)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:578)
        //       at com.company.crm.service.CompanyService.viewCompanySummary(CompanyService.java:69)
        //   In order to prevent viewCompanySummary(Long)
        //   from throwing NumberFormatException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   viewCompanySummary(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(new CompanySummaryDto("Company Name", 3, "Most Popular User"));
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenReturn(companySummaryDtoList);
        companyService.viewCompanySummary(123L);
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testViewCompanySummary5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NumberFormatException: For input string: "Most Popular User"
        //       at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
        //       at java.lang.Integer.parseInt(Integer.java:652)
        //       at java.lang.Integer.valueOf(Integer.java:983)
        //       at com.company.crm.service.CompanyService.lambda$viewCompanySummary$1(CompanyService.java:69)
        //       at java.util.stream.Collectors.lambda$mapping$13(Collectors.java:461)
        //       at java.util.stream.Collectors.lambda$groupingBy$53(Collectors.java:1129)
        //       at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1655)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:913)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:578)
        //       at com.company.crm.service.CompanyService.viewCompanySummary(CompanyService.java:69)
        //   In order to prevent viewCompanySummary(Long)
        //   from throwing NumberFormatException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   viewCompanySummary(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        CompanySummaryDto companySummaryDto = mock(CompanySummaryDto.class);
        when(companySummaryDto.getThreadCount()).thenReturn(3);
        when(companySummaryDto.getMostPopularUser()).thenReturn("Most Popular User");

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(companySummaryDto);
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenReturn(companySummaryDtoList);
        companyService.viewCompanySummary(123L);
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    void testViewCompanySummary6() {
        CompanySummaryDto companySummaryDto = mock(CompanySummaryDto.class);
        when(companySummaryDto.getCompanyName()).thenReturn("Company Name");
        when(companySummaryDto.getThreadCount()).thenReturn(3);
        when(companySummaryDto.getMostPopularUser()).thenReturn("42");

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(companySummaryDto);
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenReturn(companySummaryDtoList);
        CompanySummaryDto actualViewCompanySummaryResult = companyService.viewCompanySummary(123L);
        assertEquals("Company Name", actualViewCompanySummaryResult.getCompanyName());
        assertEquals(1, actualViewCompanySummaryResult.getThreadCount().intValue());
        assertEquals("42", actualViewCompanySummaryResult.getMostPopularUser());
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId((Long) any());
        verify(companySummaryDto).getThreadCount();
        verify(companySummaryDto).getCompanyName();
        verify(companySummaryDto).getMostPopularUser();
    }

    /**
     * Method under test: {@link CompanyService#viewCompanySummary(Long)}
     */
    @Test
    void testViewCompanySummary7() {
        CompanySummaryDto companySummaryDto = mock(CompanySummaryDto.class);
        when(companySummaryDto.getCompanyName())
                .thenThrow(new ApplicationActivityException(ApplicationErrorMessages.COMPANY_NOT_FOUND, HttpStatus.CONTINUE));
        when(companySummaryDto.getThreadCount()).thenReturn(3);
        when(companySummaryDto.getMostPopularUser()).thenReturn("42");

        ArrayList<CompanySummaryDto> companySummaryDtoList = new ArrayList<>();
        companySummaryDtoList.add(companySummaryDto);
        when(companyRepository.retrieveCompanyConversationSummaryByCompanyId((Long) any()))
                .thenReturn(companySummaryDtoList);
        assertThrows(ApplicationActivityException.class, () -> companyService.viewCompanySummary(123L));
        verify(companyRepository).retrieveCompanyConversationSummaryByCompanyId((Long) any());
        verify(companySummaryDto).getThreadCount();
        verify(companySummaryDto).getCompanyName();
        verify(companySummaryDto).getMostPopularUser();
    }
}

