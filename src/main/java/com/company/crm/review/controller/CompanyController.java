package com.company.crm.review.controller;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.domain.CompanySummaryDto;
import com.company.crm.review.service.ICompanyService;
import com.company.crm.review.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/company/")
public class CompanyController {

    private final ICompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("import")
    public void importCompanies(@RequestBody List<CompanyDto> companies) {
        companyService.save(companies);
    }

    @GetMapping("summary/{companyId}")
    public CompanySummaryDto viewCompanySummary(@PathVariable("companyId") Long companyId) {
        return companyService.viewCompanySummary(companyId);
    }
}
