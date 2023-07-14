package br.com.banco.Service;

import br.com.banco.Entity.Transaction;
import br.com.banco.Repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatementService {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public List<Transaction> getTransactionsByAccountAndFilters(Long accountId, LocalDateTime startTime, LocalDateTime endTime, String operatorName) {
        if (startTime != null && endTime != null && operatorName != null) {
            return statementRepository.findAllByAccountAccountIdAndTransactionDateBetweenAndTransactionOperatorName(
                    accountId, startTime, endTime, operatorName);
        } else if (startTime != null && endTime != null) {
            return statementRepository.findAllByAccountAccountIdAndTransactionDateBetween(
                    accountId, startTime, endTime);
        } else if (operatorName != null) {
            return statementRepository.findAllByAccountAccountIdAndTransactionOperatorName(
                    accountId, operatorName);
        } else {
            return statementRepository.findAllByAccountAccountId(accountId);
        }
    }
}

