package com.helpscout.review.controller;

import com.helpscout.review.entity.Company;
import com.helpscout.review.repository.CompanyRepository;
import com.helpscout.review.service.CompanyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImportController {

    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    public ImportController(CompanyRepository companyRepository, CompanyService companyService) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @PostMapping("/company/import")
    public void importCompanies(@RequestBody List<Company> companies) {
        companyService.removeDuplicateThreads(companies);
        companyRepository.saveAll(companies);
    }
}