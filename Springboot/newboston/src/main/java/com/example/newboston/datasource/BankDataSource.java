package com.example.newboston.datasource;

import java.util.Collection;

import com.example.newboston.model.Bank;

public interface BankDataSource {  //this interface will define the functionality expected from the data source
    
    public Collection<Bank> retrieveBanks();

    public Bank retrieveBank(String accountNumber);

    public Bank createBank(Bank bank);

    public Bank updateBank(Bank bank);

    public void deleteBank(String accountNumber);
}
