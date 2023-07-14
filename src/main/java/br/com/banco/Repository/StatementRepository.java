package br.com.banco.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import br.com.banco.Entity.Transaction;


@Repository
@Component
public interface StatementRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccountAccountId(Long contaId);

    List<Transaction> findAll();

    List<Transaction> findAllByAccountAccountIdAndTransactionDateBetween( Long accountId, LocalDateTime startTime, LocalDateTime endTime);

    List<Transaction> findAllByAccountAccountIdAndTransactionOperatorName(Long accountId, String operatorName);

    List<Transaction> findAllByAccountAccountIdAndTransactionDateBetweenAndTransactionOperatorName( 
            Long accountId, LocalDateTime startTime, LocalDateTime endTime, String operatorName);

}
