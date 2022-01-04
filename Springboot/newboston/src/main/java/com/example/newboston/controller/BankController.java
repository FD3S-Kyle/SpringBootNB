package com.example.newboston.controller;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.example.newboston.datasource.BankDataSource;
import com.example.newboston.datasource.mock.MockBankDataSource;
import com.example.newboston.model.Bank;
import com.example.newboston.service.BankService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private BankDataSource dataSource = new MockBankDataSource();
    private BankService service = new BankService(dataSource);

    public BankController(BankService service) {
        this.service = service;
    }

    @ExceptionHandler(value = NoSuchElementException.class) //when ever the NoSuchElementException is thrown this method handles that exception and returns the included response
    public ResponseEntity<String> handleNotFound(NoSuchElementException e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bank addBank(@RequestBody Bank bank)
    {
        return this.service.addBank(bank);
    }

    @PatchMapping
    public Bank updateBank(@RequestBody Bank bank)
    {
        return this.service.updateBank(bank);
    }

    @DeleteMapping(path = "/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBank(@PathVariable("accountNumber") String accountNumber)
    {
        this.service.deleteBank(accountNumber);
    }
}
