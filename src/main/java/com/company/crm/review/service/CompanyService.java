package com.company.crm.review.service;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.domain.CompanySummaryDto;
import com.company.crm.review.entity.Company;
import com.company.crm.review.exception.ApplicationActivityException;
import com.company.crm.review.exception.ApplicationErrorMessages;
import com.company.crm.review.repository.CompanyRepository;
import com.company.crm.review.rules.BusinessRules;
import com.company.crm.review.rules.configuration.BusinessRulesConfiguration;
import com.company.crm.review.util.ModelMapperUtil;
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
public class CompanyService implements ICompanyService {

    final Map<BusinessRules.BusinessRule, List<CompanyDto>> rulesToExecute = new LinkedHashMap<>();

    private final CompanyRepository companyRepository;
    private final BusinessRulesConfiguration rulesConfiguration;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, BusinessRulesConfiguration rulesConfiguration) {

        this.companyRepository = companyRepository;
        this.rulesConfiguration = rulesConfiguration;
    }

    private void executeBusinessRules(List<CompanyDto> companies) {
        {
            rulesToExecute.put(BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE, companies);
            rulesToExecute.put(BusinessRules.BusinessRule.REMOVE_DUPLICATE_THREADS_RULE, companies);
            rulesToExecute.put(BusinessRules.BusinessRule.PERSIST_DUPLICATE_THREADS_RULE, companies);
        }

        rulesToExecute.forEach((businessRule, payload) -> {
            BusinessRules<CompanyDto> rule = rulesConfiguration.findBusinessRule(businessRule);
            if (rule == null) {
                return;
            }
            rule.execute(payload);
        });
    }

    @Override
    public List<Company> save(List<CompanyDto> companies) {
        if (companies.isEmpty()) {
            log.error("Supplied payload (list of companies) is empty");
            throw new ApplicationActivityException(ApplicationErrorMessages.EMPTY_PAYLOAD_SUPPLIED, HttpStatus.BAD_REQUEST);
        }

        executeBusinessRules(companies);
        //convert filtered company dto object to list of company entities
        List<Company> company = ModelMapperUtil.convertDtoToEntity(companies, Company.class);

        //mapping foreign keys of related entities does not happen out of the box, we have to map them.
        company.forEach(companyEntity -> companyEntity.getConversations().forEach(conversation -> {
            conversation.setCompany(companyEntity);
            conversation.getThreads().forEach(thread -> thread.setConversation(conversation));
        }));

        return companyRepository.saveAll(company);
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
