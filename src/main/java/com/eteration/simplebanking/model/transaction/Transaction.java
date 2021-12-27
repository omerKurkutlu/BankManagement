package com.eteration.simplebanking.model.transaction;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

// This class is a place holder you can change the complete implementation
@Inheritance
@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "approvalCode", updatable = false, nullable = false)
    private String approvalCode;
    private Date date;
    private double amount;
    @Transient @JsonSerialize private final String  type=this.getClass().getSimpleName();

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public Transaction() {
        this.date=new Date();
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.date=new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
}
