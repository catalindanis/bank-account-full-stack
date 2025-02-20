package com.example.demo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    /**
     * Service for transactions
     */

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        /**
         * Constructor of TransactionService
         */
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(String date, double amount, String type) {
        /**
         * Create a transaction and add it in the repository
         */
        Transaction transaction = new Transaction(generateNextId(), LocalDate.parse(date), amount, type);
        transactionRepository.addTransaction(transaction);
    }

    public void updateTransaction(long id, String date, double amount, String type) {
        /**
         * Updates a transaction by id with new properties
         */
        this.transactionRepository.updateTransaction(id, new Transaction(LocalDate.parse(date), amount, type));
    }

    public void deleteTransaction(long id) {
        /**
         * Deletes a transaction by id
         */
        this.transactionRepository.deleteTransactionById(id);
    }

    public int deleteTransactionsByType(String type) {
        /**
         * Deletes transactions of a type
         * @return number of transactions deleted
         */
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getType().equals(type))
                deleteId.add(transaction.getId());
        for(Long id : deleteId)
            transactionRepository.deleteTransactionById(id);
        return deleteId.size();
    }

    public int deleteTransactionFromDate(String date) {
        /**
         * Deletes transactions from a date
         * @return number of transactions deleted
         */
        LocalDate real_date = LocalDate.parse(date);
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isEqual(real_date))
                deleteId.add(transaction.getId());
        for(Long id : deleteId)
            transactionRepository.deleteTransactionById(id);
        return deleteId.size();
    }

    public int deleteTransactionFromInterval(String dateStart, String dateEnd) {
        /**
         * Deletes transactions between two dates
         * @return number of transactions deleted
         */
        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(!transaction.getDate().isBefore(startDate) &&
                    !transaction.getDate().isAfter(endDate))
                deleteId.add(transaction.getId());
        for(Long id : deleteId)
            transactionRepository.deleteTransactionById(id);
        return deleteId.size();
    }

    public Transaction getTransactionById(long id) {
        /**
         * Gets a transaction by id from the repository
         * @return transaction
         */
        return this.transactionRepository.getTransactionById(id);
    }

    public List<Transaction> getAll() {
        /**
         * Get all transactions from the repository
         * @return list of transactions
         */
        return this.transactionRepository.getAll();
    }

    public List<Transaction> getAllWithMinAmountAndBeforeDate(Double minAmount, String dateEnd) {
        /**
         * Get all transactions from the repository with a minimum amount and before a date
         * @return list of transactions
         */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isBefore(LocalDate.parse(dateEnd)) &&
                transaction.getAmount() > minAmount)
                transactions.add(transaction);
        return transactions;
    }

    public List<Transaction> getAllWithMinAmount(Double minAmount) {
        /**
         * Get all transactions from the repository with a minimum amount
         * @return list of transactions
         */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getAmount() > minAmount)
                transactions.add(transaction);
        return transactions;
    }

    public List<Transaction> getAllByType(String type) {
        /**
         * Get all transactions from the repository by a type
         * @return list of transactions
         */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getType().equals(type))
                transactions.add(transaction);
        return transactions;
    }

    private long generateNextId(){
        /**
         * Generate next id for a transaction based on last maximum id
         * @return id long
         */
        long id = 0;
        for(Transaction transaction : this.getAll()){
            id = Math.max(id, transaction.getId());
        }
        return id + 1;
    }
}
