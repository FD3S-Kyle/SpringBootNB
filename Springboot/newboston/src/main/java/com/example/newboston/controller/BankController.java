package com.example.newboston.controller;

import java.util.Collection;

import com.example.newboston.datasource.BankDataSource;
import com.example.newboston.datasource.mock.MockBankDataSource;
import com.example.newboston.model.Bank;
import com.example.newboston.service.BankService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private BankDataSource dataSource = new MockBankDataSource();
    private BankService service = new BankService(dataSource);

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Bank> getBanks()
    {
        return this.service.getBanks();
    }

    @GetMapping ("/{accountNumber}")
    public Bank getBank(@PathVariable("accountNumber") String accountNumber)
    {
        return this.service.getBank(accountNumber);
    }
}
