package com.company.crm.review.rules;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.entity.DuplicateThread;
import com.company.crm.review.exception.ApplicationActivityException;
import com.company.crm.review.repository.DuplicateThreadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.company.crm.review.exception.ApplicationErrorMessages.PERSIST_DUPLICATE_RULE_EXECUTION_ERROR;

@Service
@Slf4j
public class PersistDuplicateThreadsRule implements BusinessRules<CompanyDto> {

    private final DuplicateThreadRepository duplicateThreadRepository;

    public PersistDuplicateThreadsRule(DuplicateThreadRepository duplicateThreadRepository) {
        this.duplicateThreadRepository = duplicateThreadRepository;
    }

    @Override
    public void execute(List<CompanyDto> companies) {
        List<DuplicateThread> duplicateThreadEntityList = new ArrayList<>();
        try {
            companies.forEach(company -> company.getConversations().forEach(conversationDto -> {
                if (conversationDto.getDuplicateThreads().size() > 0) {
                    conversationDto.getDuplicateThreads().forEach(dupThread -> {
                        DuplicateThread duplicateThread = new DuplicateThread();
                        duplicateThread.setId(dupThread.getId());
                        duplicateThread.setPayload(dupThread.getPayload());
                        duplicateThread.setConversationId(conversationDto.getId());

                        duplicateThreadEntityList.add(duplicateThread);
                    });
                }
            }));

            duplicateThreadRepository.saveAll(duplicateThreadEntityList);
        } catch (RuntimeException exception) {
            log.error("Error persisting processing data for duplicate threads records, actual exception message is: {}", exception.getMessage(), exception);
            throw new ApplicationActivityException(PERSIST_DUPLICATE_RULE_EXECUTION_ERROR, HttpStatus.BAD_REQUEST); //could be as a result of bad data, TODO:// implement validations on DTO members
        }
    }

    @Override
    public BusinessRule getBusinessRule() {
        return BusinessRule.PERSIST_DUPLICATE_THREADS_RULE;
    }
}