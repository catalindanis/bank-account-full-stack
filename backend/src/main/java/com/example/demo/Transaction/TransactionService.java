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
         * @param transactionRepository: the repository of transactions (TransactionRepository)
         */
        this.transactionRepository = transactionRepository;
        this.transactionsHistory = new ArrayList<>();
        this.makeCopy();
    }

    public void addTransaction(String date, double amount, String type) {
        /**
         * Create a transaction and add it in the repository
         * @param date: the date of the transaction (String)
         * @param amount: the amount of the transaction (double)
         * @param type: the type of the transaction (String)
         * @throws IllegalArgumentException if a transaction with that id already exists
         */
        this.makeCopy();
        Transaction transaction = new Transaction(generateNextId(), LocalDate.parse(date), amount, type);
        try {
            this.transactionRepository.addTransaction(transaction);
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public void updateTransaction(long id, String date, double amount, String type) {
        /**
         * Updates a transaction by id with new properties
         * @param id: the transaction id (long)
         * @param date: the new date (String)
         * @param amount: the new amount (double)
         * @param type: the new type (String)
         * @throws IllegalArgumentException if a transaction with that id doesn't exists
         */
        this.makeCopy();
        try {
            this.transactionRepository.updateTransaction(id, new Transaction(LocalDate.parse(date), amount, type));
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public void deleteTransaction(long id) {
        /**
         * Deletes a transaction by id
         * @param id: the id of the transaction (long)
         * @throws IllegalArgumentException if a transaction with that id doesn't exists
         */
        this.makeCopy();
        try {
            this.transactionRepository.deleteTransactionById(id);
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public int deleteTransactionsByType(String type) {
        /**
         * Deletes transactions of a type
         * @param type: the type of transactions (String)
         * @return number of transactions deleted (int)
         */
        this.makeCopy();
        try {
            List<Long> deleteId = new ArrayList<>();
            for (Transaction transaction : this.getAll())
                if (transaction.getType().equals(type)) {
                    deleteId.add(transaction.getId());
                }
            for (Long id : deleteId)
                transactionRepository.deleteTransactionById(id);
            if(deleteId.isEmpty())
                this.undo();
            return deleteId.size();
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public int deleteTransactionFromDate(String date) {
        /**
         * Deletes transactions from a date
         * @param date: the date of transactions (String)
         * @return number of transactions deleted (int)
         */
        this.makeCopy();
        try {
            LocalDate real_date = LocalDate.parse(date);
            List<Long> deleteId = new ArrayList<>();
            for (Transaction transaction : this.getAll())
                if (transaction.getDate().isEqual(real_date)) {
                    deleteId.add(transaction.getId());
                }
            for (Long id : deleteId)
                transactionRepository.deleteTransactionById(id);
            if(deleteId.isEmpty())
                this.undo();
            return deleteId.size();
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public int deleteTransactionFromInterval(String dateStart, String dateEnd) {
        /**
         * Deletes transactions between two dates
         * @param dateStart: the start date of transactions (String)
         * @param dateEnd: the end date of transactions (String)
         * @return number of transactions deleted (int)
         */
        this.makeCopy();
        try {
            LocalDate startDate = LocalDate.parse(dateStart);
            LocalDate endDate = LocalDate.parse(dateEnd);
            List<Long> deleteId = new ArrayList<>();
            for (Transaction transaction : this.getAll())
                if (!transaction.getDate().isBefore(startDate) &&
                        !transaction.getDate().isAfter(endDate)) {
                    deleteId.add(transaction.getId());
                }
            for (Long id : deleteId)
                transactionRepository.deleteTransactionById(id);
            if (deleteId.isEmpty())
                this.undo();
            return deleteId.size();
        }
        catch (Exception e) {
            this.undo();
            throw e;
        }
    }

    public Transaction getTransactionById(long id) {
        /**
         * Gets a transaction by id from the repository
         * @param id: the id of the transaction (long)
         * @return the transaction with the id (Transaction)
         */
        return this.transactionRepository.getTransactionById(id);
    }

    public List<Transaction> getAll() {
        /**
         * Get all transactions from the repository
         * @return list of transactions (List<Transaction>)
         */
        return this.transactionRepository.getAll();
    }

    public List<Transaction> getAllWithMinAmountAndBeforeDate(Double minAmount, String dateEnd) {
        /**
         * Get all transactions from the repository with a minimum amount and before a date
         * @param minAmount: the minimum amount of the transactions (Double)
         * @param dateEnd: the end date of the transactions (String)
         * @return list of transactions (List<Transaction>)
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
         * @param minAmount: the minimum amount of the transactions (Double)
         * @return list of transactions (List<Transaction>)
         * */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(transaction.getAmount() > minAmount)
                transactions.add(transaction);
        return transactions;
    }

    public List<Transaction> getAllByType(String type) {
        /**
         * Get all transactions from the repository by a type
         * @param type: the type of transactions (String)
         * @return list of transactions (List<Transaction>)
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
         * @param type: the string of the transactions (String)
         * @return the sum of the transactions amount (Double)
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
         * @param date: the date (String)
         * @return the balance on a date (Double)
         */
        double balance = 0.0;
        LocalDate realDate = LocalDate.parse(date).plusDays(1);
        for(Transaction transaction : this.getAll())
            if(transaction.getDate().isBefore(realDate))
                if(transaction.getType().equals("enter"))
                    balance += transaction.getAmount();
                else
                    balance -= transaction.getAmount();
        return balance;
    }

    public List<Transaction> getAllWithoutType(String type) {
        /**
         * Get all transactions from the repository different from a type
         * @param type: the type of the transactions not to be returned (String)
         * @return list of transactions (List<Transaction>)
         */
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : this.getAll())
            if(!transaction.getType().equals(type))
                transactions.add(transaction);

        return transactions;
    }

    public List<Transaction> getAllByTypeWithMaxAmount(String type, Double maxAmount) {
        /**
         * Get all transactions from the repository with a type and with a max amount
         * @param type: the type of transactions (String)
         * @param maxAmount: the maximum amount of transactions (Double)
         * @return list of transactions (List<Transaction>)
         */
        List<Transaction> transactions = getAllByType(type);
        for(Transaction transaction : this.getAll())
            if(transaction.getAmount() < maxAmount)
                transactions.add(transaction);
        return transactions;
    }

    private long generateNextId(){
        /**
         * Generate next id for a transaction based on last maximum id
         * @return id (long)
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
