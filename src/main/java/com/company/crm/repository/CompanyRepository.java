package com.company.crm.repository;

import com.company.crm.dto.CompanySummaryDto;
import com.company.crm.entity.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    @Query(rowMapperClass = CompanySummaryDto.class, value = "SELECT com.name as companyName, count( conv.conversation_id) as conversationCount, conv.user_id as mostPopularUser, from company com" +
            " left join conversation conv on com.company_id = conv.company" +
            " left join thread th on conv.conversation_id = th.conversation" +
            "  where com.company_id = :company_id group by com.name ,conv.user_id")
    List<CompanySummaryDto> retrieveCompanyConversationSummaryByCompanyId(@Param("company_id") Long companyId);
}
/**
 *
 * ==refined query==
 * SELECT  COMPANY.NAME, count(CONVERSATION.CONVERSATION_ID), SUM(COUNT(CONVERSATION.CONVERSATION_ID)) OVER() AS total_count, CONVERSATION.USER_ID FROM COMPANY INNER JOIN CONVERSATION ON COMPANY.COMPANY_ID=CONVERSATION.COMPANY INNER JOIN THREAD ON CONVERSATION.CONVERSATION_ID=THREAD.CONVERSATION WHERE COMPANY.COMPANY_ID=1 group by conversation.user_id order by count(CONVERSATION.CONVERSATION_ID) desc;
 *
 */