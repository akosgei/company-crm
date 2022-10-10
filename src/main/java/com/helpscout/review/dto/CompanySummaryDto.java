package com.helpscout.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanySummaryDto {
    private String companyName;
    private Integer conversationCount;
    private String mostPopularUser;
}
