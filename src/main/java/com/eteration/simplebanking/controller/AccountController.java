package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is a place holder you can change the complete implementation
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/v1/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        if (account != null)
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        else
            return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/account/v1/create")
    public ResponseEntity<Account> create(@RequestBody Account body) throws AccountNotFoundException {
        if (body == null) {
            return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.create(body);
        if (account != null) {
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        } else
            return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/account/v1/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction body) throws AccountNotFoundException, InsufficientBalanceException {
        if (body == null || body.getAmount() <= 0) {
            return new ResponseEntity<TransactionStatus>(HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = accountService.credit(accountNumber, body.getAmount());
        if (transaction != null) {
            TransactionStatus response = new TransactionStatus(HttpStatus.OK.name(), transaction.getApprovalCode());
            return ResponseEntity.ok(response);
        } else
            return new ResponseEntity<TransactionStatus>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/account/v1/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction body) throws AccountNotFoundException, InsufficientBalanceException {
        if (body == null || body.getAmount() <= 0) {
            return new ResponseEntity<TransactionStatus>(HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = accountService.debit(accountNumber, body.getAmount());
        if (transaction != null) {
            TransactionStatus response = new TransactionStatus(HttpStatus.OK.name(), transaction.getApprovalCode());
            return ResponseEntity.ok(response);
        } else
            return new ResponseEntity<TransactionStatus>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/account/v1/phoneBillPayment/{accountNumber}")
    public ResponseEntity<TransactionStatus> phoneBillPayment(@PathVariable String accountNumber, @RequestBody PhoneBillPaymentTransaction body) throws AccountNotFoundException, InsufficientBalanceException {

        Transaction transaction = accountService.phoneBillPayment(accountNumber, body);
        if (transaction != null) {
            TransactionStatus response = new TransactionStatus(HttpStatus.OK.name(), transaction.getApprovalCode());
            return ResponseEntity.ok(response);
        } else
            return new ResponseEntity<TransactionStatus>(HttpStatus.NO_CONTENT);
    }
}