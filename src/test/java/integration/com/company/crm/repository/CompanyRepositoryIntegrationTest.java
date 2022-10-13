package com.company.crm.repository;

import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.entity.Thread;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJdbcTest
public class CompanyRepositoryIntegrationTest {

    @Autowired
    CompanyRepository repositoryUnderTest;

    @Test
    public void testCompanyWithValidCompanyId() {
        //given
        Thread thread1 = Thread.builder()
                .id(22563L)
                .payload("some payload one")
                .build();

        Thread thread2 = Thread.builder()
                .id(9185L)
                .payload("some payload two")
                .build();
        Thread thread3 = Thread.builder()
                .id(1367L)
                .payload("some payload three")
                .build();

        Conversation conversation1 = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(333L)
                .threads(Set.of(thread1, thread2, thread3))
                .build();

        Conversation conversation2 = Conversation.builder()
                .id(2222L)
                .from("demo_two@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(444L)
                .threads(Set.of(thread1))
                .build();

        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Set.of(conversation1, conversation2))
                .build();

        repositoryUnderTest.save(company);

        //when
        List<CompanySummaryDto> response = repositoryUnderTest.retrieveCompanyConversationSummaryByCompanyId(1L);

        //then assert
        assertThat(2).isEqualTo(response.size());
        assertThat(response.get(0).getThreadCount()).isEqualTo(3);
        assertThat(response.get(0).getMostPopularUser()).isEqualTo("333");
        assertThat(response.get(1).getThreadCount()).isEqualTo(1);
    }

    @Test
    public void testCompanyWithInvalidCompanyId() {
        //given
        Thread thread1 = Thread.builder()
                .id(22563L)
                .payload("some payload one")
                .build();
        Conversation conversation1 = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(333L)
                .threads(Set.of(thread1))
                .build();
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Set.of(conversation1))
                .build();
        repositoryUnderTest.save(company);
        //when
        List<CompanySummaryDto> response = repositoryUnderTest.retrieveCompanyConversationSummaryByCompanyId(1234L);
        //then
        assertThat(response).isEmpty();
    }

    @Test
    public void testCompanyWithoutConversation() {
        //given
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Collections.emptySet())
                .build();
        repositoryUnderTest.save(company);

        //when
        List<CompanySummaryDto> response = repositoryUnderTest.retrieveCompanyConversationSummaryByCompanyId(1L);
        //then
        assertThat(1).isEqualTo(response.size());
        assertThat(0).isEqualTo(response.get(0).getThreadCount());
        assertThat(response.get(0).getMostPopularUser()).isNullOrEmpty();
    }

    @Test
    public void testCompanyConversationWithoutThreads() {
        //given
        Conversation conversation = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(1964L)
                .userId(333L)
                .threads(Collections.emptySet())
                .build();

        Company company = com.company.crm.entity.Company.builder()
                .id(222L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Set.of(conversation))
                .build();
        repositoryUnderTest.save(company);

        //when
        List<CompanySummaryDto> response = repositoryUnderTest.retrieveCompanyConversationSummaryByCompanyId(1L);
        //then
        assertThat(1).isEqualTo(response.size());
        assertThat(1).isEqualTo(response.get(0).getThreadCount());
        assertThat(response.get(0).getMostPopularUser()).isEqualTo("333");
    }
}