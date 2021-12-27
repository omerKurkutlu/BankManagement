package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Account {
    private String owner;
    @Id
    private String accountNumber;
    private double balance;
    private Date createDate;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions;


    public Account() {

    }

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.createDate = new Date();
        this.transactions = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean post(Transaction transaction) throws InsufficientBalanceException {
        if (transaction == null) return false;
        if (transaction instanceof DepositTransaction) {
            if (this.deposit(transaction.getAmount())) {
                this.transactions.add(transaction);
            } else {
                return false;
            }
        } else if (transaction instanceof WithdrawalTransaction) {
            if (this.withdraw(transaction.getAmount())) {
                this.transactions.add(transaction);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0 || amount > balance)
            throw new InsufficientBalanceException("Insufficent balance");
        this.balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0)
            return false;
        this.balance += amount;
        return true;
    }
}
