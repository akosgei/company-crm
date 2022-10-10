package com.helpscout.review.rules;

import com.helpscout.review.entity.Company;
import com.helpscout.review.entity.DuplicateThread;
import com.helpscout.review.entity.Thread;
import com.helpscout.review.exception.ApplicationActivityException;
import com.helpscout.review.util.ModelMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.helpscout.review.exception.ApplicationErrorMessages.PERSIST_DUPLICATE_RULE_EXECUTION_ERROR;


@Service
@Slf4j
public class RemoveDuplicateThreadsRule implements BusinessRules<Company> {

    /**
     * Removes any duplicate threads (i.e., those with matching payloads) per conversation within each company.
     * Map each duplicate thread {@link DuplicateThread} to its parent conversation{@link com.helpscout.review.entity.Conversation}.
     */

    @Override
    public void execute(List<Company> companies) {
        try {
            companies.forEach(company -> company.getConversations().forEach(conversation -> {
                Map<String, Thread> stringThreadEntry = new HashMap<>();
                Map<String, DuplicateThread> duplicateThreadEntry = new HashMap<>();

                conversation.getThreads().forEach(thread -> {
                    if (stringThreadEntry.containsKey(thread.getPayload())) { //check for a duplicate entry
                        duplicateThreadEntry.put(thread.getPayload(), ModelMapperUtil.convertDtoToEntity(thread, DuplicateThread.class));
                    } else {
                        stringThreadEntry.put(thread.getPayload(), thread);
                    }
                });
                conversation.getDuplicateThreads().addAll(duplicateThreadEntry.values());
                conversation.getThreads().addAll(stringThreadEntry.values());
            }));
        } catch (RuntimeException exception) {
            log.error("Error  removing duplicate threads, actual exception message is : {}", exception.getMessage(), exception);
            throw new ApplicationActivityException(PERSIST_DUPLICATE_RULE_EXECUTION_ERROR, HttpStatus.BAD_REQUEST); //could be as a result of bad data, TODO:// implement validations on DTO members
        }
    }

    @Override
    public BusinessRules.BusinessRule getBusinessRule() {
        return BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE;
    }
}
