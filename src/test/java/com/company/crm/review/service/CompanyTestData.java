package com.company.crm.review.service;

import com.company.crm.review.domain.CompanyDto;
import com.company.crm.review.repository.CompanyRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompanyTestData {

    public static List<CompanyDto> getCompanyData() {

        CompanyDto company1 = new CompanyDto(8000L, "Company_A", LocalDateTime.of(2018, Month.MAY, 20, 12, 0, 0), getConversationList());
        CompanyDto company2 = new CompanyDto(9000L, "Company_B", LocalDateTime.of(2018, Month.OCTOBER, 20, 12, 0, 0), Collections.emptyList());

        return Arrays.asList(company1, company2);
    }

    private static List<CompanyDto.ConversationDto.ThreadDto> getThreadList() {
        CompanyDto.ConversationDto.ThreadDto thread1 = new CompanyDto.ConversationDto.ThreadDto(555L, "I am a simple thread");
        CompanyDto.ConversationDto.ThreadDto thread2 = new CompanyDto.ConversationDto.ThreadDto(777L, "I am a simple thread again");

        return Arrays.asList(thread1, thread2);
    }


    private static List<CompanyDto.ConversationDto> getConversationList() {
        CompanyDto.ConversationDto conversation1 = new CompanyDto.ConversationDto(111L, 333L, 444L, "someuser@mail.com", LocalDateTime.of(2019, Month.JUNE, 20, 12, 0, 0), getThreadList(), Collections.emptyList());
        CompanyDto.ConversationDto conversation2 = new CompanyDto.ConversationDto(222L, 444L, 555L, "anotheruser@mail.com", LocalDateTime.of(2020, Month.APRIL, 12, 11, 0, 0), getThreadList(), Collections.emptyList());
        return Arrays.asList(conversation1, conversation2);
    }


    public static List<CompanyRepository.CompanyPopularUserDto> getCompanySummaryData() {
        CompanyRepository.CompanyPopularUserDto summaryQueryResult1 = new CompanyRepository.CompanyPopularUserDto() {
            @Override
            public String getCompanyName() {
                return "DummyCompany";
            }

            @Override
            public Integer getThreadCount() {
                return 10;
            }

            @Override
            public Integer getMostPopularUser() {
                return 2222;
            }
        };

        CompanyRepository.CompanyPopularUserDto summaryQueryResult2 = new CompanyRepository.CompanyPopularUserDto() {
            @Override
            public String getCompanyName() {
                return "DummyCompany";
            }

            @Override
            public Integer getThreadCount() {
                return 10;
            }

            @Override
            public Integer getMostPopularUser() {
                return 4444;
            }
        };

        return Arrays.asList(summaryQueryResult1, summaryQueryResult2);
    }
}
