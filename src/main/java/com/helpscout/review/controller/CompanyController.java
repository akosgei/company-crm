package com.helpscout.review.controller;


import com.helpscout.review.dto.CompanySummaryDto;
import com.helpscout.review.entity.Company;
import com.helpscout.review.service.CompanyService;
import com.helpscout.review.service.ICompanyService;
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
    public void importCompanies(@RequestBody List<Company> companies) {
        companyService.save(companies);
    }

    @GetMapping("summary/{companyId}")
    public CompanySummaryDto viewCompanySummary(@PathVariable("companyId") Long companyId) {
        return companyService.viewCompanySummary(companyId);
    }
}
