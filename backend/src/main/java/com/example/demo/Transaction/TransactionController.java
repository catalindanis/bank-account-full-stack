package com.example.demo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public String delete(@RequestParam(required = false) Long id, String dateStart, String dateEnd, String type) {
        if(id != null) {
            this.transactionService.deleteTransaction(id.longValue());
            return "";
        }
        int result;
        if(dateStart == null)
            result = this.transactionService.deleteTransactionsByType(type);
        else if(dateEnd == null)
            result = this.transactionService.deleteTransactionFromDate(dateStart);
        else
            result = this.transactionService.deleteTransactionFromInterval(dateStart, dateEnd);
        return String.valueOf(result);
    }
}
