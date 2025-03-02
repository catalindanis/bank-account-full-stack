package com.example.demo.Transaction;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTransaction {
    /*
    The class used for testing transactions
     */

    /**
     * Transaction properties
     */
    private TransactionRepository repository;
    private TransactionService service;
    private TransactionController controller;

    public TestTransaction(){
        /*
        The constructor of TestTransaction class
         */
        repository = new TransactionRepository();
        service = new TransactionService(repository);
        controller = new TransactionController(service);
    }

    @Test
    public void testDomain(){
        /**
         * This function tests the transaction domain
         */
        Transaction t = new Transaction(0, LocalDate.parse("2024-11-02"), 150, "enter");
        assertEquals(0, t.getId());
        assertEquals(LocalDate.parse("2024-11-02"), t.getDate());
        assertEquals(150, t.getAmount());
        assertEquals("enter", t.getType());
    }

    @Test
    public void testRepository(){
        /**
         * This function tests the transactions repository
         */
        Transaction t1 = new Transaction(0, LocalDate.parse("2024-11-02"), 150, "enter");
        Transaction t2 = new Transaction(0, LocalDate.parse("2023-08-10"), 120, "exit");
        Transaction t3 = new Transaction(1, LocalDate.parse("2023-08-10"), 120, "exit");
        this.repository.addTransaction(t1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.repository.addTransaction(t2);
        });
        assertEquals("Transaction already exists", exception.getMessage());
        assertEquals(this.repository.getAll().size(), 1);
        this.repository.addTransaction(t3);
        assertEquals(this.repository.getAll().size(), 2);

        assertEquals(this.repository.getTransactionById(3), null);
        assertEquals(this.repository.getTransactionById(1), t3);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            this.repository.deleteTransactionById(-1);
        });
        assertEquals("Transaction not found", exception.getMessage());
        this.repository.deleteTransactionById(1);
        assertEquals(this.repository.getAll().size(), 1);

        this.repository.updateTransaction(0, t2);
        assertEquals(this.repository.getTransactionById(0), t2);

        this.repository.setTransactions(new ArrayList<>());
        assertEquals(this.repository.getAll().size(), 0);
    }

    @Test
    public void testService(){
        /**
         * This function tests the transactions service
         */
    }

    @Test
    public void testController(){
        /**
         * This function tests the transactions controller
         */

    }
}
