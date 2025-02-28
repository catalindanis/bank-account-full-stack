package com.example.demo.Transaction;

import java.time.LocalDate;

public class Transaction {
    /**
     * Transaction class
     */

    /**
     * Transaction properties
     */
    private long id;
    private LocalDate date;
    private double amount;
    private String type;

    /**
     * Transaction constructors
     */
    public Transaction() {}

    public Transaction(long id, LocalDate date, double amount, String type) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(LocalDate date, double amount, String type) {
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Getters and setters for transaction properties
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * String format of transaction
     * @return
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }
}
