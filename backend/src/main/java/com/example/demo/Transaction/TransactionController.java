package com.example.demo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/get")
    public List<Transaction> getAll() {
        return this.transactionService.getAll();
    }

    @PostMapping("/add")
    public void add(@RequestParam String date, double amount, String type) {
        this.transactionService.addTransaction(date, amount, type);
    }

    @PutMapping("/update")
    public void update(@RequestParam long id, String date, double amount, String type) {
        this.transactionService.updateTransaction(id, date, amount, type);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(required = false) Long id, String dateStart, String dateEnd, String type) {
        if(id != null)
            this.transactionService.deleteTransaction(id.longValue());
        else{
            if(dateStart == null)
                this.transactionService.deleteTransactionsByType(type);
            else if(dateEnd == null)
                this.transactionService.deleteTransactionFromDay(dateStart);
            else
                this.transactionService.deleteTransactionFromInterval(dateStart, dateEnd);
        }
    }
}
