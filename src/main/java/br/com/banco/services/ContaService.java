package br.com.banco.services;

import br.com.banco.entities.Conta;
import br.com.banco.exceptions.ContaNaoEncontradaException;
import br.com.banco.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public ContaRepository getRepository() {
        return repository;
    }

    public void setRepository(ContaRepository repository) {
        this.repository = repository;
    }

    public List<Conta> buscarContas() {
        return this.repository.findAll();
    }

    public Conta criarConta(Conta conta) {
        return this.repository.save(conta);
    }

    public Conta encontrarConta(Long id) throws ContaNaoEncontradaException {
        return this.repository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
    }

    public void deletarConta(Long id) throws ContaNaoEncontradaException {
        this.repository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
        this.repository.deleteById(id);
    }
}
