package com.company.crm.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ErrorResponse {
    private int httpStatusCode;
    private String errorMessage;
    private LocalDateTime responseTime;
}