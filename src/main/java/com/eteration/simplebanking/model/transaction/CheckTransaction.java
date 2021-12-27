package com.eteration.simplebanking.model.transaction;


import javax.persistence.Entity;

@Entity
public class CheckTransaction extends WithdrawalTransaction {

    private String checkNumber;
    private String receiver;

    public CheckTransaction() {
        super();
    }

    public CheckTransaction(String checkNumber, String receiver, double amount) {
        super(amount);
        this.checkNumber = checkNumber;
        this.receiver = receiver;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
