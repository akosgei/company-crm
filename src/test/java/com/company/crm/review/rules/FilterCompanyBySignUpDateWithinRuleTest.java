package com.company.crm.review.rules;

import com.company.crm.review.service.CompanyTestData;
import com.company.crm.review.domain.CompanyDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class FilterCompanyBySignUpDateWithinRuleTest {

    private FilterCompanyBySignUpDateWithinRule signUpDateWithinRule;

    @Before
    public void setup() {
        LocalDateTime startDate = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 59);
        LocalDateTime endDate = LocalDateTime.of(2018, Month.NOVEMBER, 1, 0, 0, 0);
        signUpDateWithinRule = new FilterCompanyBySignUpDateWithinRule(startDate, endDate);
    }

    @Test
    public void execute() {
        List<CompanyDto> testData = new ArrayList<>(CompanyTestData.getCompanyData());
        Assert.assertEquals(2, testData.size());
        signUpDateWithinRule.execute(testData);
        // After rule filter is applied
        Assert.assertEquals(1, testData.size());
    }
}