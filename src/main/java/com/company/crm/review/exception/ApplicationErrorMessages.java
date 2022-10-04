package com.company.crm.review.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorMessages {

    COMPANY_NOT_FOUND("Company with supplied id not found!"),
    EMPTY_PAYLOAD_SUPPLIED("Payload supplied is empty!"),
    ERROR_UPLOADING_COMPANIES("Invalid payload supplied!"),
    DATA_INTEGRITY_ERROR("Company's data is incorrectly mapped!"),
    //RULES ERRORS
    PERSIST_DUPLICATE_RULE_EXECUTION_ERROR("Error occured executing persist duplicate threads rule"),
    FILTER_SIGNUP_BY_DATE_EXECUTION_ERROR("Error occured executing filter by date within rule"),
    REMOVE_DUPLICATES_EXECUTION_ERROR("Error occured executing removal of duplicate threads rule");


    final String detailedErrorMessage;

    ApplicationErrorMessages(String detailedErrorMessage) {
        this.detailedErrorMessage = detailedErrorMessage;
    }
}
