package com.company.crm.rules;

import com.company.crm.entity.Company;
import com.company.crm.entity.Conversation;
import com.company.crm.entity.DuplicateThread;
import com.company.crm.entity.Thread;
import com.company.crm.exception.ApplicationActivityException;
import com.company.crm.exception.ApplicationErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class RemoveDuplicateThreadsRule implements BusinessRules<Company> {

    /**
     * Removes any duplicate threads (i.e., those with matching payloads) per conversation within each company.
     * Map each duplicate thread {@link DuplicateThread} to its parent conversation{@link Conversation}.
     */

    @Override
    public void execute(List<Company> companies) {
        try {
            companies.forEach(company -> company.getConversations().forEach(conversation -> {
                Map<String, Thread> stringThreadEntry = new HashMap<>();
                Map<String, DuplicateThread> duplicateThreadEntry = new HashMap<>();

                conversation.getThreads().forEach(thread -> {
                    if (stringThreadEntry.containsKey(thread.getPayload())) { //check for a duplicate entry
                        duplicateThreadEntry.put(thread.getPayload(), DuplicateThread.builder()
                                .id(thread.getId())
                                .payload(thread.getPayload())
                                .build());
                    } else {
                        stringThreadEntry.put(thread.getPayload(), thread);
                    }
                });
                if (!duplicateThreadEntry.isEmpty()) {
                    conversation.setThreads(new HashSet<>());
                    conversation.getDuplicateThreads().addAll(duplicateThreadEntry.values());
                    conversation.getThreads().addAll(stringThreadEntry.values());
                }
            }));
        } catch (RuntimeException exception) {
            log.error("Error  removing duplicate threads, actual exception message is : {}", exception.getMessage(), exception);
            throw new ApplicationActivityException(ApplicationErrorMessages.PERSIST_DUPLICATE_RULE_EXECUTION_ERROR, HttpStatus.BAD_REQUEST); //could be as a result of bad data, TODO:// implement validations on DTO members
        }
    }

    @Override
    public BusinessRules.BusinessRule getBusinessRule() {
        return BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE;
    }
}
