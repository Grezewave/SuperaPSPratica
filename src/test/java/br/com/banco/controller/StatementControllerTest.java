package br.com.banco.controller;

import br.com.banco.Controller.StatementController;
import br.com.banco.Entity.Transaction;
import br.com.banco.Service.StatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class StatementControllerTest {

    @Mock
    private StatementService statementService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        StatementController statementController = new StatementController(statementService);
        mockMvc = MockMvcBuilders.standaloneSetup(statementController).build();
    }

    @Test
    public void testGetStatement_WithAllFilters() throws Exception {
        Long accountId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59);
        String operatorName = "Operator1";

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementService.getTransactionsByAccountAndFilters(
                accountId, startTime, endTime, operatorName)).thenReturn(expectedTransactions);

        mockMvc.perform(get("/client/{accountNumber}/statement", accountId)
                .param("startTime", startTime.toString())
                .param("endTime", endTime.toString())
                .param("operatorName", operatorName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expectedTransactions.size()));

        verify(statementService, times(1)).getTransactionsByAccountAndFilters(
                accountId, startTime, endTime, operatorName);
    }

    @Test
    public void testGetStatement_WithoutFilters() throws Exception {
        Long accountId = 1L;

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(statementService.getTransactionsByAccountAndFilters(
                accountId, null, null, null)).thenReturn(expectedTransactions);

        mockMvc.perform(get("/client/{accountNumber}/statement", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expectedTransactions.size()));

        verify(statementService, times(1)).getTransactionsByAccountAndFilters(
                accountId, null, null, null);
    }
}
