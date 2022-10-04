package com.company.crm.review.rules;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.exception.ApplicationActivityException;
import com.company.crm.review.exception.ApplicationErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FilterCompanyBySignUpDateWithinRule implements BusinessRules<CompanyDto> {

    // can also define spring.jackson.date-format=com.fasterxml.jackson.databind.util.ISO8601DateFormat in property file.
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public FilterCompanyBySignUpDateWithinRule(@Value("#{T(java.time.LocalDateTime).parse('${helpScout.rule.filterSignUpDateFrom}')}") LocalDateTime startDate, @Value("#{T(java.time.LocalDateTime).parse('${helpScout.rule.filterSignUpDateTo}')}") LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public void execute(List<CompanyDto> companies) {
        try {
            companies
                    .removeIf(company -> (company.getSignedUp()
                            .isBefore(endDate) && company.getSignedUp()
                            .isAfter(startDate) && company.getConversations().size() < 1));
        } catch (RuntimeException exception) {
            log.error("Error filtering records by date within date range, actual exception message is : {}", exception.getMessage(), exception);
            throw new ApplicationActivityException(ApplicationErrorMessages.FILTER_SIGNUP_BY_DATE_EXECUTION_ERROR, HttpStatus.BAD_REQUEST); //could be as a result of bad data, TODO:// implement validations on DTO members
        }
    }

    @Override
    public BusinessRules.BusinessRule getBusinessRule() {
        return BusinessRules.BusinessRule.FILTER_BY_SIGN_UP_DATE_RULE;
    }
}
