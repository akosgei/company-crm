package com.company.crm.service;


import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import com.company.crm.exception.ApplicationActivityException;
import com.company.crm.exception.ApplicationErrorMessages;
import com.company.crm.repository.CompanyRepository;
import com.company.crm.rules.BusinessRules;
import com.company.crm.rules.configuration.BusinessRulesConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    final Map<BusinessRules.BusinessRule, List<Company>> rulesToExecute = new HashMap<>();

    private final CompanyRepository companyRepository;
    private final BusinessRulesConfiguration rulesConfiguration;


    private void executeBusinessRules(List<Company> companies) {
        {
            rulesToExecute.put(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE, companies);
            rulesToExecute.put(BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE, companies);
        }

        rulesToExecute.forEach((businessRule, payload) -> {
            BusinessRules<Company> rule = rulesConfiguration.findBusinessRule(businessRule);
            rule.execute(payload);
        });
    }

    @Override
    public void save(List<Company> companies) {
        if (companies.isEmpty()) {
            log.error("Supplied payload (list of companies) is empty");
            throw new ApplicationActivityException(ApplicationErrorMessages.EMPTY_PAYLOAD_SUPPLIED, HttpStatus.BAD_REQUEST);
        }

        executeBusinessRules(companies);
        companyRepository.saveAll(companies);
    }


    @Override
    public CompanySummaryDto viewCompanySummaryDetails(Long companyId) {
        List<CompanySummaryDto> companySummaryResult = companyRepository.retrieveCompanyConversationSummaryByCompanyId(companyId);
        if (companySummaryResult.isEmpty()) {
            log.error("Company with supplied id, {} does not exist.", companyId);
            throw new ApplicationActivityException(ApplicationErrorMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        /**
         * we create a map of <countOfConversationsPerUser, List<ThepopularUser>>
         */
        Map<Integer, List<Integer>> threadToUserMap = companySummaryResult.stream()
                .collect(groupingBy(CompanySummaryDto::getConversationCount, mapping((CompanySummaryDto companySummaryDto) -> Integer.valueOf(companySummaryDto.getMostPopularUser()), toList())));

        /**
         From the map above, we iterate to compare which map entry has the highest value,
         there is a possibility that it could be more than one use, hence storing the results of this computation in a list
         */
        List<Integer> popularUser = threadToUserMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new ApplicationActivityException(ApplicationErrorMessages.DATA_INTEGRITY_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
        /**
         * We construct the response payload, most popular user would contain more than one user, hence the joining them in a string with a comma separator
         */
        return CompanySummaryDto.builder()
                .companyName(companySummaryResult.get(0).getCompanyName()) // the company will always be the same
                .conversationCount(companySummaryResult.size())
                .mostPopularUser(popularUser.stream().map(Object::toString).collect(joining(","))).build();
    }
}