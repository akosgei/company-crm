package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.entity.Thread;
import com.company.crm.exception.ApplicationActivityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RemoveDuplicateThreadsRule.class})
@ExtendWith(SpringExtension.class)
class RemoveDuplicateThreadsRuleTest {
    @Autowired
    private RemoveDuplicateThreadsRule removeDuplicateThreadsRule;

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by execute(List)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        removeDuplicateThreadsRule.execute(new ArrayList<>());
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute2() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by execute(List)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        removeDuplicateThreadsRule.execute(companyList);
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute3() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by execute(List)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company());
        companyList.add(new Company());
        removeDuplicateThreadsRule.execute(companyList);
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute4() {
        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(null);
        assertThrows(ApplicationActivityException.class, () -> removeDuplicateThreadsRule.execute(companyList));
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute5() {
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(new HashSet<>());

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute6() {
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

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute7() {
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

        Conversation conversation1 = new Conversation();
        conversation1.setCompany(new Company());
        conversation1.setConversationId(123L);
        conversation1.setDuplicateThreads(new HashSet<>());
        conversation1.setFrom("jane.doe@example.org");
        conversation1.setId(123L);
        conversation1.setNumber(1L);
        conversation1.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation1.setThreads(new HashSet<>());
        conversation1.setUserId(123L);

        HashSet<Conversation> conversationSet = new HashSet<>();
        conversationSet.add(conversation1);
        conversationSet.add(conversation);
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(conversationSet);

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute8() {
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

        Thread thread = new Thread();
        thread.setConversation(conversation);
        thread.setId(123L);
        thread.setPayload("Payload");
        thread.setThreadId(123L);

        HashSet<Thread> threadSet = new HashSet<>();
        threadSet.add(thread);

        Conversation conversation1 = new Conversation();
        conversation1.setCompany(new Company());
        conversation1.setConversationId(123L);
        conversation1.setDuplicateThreads(new HashSet<>());
        conversation1.setFrom("jane.doe@example.org");
        conversation1.setId(123L);
        conversation1.setNumber(1L);
        conversation1.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation1.setThreads(threadSet);
        conversation1.setUserId(123L);

        HashSet<Conversation> conversationSet = new HashSet<>();
        conversationSet.add(conversation1);
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(conversationSet);

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute9() {
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

        Thread thread = new Thread();
        thread.setConversation(conversation);
        thread.setId(123L);
        thread.setPayload("Payload");
        thread.setThreadId(123L);

        Conversation conversation1 = new Conversation();
        conversation1.setCompany(new Company());
        conversation1.setConversationId(123L);
        conversation1.setDuplicateThreads(new HashSet<>());
        conversation1.setFrom("jane.doe@example.org");
        conversation1.setId(123L);
        conversation1.setNumber(1L);
        conversation1.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation1.setThreads(new HashSet<>());
        conversation1.setUserId(123L);

        Thread thread1 = new Thread();
        thread1.setConversation(conversation1);
        thread1.setId(123L);
        thread1.setPayload("Payload");
        thread1.setThreadId(123L);

        HashSet<Thread> threadSet = new HashSet<>();
        threadSet.add(thread1);
        threadSet.add(thread);

        Conversation conversation2 = new Conversation();
        conversation2.setCompany(new Company());
        conversation2.setConversationId(123L);
        conversation2.setDuplicateThreads(new HashSet<>());
        conversation2.setFrom("jane.doe@example.org");
        conversation2.setId(123L);
        conversation2.setNumber(1L);
        conversation2.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation2.setThreads(threadSet);
        conversation2.setUserId(123L);

        HashSet<Conversation> conversationSet = new HashSet<>();
        conversationSet.add(conversation2);
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(conversationSet);

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Method under test: {@link RemoveDuplicateThreadsRule#execute(List)}
     */
    @Test
    void testExecute10() {
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

        Thread thread = new Thread();
        thread.setConversation(conversation);
        thread.setId(123L);
        thread.setPayload("Payload");
        thread.setThreadId(123L);

        Conversation conversation1 = new Conversation();
        conversation1.setCompany(new Company());
        conversation1.setConversationId(123L);
        conversation1.setDuplicateThreads(new HashSet<>());
        conversation1.setFrom("jane.doe@example.org");
        conversation1.setId(123L);
        conversation1.setNumber(1L);
        conversation1.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation1.setThreads(new HashSet<>());
        conversation1.setUserId(123L);

        Thread thread1 = new Thread();
        thread1.setConversation(conversation1);
        thread1.setId(123L);
        thread1.setPayload("Payload");
        thread1.setThreadId(123L);

        Conversation conversation2 = new Conversation();
        conversation2.setCompany(new Company());
        conversation2.setConversationId(123L);
        conversation2.setDuplicateThreads(new HashSet<>());
        conversation2.setFrom("jane.doe@example.org");
        conversation2.setId(123L);
        conversation2.setNumber(1L);
        conversation2.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation2.setThreads(new HashSet<>());
        conversation2.setUserId(123L);

        Thread thread2 = new Thread();
        thread2.setConversation(conversation2);
        thread2.setId(123L);
        thread2.setPayload("Payload");
        thread2.setThreadId(123L);

        HashSet<Thread> threadSet = new HashSet<>();
        threadSet.add(thread2);
        threadSet.add(thread1);
        threadSet.add(thread);

        Conversation conversation3 = new Conversation();
        conversation3.setCompany(new Company());
        conversation3.setConversationId(123L);
        conversation3.setDuplicateThreads(new HashSet<>());
        conversation3.setFrom("jane.doe@example.org");
        conversation3.setId(123L);
        conversation3.setNumber(1L);
        conversation3.setReceived(LocalDateTime.of(1, 1, 1, 1, 1));
        conversation3.setThreads(threadSet);
        conversation3.setUserId(123L);

        HashSet<Conversation> conversationSet = new HashSet<>();
        conversationSet.add(conversation3);
        Company company = mock(Company.class);
        when(company.getConversations()).thenReturn(conversationSet);

        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(company);
        removeDuplicateThreadsRule.execute(companyList);
        verify(company).getConversations();
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link RemoveDuplicateThreadsRule}
     *   <li>{@link RemoveDuplicateThreadsRule#getBusinessRule()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertEquals(BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE,
                (new RemoveDuplicateThreadsRule()).getBusinessRule());
    }
}

