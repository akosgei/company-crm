package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.exception.ApplicationActivityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * So, how should you name a test [unit or integration] test? I’ve seen and tried a lot of naming conventions over the past decade. One of the most prominent, and probably least helpful, is
 * the following convention:
 * [MethodUnderTest]_[Scenario]_[ExpectedResult]
 */

/**
 * Replace the next two lines with {@Link }
 * <code>
 *   @SpringJunitConfig
 * </code>
 */
@SpringJUnitConfig(FilterCompanyBySignUpDateWithinRule.class)
@TestPropertySource(locations = "classpath:application.yml")
class FilterCompanyBySignUpDateWithinRuleTest {

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
                .conversations(Set.of(getSampleConversation()))
                .build();
        Company company2 = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("company abc")
                .signedUp(LocalDateTime.of(2017, 12, 31, 23, 59, 59))
                .conversations(Set.of(getSampleConversation()))
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
                .conversations(Set.of(getSampleConversation()))
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
                .conversations(Set.of(getSampleConversation()))
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

    /**
     * We initialize the Conversation object to be reused by multiple tests because the tests under control are determined by the presence of a conversation instance or not.
     *
     * @return {@link Conversation} instance
     */
    private static Conversation getSampleConversation() {
        return Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(333L)
                .threads(Collections.emptySet())
                .build();
    }
}

