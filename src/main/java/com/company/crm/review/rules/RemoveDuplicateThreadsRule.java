package com.company.crm.review.rules;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.exception.ApplicationActivityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.crm.review.exception.ApplicationErrorMessages.PERSIST_DUPLICATE_RULE_EXECUTION_ERROR;

@Service
@Slf4j
public class RemoveDuplicateThreadsRule implements BusinessRules<CompanyDto> {

    /**
     * Removes any duplicate threads (i.e., those with matching payloads) per conversation within each company.
     */

    @Override
    public void execute(List<CompanyDto> companies) {
        try {
            companies.forEach(company -> company.getConversations().forEach(conversation -> {
                Map<String, CompanyDto.ConversationDto.ThreadDto> stringThreadEntry = new HashMap<>();
                conversation.getThreads().forEach(thread -> {
                    if (stringThreadEntry.containsKey(thread.getPayload())) { //check for a duplicate entry
                        conversation.getDuplicateThreads().add(thread);
                    } else {
                        stringThreadEntry.put(thread.getPayload(), thread);
                    }
                });
                conversation.setThreads(new ArrayList<>());
                conversation.getThreads().addAll(stringThreadEntry.values());
            }));
        }catch (RuntimeException exception){
            log.error("Error  removing duplicate threads, actual exception message is : {}", exception.getMessage(), exception);
            throw new ApplicationActivityException(PERSIST_DUPLICATE_RULE_EXECUTION_ERROR, HttpStatus.BAD_REQUEST); //could be as a result of bad data, TODO:// implement validations on DTO members
        }

    }

    @Override
    public BusinessRules.BusinessRule getBusinessRule() {
        return BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE;
    }
}
