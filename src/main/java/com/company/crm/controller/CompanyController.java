package com.company.crm.controller;


import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import com.company.crm.service.CompanyServiceImpl;
import com.company.crm.service.CompanyService;
import com.company.crm.util.MinSizeConstraint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Validated
@RestController
@RequestMapping(path = "/company/")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyServiceImpl;
    }

    @PostMapping("import")
    public void importCompanies(
            @RequestBody
            @MinSizeConstraint List<@Valid Company> companies) {
        companyService.save(companies);
    }

    @GetMapping("summary/{companyId}")
    public CompanySummaryDto viewCompanySummary(@PathVariable("companyId") Long companyId) {
        return companyService.viewCompanySummaryDetails(companyId);
    }
}
