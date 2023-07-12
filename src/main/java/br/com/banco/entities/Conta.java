package br.com.banco.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="conta")
@Getter
@Setter
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="nome_responsavel", length=50, nullable=false, unique=false)
    private String nome;
}
