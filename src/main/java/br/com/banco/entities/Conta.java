package br.com.banco.entities;

import javax.persistence.*;

@Entity
@Table(name="conta")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_conta")
    private Long id;

    @Column(name="nome_responsavel", length=50, nullable=false, unique=false)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
