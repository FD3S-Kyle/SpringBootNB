package com.example.newboston.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import com.example.newboston.datasource.mock.MockBankDataSource;
import com.example.newboston.model.Bank;

import org.junit.jupiter.api.Test;

public class BankServiceTest {

    private MockBankDataSource dataSource = new MockBankDataSource();
    //private BankDataSource dataSource = (BankDataSource) mock(List.class); <-- this is suppose to be a mock object
    private BankService bankService = new BankService(dataSource);

    @Test
    public void DataSourceCall_RetrieveBanks()
    {
        //given

        //when
        Collection<Bank> banks = bankService.getBanks();

        //then
        verify(eq(1)); {dataSource.retrieveBanks();}
    }
}
