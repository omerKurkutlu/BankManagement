package com.eteration.simplebanking.model.transaction;


import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class PhoneBillPaymentTransaction extends BillPaymentTransaction {

    private String phoneNumber;

    public PhoneBillPaymentTransaction() {
        super();
    }

    public PhoneBillPaymentTransaction(String phoneNumber, String payee, double amount) {
        super(payee, amount);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
