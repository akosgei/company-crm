package com.company.crm.service;


import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;

import java.util.List;

public interface CompanyService {
    void save(List<Company> companyDto);

    CompanySummaryDto viewCompanySummaryDetails(Long companyId);
}
