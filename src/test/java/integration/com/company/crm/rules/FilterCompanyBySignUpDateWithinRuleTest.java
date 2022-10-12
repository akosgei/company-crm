package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.exception.ApplicationActivityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterCompanyBySignUpDateWithinRuleTest {

    LocalDateTime startDate;
    LocalDateTime endDate;

    @BeforeEach
    public void init() {
        startDate = LocalDateTime.of(2020, 1, 10, 1, 0);
        endDate = LocalDateTime.of(2020, 5, 10, 1, 0);
    }

    @Test
    void testWhenNoCompanyListProvidedEmptyListIsReturned() {

        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, endDate);
        ArrayList<Company> companyList = new ArrayList<>();
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        assertTrue(companyList.isEmpty());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute2() {
        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        assertThrows(ApplicationActivityException.class, () -> filterCompanyBySignUpDateWithinRule.execute(companyList));
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute3() {
        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        LocalDateTime signedUp = LocalDateTime.of(1, 1, 1, 1, 1);
        companyList.add(
                new Company(123L, 123L, "Error filtering records by date within date range, actual exception message is : {}",
                        signedUp, new HashSet<>()));
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        assertEquals(1, companyList.size());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(null);
        assertThrows(ApplicationActivityException.class, () -> filterCompanyBySignUpDateWithinRule.execute(companyList));
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1));
        Company company = mock(Company.class);
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        verify(company).getSignedUp();
        assertEquals(1, companyList.size());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1, 1));
        Company company = mock(Company.class);
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        verify(company, atLeast(1)).getSignedUp();
        assertEquals(1, companyList.size());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(0, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1, 1));
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(new HashSet<>());
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        verify(company, atLeast(1)).getSignedUp();
        verify(company).getConversations();
        assertTrue(companyList.isEmpty());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute8() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                null, LocalDateTime.of(1, 1, 1, 1, 1, 1));
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(new HashSet<>());
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        assertThrows(ApplicationActivityException.class, () -> filterCompanyBySignUpDateWithinRule.execute(companyList));
        verify(company, atLeast(1)).getSignedUp();
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute9() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(0, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1, 1));

        Conversation conversation = new Conversation();
        conversation.setCompany(new Company());
        conversation.setConversationId(123L);
        conversation.setDuplicateThreads(new HashSet<>());
        conversation.setFrom("jane.doe@example.org");
        conversation.setId(123L);
        conversation.setNumber(1L);
        conversation.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation.setThreads(new HashSet<>());
        conversation.setUserId(123L);

        HashSet<Conversation> conversationSet = new HashSet<>();
        conversationSet.add(conversation);
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(conversationSet);
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        verify(company, atLeast(1)).getSignedUp();
        verify(company).getConversations();
        assertEquals(1, companyList.size());
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testExecute10() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: F009 Internal error.
        //   java.lang.IllegalArgumentException: SpringDependencyInjectionContext: No bean found for type: com.company.crm.rules.FilterCompanyBySignUpDateWithinRule
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        LocalDateTime startDate = LocalDateTime.of(0, 1, 1, 1, 1);
        FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(
                startDate, LocalDateTime.of(1, 1, 1, 1, 1, 1));
        Company company = mock(Company.class);
        when(company.getConversations()).thenThrow(new RuntimeException("An error occurred"));
        when(company.getSignedUp()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        assertThrows(ApplicationActivityException.class, () -> filterCompanyBySignUpDateWithinRule.execute(companyList));
        verify(company, atLeast(1)).getSignedUp();
        verify(company).getConversations();
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FilterCompanyBySignUpDateWithinRule#FilterCompanyBySignUpDateWithinRule(LocalDateTime, LocalDateTime)}
     *   <li>{@link FilterCompanyBySignUpDateWithinRule#getBusinessRule()}
     * </ul>
     */
    @Test
    void testConstructor() {
        LocalDateTime startDate = LocalDateTime.of(1, 1, 1, 1, 1);
        assertEquals(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE,
                (new FilterCompanyBySignUpDateWithinRule(startDate, LocalDateTime.of(1, 1, 1, 1, 1))).getBusinessRule());
    }
}

