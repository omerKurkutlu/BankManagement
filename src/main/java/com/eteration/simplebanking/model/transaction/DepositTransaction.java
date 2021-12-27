package com.eteration.simplebanking.model.transaction;


import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class DepositTransaction extends Transaction  {

    public DepositTransaction() {
        super();
    }

    public DepositTransaction(double amount) {
        super(amount);
    }
}
