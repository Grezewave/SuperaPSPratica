package br.com.banco.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CONTA")
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "id_conta")
    private Long accountId;

    @Column(name = "nome_responsavel", nullable = false)
    private String responsibleName;

}
