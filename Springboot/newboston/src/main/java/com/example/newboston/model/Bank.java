package com.example.newboston.model;

public class Bank {
    private String accountNumber;
    private double trust;
    private int transactionFee;


    public Bank(String accountNumber, double trust, int transactionFee) {
        this.accountNumber = accountNumber;
        this.trust = trust;
        this.transactionFee = transactionFee;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public double getTrust() {
        return trust;
    }


    public void setTrust(double trust) {
        this.trust = trust;
    }


    public int getTransactionFee() {
        return transactionFee;
    }


    public void setTransactionFee(int transactionFee) {
        this.transactionFee = transactionFee;
    }

    

    
}
