package com.example.demo.Transaction;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    /**
     * In-memory repository of transactions
     */

    /**
     * Transaction properties
     */
    List<Transaction> transactions;

    public TransactionRepository() {
        /**
         * Constructor of TransactionRepository
         */
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        /**
         * Adds the transaction passed to the transactions list
         * @param transaction: the transaction to be added (Transaction)
         * @throws IllegalArgumentException if a transaction with that id already exists
         */
        Transaction transaction1 = getTransactionById(transaction.getId());
        if(transaction1 != null)
            throw new IllegalArgumentException("Transaction already exists");
        this.transactions.add(transaction);
    }

    public Transaction getTransactionById(long id) {
        /**
         * Returns the transaction with passed id
         * @param id: the id of the transaction (long)
         * @returns the transaction (Transaction) / null (if
         * the transaction with the passed id doesn't exists)
         */
        for (Transaction transaction : transactions)
            if (transaction.getId() == id)
                return transaction;
        return null;
    }

    public List<Transaction> getAll() {
        /**
         * Returns the transactions list
         * @return list of transactions (List<Transaction>)
         */
        return this.transactions;
    }

    public void deleteTransactionById(long id) {
        /**
         * Removes a transaction with passed id
         * @param id: the id of the transaction (long)
         * @throws IllegalArgumentException if a transaction with that id doesn't exists
         */
        Transaction transaction = getTransactionById(id);
        if(transaction == null)
            throw new IllegalArgumentException("Transaction not found");
        transactions.remove(transaction);
    }

    public void updateTransaction(long id, Transaction newTransaction) {
        /**
         * Updates a transaction with passed id
         * @param id: the id of the transaction (long)
         * @param newTransaction: the new transaction (Transaction)
         * @throws IllegalArgumentException if a transaction with that id doesn't exists
         */
        Transaction transaction = getTransactionById(id);
        if(transaction == null)
            throw new IllegalArgumentException("Transaction not found");
        transaction.setDate(newTransaction.getDate());
        transaction.setAmount(newTransaction.getAmount());
        transaction.setType(newTransaction.getType());
    }

    public void setTransactions(List<Transaction> transactions) {
        /**
         * Setter for transactions list
         * @param transactions: the new transactions list (List<Transaction>)
         */
        this.transactions = transactions;
    }
}
