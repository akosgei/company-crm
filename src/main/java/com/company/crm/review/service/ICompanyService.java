package com.company.crm.review.service;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.domain.CompanySummaryDto;
import com.company.crm.review.entity.Company;

import java.util.List;

public interface ICompanyService {
    List<Company> save(List<CompanyDto> companyDto);

    CompanySummaryDto viewCompanySummary(Long companyId);
}
