package com.example.demo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {
    /**
     * Service for transactions
     */

    private final TransactionRepository transactionRepository;
    private List<List<Transaction>> transactionsHistory;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        /**
         * Constructor of TransactionService
         */
        this.transactionRepository = transactionRepository;
        this.transactionsHistory = new ArrayList<>();
        this.makeCopy();
    }

    public void addTransaction(String date, double amount, String type) {
        /**
         * Create a transaction and add it in the repository
         */
        this.makeCopy();
        Transaction transaction = new Transaction(generateNextId(), LocalDate.parse(date), amount, type);
        this.transactionRepository.addTransaction(transaction);
    }

    public void updateTransaction(long id, String date, double amount, String type) {
        /**
         * Updates a transaction by id with new properties
         */
        this.makeCopy();
        this.transactionRepository.updateTransaction(id, new Transaction(LocalDate.parse(date), amount, type));
    }

    public void deleteTransaction(long id) {
        /**
         * Deletes a transaction by id
         */
        this.makeCopy();
        this.transactionRepository.deleteTransactionById(id);
    }

    public int deleteTransactionsByType(String type) {
        /**
         * Deletes transactions of a type
         * @return number of transactions deleted
         */
        this.makeCopy();
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getType().equals(type)) {
                deleteId.add(transaction.getId());
            }
        for(Long id : deleteId)
            transactionRepository.deleteTransactionById(id);
        return deleteId.size();
    }

    public int deleteTransactionFromDate(String date) {
        /**
         * Deletes transactions from a date
         * @return number of transactions deleted
         */
        this.makeCopy();
        LocalDate real_date = LocalDate.parse(date);
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isEqual(real_date)) {
                deleteId.add(transaction.getId());
            }
        for(Long id : deleteId)
            transactionRepository.deleteTransactionById(id);
        return deleteId.size();
    }

    public int deleteTransactionFromInterval(String dateStart, String dateEnd) {
        /**
         * Deletes transactions between two dates
         * @return number of transactions deleted
         */
        this.makeCopy();
        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);
        List<Long> deleteId = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(!transaction.getDate().isBefore(startDate) &&
                    !transaction.getDate().isAfter(endDate)) {
                deleteId.add(transaction.getId());
            }
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
        LocalDate realDate = LocalDate.parse(dateEnd);
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isBefore(realDate) &&
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

        transactions.sort(Comparator.comparingDouble(Transaction::getAmount));
        return transactions;
    }

    public Double getSumOfTransactionsByType(String type) {
        /**
         * Returns sum of transactions from the repository by a type
         * @return sum double
         */
        double sum = 0.0;
        for(Transaction transaction : this.getAll())
            if(transaction.getType().equals(type))
                sum += transaction.getAmount();
        return sum;
    }

    public Double getBalanceOnDate(String date) {
        /**
         * Get balance from a date
         * @return balance double
         */
        double balance = 0.0;
        LocalDate realDate = LocalDate.parse(date).plusDays(1);
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isBefore(realDate))
                balance += transaction.getAmount();
        return balance;
    }

    public List<Transaction> getAllWithoutType(String type) {
        /**
         * Get all transactions from the repository different from a type
         * @return list of transactions
         */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(!transaction.getType().equals(type))
                transactions.add(transaction);

        return transactions;
    }

    public List<Transaction> getAllByTypeWithMaxAmount(String type, Double maxAmount) {
        List<Transaction> transactions = getAllByType(type);
        for(Transaction transaction : this.getAll())
            if(transaction.getAmount() < maxAmount)
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

    public void undo() {
        /**
         * Sets transactions list from repository to a previous version of it
         * @throws IllegalArgumentException if there are no previous versions of transactions list
         */
        if(this.transactionsHistory.size() > 1) {
            this.transactionRepository.setTransactions(this.transactionsHistory.get(this.transactionsHistory.size() - 1));
            this.transactionsHistory.remove(this.transactionsHistory.size() - 1);
        }
        else
            throw new IllegalArgumentException("Maximum undo operations exceeded");
    }

    private void makeCopy() {
        /**
         * Make a copy of the current transactions list and add to transactions history
         */
        List<Transaction> copy = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            copy.add(new Transaction(transaction.getId(), transaction.getDate(), transaction.getAmount(), transaction.getType()));
        this.transactionsHistory.add(copy);
    }
}
