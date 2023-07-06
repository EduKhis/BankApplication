package com.example.bankapplication.model;

//import jakarta.persistence.Entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    @ManyToOne
    private User user;
    private String date;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CreditCard(String number, String date) {
        this.number = number;
        this.date = date;
    }

    public CreditCard() {
    }
}
