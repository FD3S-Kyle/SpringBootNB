package com.example.newboston.datasource.mock;

import java.util.Collection;
import java.util.List;

import com.example.newboston.datasource.BankDataSource;
import com.example.newboston.model.Bank;

import org.springframework.stereotype.Repository;

@Repository //this means this class is responsible for retrieving data
public class MockBankDataSource implements BankDataSource {

    public Collection<Bank> banks = List.of(
                                        new Bank("1234", 3.14, 18),
                                        new Bank("1010", 17.0, 0),
                                        new Bank("5678", 0.0, 100)
                                        );

    @Override
    public Collection<Bank> retrieveBanks() {
        return banks;
    }

    @Override
    public Bank retrieveBank(String accountNumber) {
        return banks.stream()
                    .filter(it->it.getAccountNumber().equals(accountNumber))
                    .findFirst()
                    .get();
    }

    
}
