package com.company.crm.review.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationActivityException extends RuntimeException{

    private final ApplicationErrorMessages errorMessages;
    private final HttpStatus httpStatus;

    public ApplicationActivityException(ApplicationErrorMessages errorMessages , HttpStatus httpStatus) {

        this.errorMessages = errorMessages;
        this.httpStatus = httpStatus;
    }
}
