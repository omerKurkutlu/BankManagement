package com.eteration.simplebanking.model.transaction;


import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class WithdrawalTransaction extends Transaction{

    public WithdrawalTransaction() {
        super();
    }

    public WithdrawalTransaction(double amount) {
        super(amount);
    }
}


