package com.company.crm.review.repository;

import com.company.crm.review.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT com.name as companyName, count( conv.conversation_primary_key) as threadCount, conv.user_id as mostPopularUser, from company com" +
            " left join conversation conv on com.company_primary_key = conv.company_fk" +
            " left join thread th on conv.conversation_primary_key = th.conversation_fk" +
            "  where com.company_primary_key = :company_id group by com.name ,conv.user_id", nativeQuery = true)
    List<CompanyPopularUserDto> retrieveCompanyConversationSummaryByCompanyId(@Param("company_id") Long companyId);

    interface CompanyPopularUserDto {
        String getCompanyName();
        Integer getThreadCount();
        Integer getMostPopularUser();
    }
}
