package com.company.crm.review.rules;

import com.company.crm.review.repository.DuplicateThreadRepository;
import com.company.crm.review.service.CompanyTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersistDuplicateThreadsRuleTest {

    @Mock
    DuplicateThreadRepository duplicateThreadRepositoryMock;

    @InjectMocks
    PersistDuplicateThreadsRule persistDuplicateThreadsRuleService;

    @Before
    public void setup() {
        when(duplicateThreadRepositoryMock.saveAll(anyList())).thenReturn(anyList());
    }

    @Test
    public void execute() {
        persistDuplicateThreadsRuleService.execute(CompanyTestData.getCompanyData());
        Mockito.verify(duplicateThreadRepositoryMock, times(1)).saveAll(anyCollection());
    }
}