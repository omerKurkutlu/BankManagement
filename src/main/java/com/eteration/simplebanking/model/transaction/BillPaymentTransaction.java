package com.eteration.simplebanking.model.transaction;


import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class BillPaymentTransaction extends WithdrawalTransaction  {

    private String payee;

    public BillPaymentTransaction() {
        super();
    }

    public BillPaymentTransaction(String payee, double amount) {
        super(amount);
        this.payee = payee;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
