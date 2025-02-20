package com.example.demo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(String date, double amount, String type) {
        Transaction transaction = new Transaction(generateNextId(), LocalDate.parse(date), amount, type);
        transactionRepository.addTransaction(transaction);
    }

    public Transaction getTransactionById(long id) {
        return this.transactionRepository.getTransactionById(id);
    }

    public List<Transaction> getAll() {
        return this.transactionRepository.getAll();
    }

    private long generateNextId(){
        long id = 0;
        for(Transaction transaction : this.getAll()){
            id = Math.max(id, transaction.getId());
        }
        return id + 1;
    }

    public void updateTransaction(long id, String date, double amount, String type) {
        this.transactionRepository.updateTransaction(id, new Transaction(LocalDate.parse(date), amount, type));
    }

    public void deleteTransaction(long id) {
        this.transactionRepository.deleteTransaction(id);
    }

    public void deleteTransactionsByType(String type) {
    }

    public void deleteTransactionFromDay(String dateStart) {
    }

    public void deleteTransactionFromInterval(String dateStart, String dateEnd) {
    }
}
