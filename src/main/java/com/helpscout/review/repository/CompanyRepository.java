package com.helpscout.review.repository;

import com.helpscout.review.dto.CompanySummaryDto;
import com.helpscout.review.entity.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    @Query(rowMapperClass = CompanySummaryDto.class, value = "SELECT com.name as companyName, count( conv.conversation_id) as threadCount, conv.user_id as mostPopularUser, from company com" +
            " left join conversation conv on com.company_id = conv.company" +
            " left join thread th on conv.conversation_id = th.conversation" +
            "  where com.company_id = :company_id group by com.name ,conv.user_id")
    List<CompanySummaryDto> retrieveCompanyConversationSummaryByCompanyId(@Param("company_id") Long companyId);
}