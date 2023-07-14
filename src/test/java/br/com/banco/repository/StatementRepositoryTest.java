package br.com.banco.repository;

import br.com.banco.Entity.Account;
import br.com.banco.Entity.Transaction;
import br.com.banco.Repository.AccountRepository;
import br.com.banco.Repository.StatementRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class StatementRepositoryTest {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private AccountRepository accountRepository;


    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setResponsibleName("Fulano");
        account = accountRepository.save(account);

        transaction1 = new Transaction();
        transaction1.setTransactionDate(LocalDateTime.now());
        transaction1.setAmount(BigDecimal.valueOf(100.0));
        transaction1.setType("DEPOSITO");
        transaction1.setTransactionOperatorName("Operator1");
        transaction1.setAccount(account);
        statementRepository.save(transaction1);

        transaction2 = new Transaction();
        transaction2.setTransactionDate(LocalDateTime.now());
        transaction2.setAmount(BigDecimal.valueOf(50.0));
        transaction2.setType("SAQUE");
        transaction2.setTransactionOperatorName("Operator2");
        transaction2.setAccount(account);
        statementRepository.save(transaction2);

        transaction3 = new Transaction();
        transaction3.setTransactionDate(LocalDateTime.now());
        transaction3.setAmount(BigDecimal.valueOf(30.0));
        transaction3.setType("SAQUE");
        transaction3.setTransactionOperatorName("Operator3");
        transaction3.setAccount(account);
        statementRepository.save(transaction3);
    }

    @AfterEach
    public void tearDown() {
        statementRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testFindAllByAccountAccountId() {
        List<Transaction> transactions = statementRepository.findAllByAccountAccountId(1L);

        Assertions.assertEquals(3, transactions.size());
        Assertions.assertTrue(transactions.contains(transaction1));
        Assertions.assertTrue(transactions.contains(transaction2));
        Assertions.assertTrue(transactions.contains(transaction3));
    }

    @Test
    public void testFindAllByTransactionDateBetween() {
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59);

        List<Transaction> transactions = statementRepository.findAllByAccountAccountIdAndTransactionDateBetween(1L, startTime, endTime);

        Assertions.assertEquals(3, transactions.size());
        Assertions.assertTrue(transactions.contains(transaction1));
        Assertions.assertTrue(transactions.contains(transaction2));
        Assertions.assertTrue(transactions.contains(transaction3));
    }

    @Test
    public void testFindAllByTransactionOperatorName() {
        List<Transaction> transactions = statementRepository.findAllByAccountAccountIdAndTransactionOperatorName(1L,"Operator2");

        Assertions.assertEquals(1, transactions.size());
        Assertions.assertTrue(transactions.contains(transaction2));
    }

    @Test
    public void testFindAllByTransactionDateBetweenAndTransactionOperatorName() {
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59);

        List<Transaction> transactions = statementRepository.findAllByAccountAccountIdAndTransactionDateBetweenAndTransactionOperatorName(1L,
                startTime, endTime, "Operator3");

        Assertions.assertEquals(1, transactions.size());
        Assertions.assertTrue(transactions.contains(transaction3));
    }
}
