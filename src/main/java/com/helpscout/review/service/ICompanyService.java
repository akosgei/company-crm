package com.helpscout.review.service;


import com.helpscout.review.dto.CompanySummaryDto;
import com.helpscout.review.entity.Company;

import java.util.List;

public interface ICompanyService {
    List<Company> save(List<Company> companyDto);

    CompanySummaryDto viewCompanySummary(Long companyId);
}
