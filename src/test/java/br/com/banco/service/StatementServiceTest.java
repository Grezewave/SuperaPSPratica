package br.com.banco.service;

import br.com.banco.Entity.Transaction;
import br.com.banco.Repository.StatementRepository;
import br.com.banco.Service.StatementService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatementServiceTest {

    @Mock
    private StatementRepository statementRepository;

    private StatementService statementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        statementService = new StatementService(statementRepository);
    }

    @Test
    public void testGetTransactionsByAccountAndFilters_WithAllFilters() {
        Long accountId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59);
        String operatorName = "Operator1";

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementRepository.findAllByAccountAccountIdAndTransactionDateBetweenAndTransactionOperatorName(
                accountId, startTime, endTime, operatorName)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = statementService.getTransactionsByAccountAndFilters(
                accountId, startTime, endTime, operatorName);

        Assertions.assertEquals(expectedTransactions.size(), actualTransactions.size());
        Assertions.assertEquals(expectedTransactions, actualTransactions);

        verify(statementRepository, times(1))
                .findAllByAccountAccountIdAndTransactionDateBetweenAndTransactionOperatorName(
                        accountId, startTime, endTime, operatorName);
    }

    @Test
    public void testGetTransactionsByAccountAndFilters_WithTimeRangeFilter() {
        Long accountId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59);

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementRepository.findAllByAccountAccountIdAndTransactionDateBetween(
                accountId, startTime, endTime)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = statementService.getTransactionsByAccountAndFilters(
                accountId, startTime, endTime, null);

        Assertions.assertEquals(expectedTransactions.size(), actualTransactions.size());
        Assertions.assertEquals(expectedTransactions, actualTransactions);

        verify(statementRepository, times(1))
                .findAllByAccountAccountIdAndTransactionDateBetween(accountId, startTime, endTime);
    }

    @Test
    public void testGetTransactionsByAccountAndFilters_WithOperatorNameFilter() {
        Long accountId = 1L;
        String operatorName = "Operator1";

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementRepository.findAllByAccountAccountIdAndTransactionOperatorName(
                accountId, operatorName)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = statementService.getTransactionsByAccountAndFilters(
                accountId, null, null, operatorName);

        Assertions.assertEquals(expectedTransactions.size(), actualTransactions.size());
        Assertions.assertEquals(expectedTransactions, actualTransactions);

        verify(statementRepository, times(1))
                .findAllByAccountAccountIdAndTransactionOperatorName(accountId, operatorName);
    }

    @Test
    public void testGetTransactionsByAccountAndFilters_WithoutFilters() {
        Long accountId = 1L;

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementRepository.findAllByAccountAccountId(accountId)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = statementService.getTransactionsByAccountAndFilters(
                accountId, null, null, null);

        Assertions.assertEquals(expectedTransactions.size(), actualTransactions.size());
        Assertions.assertEquals(expectedTransactions, actualTransactions);

        verify(statementRepository, times(1)).findAllByAccountAccountId(accountId);
    }
}
