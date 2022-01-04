package com.example.newboston.datasource.mock;

import java.util.Collection;
import com.example.newboston.model.Bank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MockBankDataSourceTest {

    private MockBankDataSource mockDataSource = new MockBankDataSource();

    @Test
    public void collectionBanks() //checks if banks collection is empty or not
    {
        //given

        //when
        Collection<Bank> banks = this.mockDataSource.retrieveBanks();
        
        //then
        //Assertions.assertThat(banks).isNotEmpty();
        Assertions.assertThat(banks.size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    public void mockData() //provides mock data
    {
        //given

        //when
        Collection<Bank> banks = this.mockDataSource.retrieveBanks();
        
        //then
        Assertions.assertThat(banks).allMatch(it->it.getAccountNumber().length()>0);
        Assertions.assertThat(banks).anyMatch(it->it.getTrust() != 0.0);
        Assertions.assertThat(banks).anyMatch(it->it.getTransactionFee() != 0);
    }
}
