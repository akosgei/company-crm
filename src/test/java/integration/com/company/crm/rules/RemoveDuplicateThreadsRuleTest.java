package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.entity.Thread;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {RemoveDuplicateThreadsRule.class})
@ExtendWith(SpringExtension.class)
class RemoveDuplicateThreadsRuleTest {

    @Autowired
    private RemoveDuplicateThreadsRule removeDuplicateThreadsRule;

    /**
     * given a valid company list with conversations containing duplicate conversations, the duplicate conversations should be removed.
     * given a valid company list with conversations containing non-duplicate threads, the list should be returned as it.
     * given a valid company list with no conversations , the same company list should be returned as is.
     */

    @Test
    public void testRemoveDuplicateThreadAndPopulateDuplicateThreadsCollection() {
        //given
        Thread thread_one = Thread.builder()
                .id(123L)
                .payload("some identical payload")
                .build();
        Thread thread_two = Thread.builder()
                .id(345L)
                .payload("some identical payload")
                .build();

        Conversation conversation = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(555L)
                .userId(777L)
                .threads(new HashSet<>(List.of(thread_one, thread_two)))
                .build();
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Set.of(conversation))
                .build();
        List<Company> companies = new ArrayList<>(List.of(company));

        removeDuplicateThreadsRule.execute(companies);
        assertThat(companies.get(0).getConversations().stream().findFirst().get().getDuplicateThreads().size()).isEqualTo(1);
        assertThat(companies.get(0).getConversations().stream().findFirst().get().getThreads().size()).isEqualTo(1);
    }

    @Test
    public void testNoDuplicateThreadsConversationListIsNotChanged() {
        //given
        Thread thread_one = Thread.builder()
                .id(123L)
                .payload("some identical payload")
                .build();
        Thread thread_two = Thread.builder()
                .id(345L)
                .payload("some non identical payload")
                .build();

        Conversation conversation = Conversation.builder()
                .id(111L)
                .from("demo_one@mail.com")
                .received(LocalDateTime.now())
                .number(555L)
                .userId(777L)
                .threads(new HashSet<>(List.of(thread_one, thread_two)))
                .build();
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .conversations(Set.of(conversation))
                .build();
        List<Company> companies = new ArrayList<>(List.of(company));
        //when
        removeDuplicateThreadsRule.execute(companies);
        //then
        assertThat(companies.get(0).getConversations().stream().findFirst().get().getDuplicateThreads().size()).isEqualTo(0);
        assertThat(companies.get(0).getConversations().stream().findFirst().get().getThreads().size()).isEqualTo(2);
    }

    @Test
    public void testCompanyWithNoConversationsListIsNotChanged() {
        Company company = com.company.crm.entity.Company.builder()
                .id(1L)
                .name("companyXyz")
                .signedUp(LocalDateTime.now())
                .build();
        List<Company> companies = new ArrayList<>(List.of(company));
        removeDuplicateThreadsRule.execute(companies);
        assertThat(companies.get(0).getConversations().stream().findFirst().isEmpty()).isEqualTo(true);
        assertThat(companies.get(0).getConversations().stream().findFirst().isEmpty()).isEqualTo(true);
    }
}