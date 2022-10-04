package com.company.crm.review.rules

import com.company.crm.review.domain.CompanyDto
import com.company.crm.review.domain.CompanyDto.ConversationDto
import com.company.crm.review.domain.CompanyDto.ConversationDto.ThreadDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RemoveDuplicateThreadsRuleTest {
    
    private val removeDuplicateThreadsRuleService = RemoveDuplicateThreadsRule()
    
    @Test
    fun `should remove duplicate threads`() {
        val companies = listOf(
            company(1,
                conversation(10,
                    thread(101, "a"),
                    thread(102, "a"),
                    thread(103, "b")
                )
            ),
            company(2,
                conversation(20,
                    thread(201, "a"),
                    thread(202, "b"),
                    thread(203, "b")
                )
            )
        )

        removeDuplicateThreadsRuleService.execute(companies)

        assertThat(companies)
                .flatExtracting("conversations")
                .flatExtracting("threads")
                .extracting("id")
                .containsExactly(101L, 103L, 201L, 202L)
    }

    private fun company(id: Long, vararg conversations: ConversationDto) = CompanyDto().apply {
        this.id = id
        this.name = "Company $id"
        this.conversations = listOf(*conversations)
    }

    private fun conversation(id: Long, vararg threads: ThreadDto) = ConversationDto().apply {
        this.id = id
        this.threads = listOf(*threads)
    }

    private fun thread(id: Long, payload: String) = ThreadDto().apply {
        this.id = id
        this.payload = payload
    }
}