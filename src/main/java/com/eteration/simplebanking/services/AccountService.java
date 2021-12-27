package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


// This class is a place holder you can change the complete implementation
@Service
public class AccountService {


    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        return account;
    }

    public Transaction credit(String accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account account = this.findAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException
                    (new StringBuilder("Account not found with account id ").append(accountNumber).toString());
        }
        Transaction transaction = new DepositTransaction(amount);
        account.post(transaction);
        accountRepository.save(account);
        return account.getTransactions().get(account.getTransactions().size() -1);

    }

    public Transaction debit(String accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account account = this.findAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException
                    (new StringBuilder("Account not found with account id ").append(accountNumber).toString());
        }
        Transaction transaction = new WithdrawalTransaction(amount);
        if (account.post(transaction) == false)
            throw new InsufficientBalanceException(new StringBuilder("Insufficient balance ").append(account.getBalance()).toString());
        accountRepository.save(account);
        return account.getTransactions().get(account.getTransactions().size() -1);
    }

    public Account create(Account account) {
        account.setCreateDate(new Date());
        return accountRepository.save(account);
    }

    public Transaction phoneBillPayment(String accountNumber, PhoneBillPaymentTransaction transaction) throws AccountNotFoundException, InsufficientBalanceException {
        Account account = this.findAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException
                    (new StringBuilder("Account not found with account id ").append(accountNumber).toString());
        }
        if (account.post(transaction) == false)
            throw new InsufficientBalanceException(new StringBuilder("Insufficient balance ").append(account.getBalance()).toString());
        accountRepository.save(account);
        return account.getTransactions().get(account.getTransactions().size() -1);
    }
}
