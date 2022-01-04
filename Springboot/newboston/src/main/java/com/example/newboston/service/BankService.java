package com.example.newboston.service;

import java.util.Collection;
import com.example.newboston.datasource.BankDataSource;
import com.example.newboston.model.Bank;

import org.springframework.stereotype.Service;

@Service //tells springboot to make this class(or its objects) available at run time
         //responsible for call data source, handling data exceptions etc
public class BankService {
    private BankDataSource dataSource;

    public BankService( BankDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Bank> getBanks()
    {
        return dataSource.retrieveBanks();
    }

    public Bank getBank(String accountNumber) {
        return dataSource.retrieveBank(accountNumber);
    }

    public Bank addBank(Bank bank) {
        return dataSource.createBank(bank);
    }

    public Bank updateBank(Bank bank) {
        return dataSource.updateBank(bank);
    }

    public void deleteBank(String accountNumber) {
        dataSource.deleteBank(accountNumber);
    }
}
