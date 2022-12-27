package com.company.crm.rules;


import com.company.crm.entity.Company;
import com.company.crm.exception.ApplicationActivityException;
import com.company.crm.exception.ApplicationErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class FilterCompanyBySignUpDateWithinRule implements BusinessRules<Company> {

    // can also define spring.jackson.date-format=com.fasterxml.jackson.databind.util.ISO8601DateFormat in property file to parse the dates during runtime
    @Value("#{T(java.time.LocalDateTime).parse('${crm.rule.filterSignUpDateFrom}')}")
    private LocalDateTime startDate;
    @Value("#{T(java.time.LocalDateTime).parse('${crm.rule.filterSignUpDateTo}')}")
    private LocalDateTime endDate;

    /**
     * If a company sign-up date is within the first 10 months of 2018,
     * then it should only be saved if there aren't any conversations associated with the company.
     * We will use stream api to iterate the company list to check if the signed-up date falls
     * within dates supplied in the {@link FilterCompanyBySignUpDateWithinRule#startDate} and  {@link FilterCompanyBySignUpDateWithinRule#endDate}
     * the predicate removeIf with filter out such companies that do not satisfy the above criteria.
     * ie: {@link Collection#removeIf(Predicate)} should satisfy below predicate expressions
     *
     * <li>company within the date range provided</>
     * <li>company has conversations</>
     *
     * @param java.util.List<Company> companies
     */
    @Override
    public void execute(List<Company> companies) {
        try {
            companies
                    .removeIf(company -> (company.getSignedUp()
                            .isBefore(endDate) && company.getSignedUp()
                            .isAfter(startDate) && !company.getConversations().isEmpty()));
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
