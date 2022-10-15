package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.exception.ApplicationActivityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ContextConfiguration(classes = {FilterCompanyBySignUpDateWithinRule.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"crm.rule.filterSignUpDateFrom = 2017-12-31T23:59:58", "crm.rule.filterSignUpDateTo =2018-11-01T00:00:00"})
class FilterCompanyBySignUpDateWithinRuleTest {

    static Conversation sampleConversation;

    static {
        sampleConversation = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(333L)
                .threads(Collections.emptySet())
                .build();
    }

    @Autowired
    private FilterCompanyBySignUpDateWithinRule filterCompanyBySignUpDateWithinRule;

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testRemoveCompaniesIfSignedUpDateIsWithinDateFromAndDateToRule() {

        Company company1 = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company xyz")
                .signedUp(LocalDateTime.of(2018, 10, 30, 0, 0, 0))
                .conversations(Set.of(sampleConversation))
                .build();
        Company company2 = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company abc")
                .signedUp(LocalDateTime.of(2017, 12, 31, 23, 59, 59))
                .conversations(Set.of(sampleConversation))
                .build();
        List<Company> companyList = new ArrayList<>(List.of(company1, company2));
        filterCompanyBySignUpDateWithinRule.execute(companyList);
        assertThat(companyList).isEmpty();
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testRemoveOneCompanyIfSignedUpDateIsWithinDateFromAndDateToRule() {
        Company company1 = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company xyz")
                .signedUp(LocalDateTime.of(2018, 10, 30, 0, 0, 0))
                .conversations(Set.of(sampleConversation))
                .build();
        Company company2 = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company abc")
                .signedUp(LocalDateTime.of(2019, 10, 30, 0, 0, 0))
                .conversations(Collections.emptySet())
                .build();
        List<Company> companyList = new ArrayList<>(List.of(company1, company2));
        filterCompanyBySignUpDateWithinRule.execute(companyList);

        assertThat(1).isEqualTo(companyList.size());
        assertThat("company abc").isEqualTo(companyList.get(0).getName());
    }

    @Test
    void testDoNotRemoveCompanyIfSignedUpDateIsNotWithinDateFromAndDateToRule() {
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company xyz")
                .signedUp(LocalDateTime.of(2019, 10, 30, 0, 0, 0))
                .conversations(Set.of(sampleConversation))
                .build();
        List<Company> companyList = new ArrayList<>(Collections.singletonList(company));
        filterCompanyBySignUpDateWithinRule.execute(companyList);

        assertThat(companyList).isNotEmpty();
    }

    /**
     * Method under test: {@link FilterCompanyBySignUpDateWithinRule#execute(List)}
     */
    @Test
    void testApplicationThrowsExceptionIfCompanyListIsEmpty() {
        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        assertThatThrownBy(() -> filterCompanyBySignUpDateWithinRule
                .execute(companyList))
                .isInstanceOf(ApplicationActivityException.class);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link FilterCompanyBySignUpDateWithinRule}
     *   <li>{@link FilterCompanyBySignUpDateWithinRule#getBusinessRule()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertThat(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE).isEqualTo(new FilterCompanyBySignUpDateWithinRule().getBusinessRule());
    }
}

