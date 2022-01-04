package com.example.newboston.datasource.mock;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

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
    public Collection<Bank> retrieveBanks() //returns all banks
    { 
        return banks;  
    }

    @Override
    //this gets the first bank which it's accountNumber matches with the one being sent
    public Bank retrieveBank(String accountNumber) throws NoSuchElementException  
    {
        Bank bank = banks.stream()
                        .filter(it->it.getAccountNumber().equals(accountNumber))
                        .findFirst()
                        .get();
         
        if(bank == null) //this if is not currently working
        {
            throw new NoSuchElementException("Could not find a bank with account number: " + accountNumber);
        }

        return bank;
    }

    @Override
    public Bank createBank(Bank bank)
    {
        if(banks.stream().anyMatch(it->it.getAccountNumber().equals(bank.getAccountNumber())))
        {
            throw new IllegalArgumentException("Bank with account number " + bank.getAccountNumber() + "already exists");
        }

        banks.add(bank);
        return bank;
    }

    @Override
    public Bank updateBank(Bank bank)
    {
        Bank currentBank = banks.stream().filter(it->it.getAccountNumber().equals(bank.getAccountNumber())).findFirst().get();

        if(currentBank == null)
        {
            throw new NoSuchElementException("Could not find a bank with account number " + bank.getAccountNumber());
        }

        banks.remove(currentBank);
        banks.add(bank);

        return bank;
    }

    @Override
    public void deleteBank(String accountNumber) {
        Bank currentBank = banks.stream().filter(it->it.getAccountNumber().equals(accountNumber)).findFirst().get();

        if(currentBank == null)
        {
            throw new NoSuchElementException("Could not find a bank with account number " + accountNumber);
        }

        banks.removeIf(it->it.getAccountNumber().equals(accountNumber));
    }

    
}
