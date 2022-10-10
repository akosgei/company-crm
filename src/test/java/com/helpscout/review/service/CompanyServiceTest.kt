package com.helpscout.review.service

import com.helpscout.review.entity.Company
import com.helpscout.review.entity.Conversation
import com.helpscout.review.entity.Thread
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CompanyServiceTest {
    
    private val companyService = CompanyService()
    
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

        companyService.removeDuplicateThreads(companies)

        assertThat(companies)
                .flatExtracting("conversations")
                .flatExtracting("threads")
                .extracting("id")
                .containsExactly(101L, 103L, 201L, 202L)
    }

    private fun company(id: Long, vararg conversations: Conversation) = Company().apply {
        this.id = id
        this.name = "Company $id"
        this.conversations = setOf(*conversations)
    }

    private fun conversation(id: Long, vararg threads: Thread) = Conversation().apply {
        this.id = id
        this.threads = setOf(*threads)
    }

    private fun thread(id: Long, payload: String) = Thread().apply {
        this.id = id
        this.payload = payload
    }
}