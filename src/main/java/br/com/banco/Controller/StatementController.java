package br.com.banco.Controller;

import br.com.banco.Entity.Transaction;
import br.com.banco.Service.StatementService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class StatementController {

    @Autowired
    private StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/client/{accountNumber}/statement")
    public List<Transaction> getStatement(
            @PathVariable String accountNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String operatorName) {

        Long accountId = Long.parseLong(accountNumber);

        List<Transaction> transactions = statementService.getTransactionsByAccountAndFilters(accountId, startTime, endTime, operatorName);

        log.info(transactions.toString());

        return transactions;
    }
}


