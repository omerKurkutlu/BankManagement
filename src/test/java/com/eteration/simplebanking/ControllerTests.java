package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.dto.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    
    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        Transaction transaction = new DepositTransaction(12.2);

        doReturn(transaction).when(service).credit("17892", 1000.0);

        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).credit("17892", 1000.0);
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        Transaction depositTransaction = new DepositTransaction(1000.0);
        doReturn(depositTransaction).when(service).credit("17892", 1000.0);

        Transaction withdrawalTransaction = new WithdrawalTransaction(50.0);
        doReturn(withdrawalTransaction).when(service).debit("17892", 50.0);

        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(50.0));

        verify(service, times(1)).credit("17892", 1000.0);
        verify(service, times(1)).debit("17892", 50.0);

        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
    throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");

            doThrow(new InsufficientBalanceException("")).when(service).credit("17892", 1000.0);

            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccount("17892");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}
