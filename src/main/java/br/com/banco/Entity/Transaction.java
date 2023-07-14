package br.com.banco.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSFERENCIA")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_transferencia", nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, name = "valor")
    private BigDecimal amount;

    @Column(nullable = false, name = "tipo")
    private String type;

    @Column(name = "nome_operador_transacao")
    private String transactionOperatorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", referencedColumnName="id_conta", nullable = false)
    private Account account;

}
