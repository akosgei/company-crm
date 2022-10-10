package com.helpscout.review.service;


import com.helpscout.review.dto.CompanySummaryDto;
import com.helpscout.review.entity.Company;
import com.helpscout.review.exception.ApplicationActivityException;
import com.helpscout.review.exception.ApplicationErrorMessages;
import com.helpscout.review.repository.CompanyRepository;
import com.helpscout.review.rules.BusinessRules;
import com.helpscout.review.rules.configuration.BusinessRulesConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    final Map<BusinessRules.BusinessRule, List<Company>> rulesToExecute = new LinkedHashMap<>();

    private final CompanyRepository companyRepository;
    private final BusinessRulesConfiguration rulesConfiguration;


    private void executeBusinessRules(List<Company> companies) {
        {
            rulesToExecute.put(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE, companies);
            rulesToExecute.put(BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE, companies);
            rulesToExecute.put(BusinessRules.BusinessRule.PERSIST_DUPLICATE_THREADS_RULE, companies);
        }

        rulesToExecute.forEach((businessRule, payload) -> {
            BusinessRules<Company> rule = rulesConfiguration.findBusinessRule(businessRule);
            if (rule == null) {
                return;
            }
            rule.execute(payload);
        });
    }

    @Override
    public List<Company> save(List<Company> companies) {
        if (companies.isEmpty()) {
            log.error("Supplied payload (list of companies) is empty");
            throw new ApplicationActivityException(ApplicationErrorMessages.EMPTY_PAYLOAD_SUPPLIED, HttpStatus.BAD_REQUEST);
        }

        executeBusinessRules(companies);
        return (List<Company>) companyRepository.saveAll(companies);
    }


    @Override
    public CompanySummaryDto viewCompanySummary(Long companyId) {
        List<CompanyRepository.CompanyPopularUserDto> companySummaryResult = companyRepository.retrieveCompanyConversationSummaryByCompanyId(companyId);
        if (companySummaryResult.isEmpty()) {
            log.error("Company with supplied id, {} does not exist.", companyId);
            throw new ApplicationActivityException(ApplicationErrorMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Map<Integer, List<Integer>> threadToUserMap = companySummaryResult.stream()
                .collect(groupingBy(CompanyRepository.CompanyPopularUserDto::getThreadCount, mapping(CompanyRepository.CompanyPopularUserDto::getMostPopularUser, toList())));

        List<Integer> popularUser = threadToUserMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new ApplicationActivityException(ApplicationErrorMessages.DATA_INTEGRITY_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));


        return CompanySummaryDto.builder()
                .companyName(companySummaryResult.get(0).getCompanyName()) // the company will always be the same
                .conversationCount(companySummaryResult.size())
                .mostPopularUser(popularUser.stream().map(String::valueOf).collect(Collectors.joining(","))).build();
    }
}
