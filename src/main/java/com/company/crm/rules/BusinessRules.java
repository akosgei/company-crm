package com.company.crm.rules;

import java.util.List;

public interface BusinessRules<T> {

    void execute(List<T> payload);

    BusinessRule getBusinessRule();

    enum BusinessRule {
        FILTER_BY_SIGN_UP_DATE_RULE,
        REMOVE_DUPLICATE_THREADS_RULE
    }
}
