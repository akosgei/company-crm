package com.helpscout.review.repository;

import com.helpscout.review.entity.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    @Query(value = "SELECT com.name as companyName, count( conv.conversation_primary_key) as threadCount, conv.user_id as mostPopularUser, from company com" +
            " left join conversation conv on com.company_primary_key = conv.company_fk" +
            " left join thread th on conv.conversation_primary_key = th.conversation_fk" +
            "  where com.company_primary_key = :company_id group by com.name ,conv.user_id")
    List<CompanyPopularUserDto> retrieveCompanyConversationSummaryByCompanyId(@Param("company_id") Long companyId);

    //interface based projection
    interface CompanyPopularUserDto {
        String getCompanyName();

        Integer getThreadCount();

        Integer getMostPopularUser();
    }
}