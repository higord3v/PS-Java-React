package br.com.banco.services;

import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.entities.Transferencia;
import br.com.banco.exceptions.TransferenciaNaoEncontradaException;
import br.com.banco.mappers.TransferenciaMapper;
import br.com.banco.repositories.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    private TransferenciaRepository repository;

    TransferenciaService(TransferenciaRepository repository) {
        this.repository = repository;
    }

    public TransferenciaRepository getRepository() {
        return repository;
    }

    public void setRepository(TransferenciaRepository repository) {
        this.repository = repository;
    }

    public List<Transferencia> encontrarTransferencias(Long id, Optional<String> nome_operador_transacao, Optional<String> inicio, Optional<String> fim) {
        if (nome_operador_transacao.get().isEmpty()) {
            if (inicio.get().isEmpty() || fim.get().isEmpty()) {
                return this.repository.findByContaId(id);
            }
            OffsetDateTime data_fim = OffsetDateTime.parse(fim.get());
            OffsetDateTime data_inicio = OffsetDateTime.parse(inicio.get());
            return this.repository.findByDataTransferenciaBetweenAndContaId(data_inicio, data_fim, id);
        }

        if (inicio.get().isEmpty() || fim.get().isEmpty()) {
            return this.repository.findByNomeOperadorTransacaoAndContaId(nome_operador_transacao.get(), id);
        }
        OffsetDateTime data_fim = OffsetDateTime.parse(fim.get());
        OffsetDateTime data_inicio = OffsetDateTime.parse(inicio.get());

        return this.repository.findByDataTransferenciaBetweenAndContaIdAndNomeOperadorTransacao(
                data_inicio,
                data_fim,
                id,
                nome_operador_transacao.get()
        );
    }

    public TransferenciaDTO encontrarUmaTransferencia(Long id) throws TransferenciaNaoEncontradaException {
        Optional<Transferencia> transferencia = this.repository.findById(id);
        if (!transferencia.isPresent()) throw new TransferenciaNaoEncontradaException(id);
        TransferenciaDTO dto = TransferenciaMapper.INSTANCE.transferenciaToTransferenciaDto(transferencia.get());
        return dto;
    }

    public Transferencia criarTransferencia(Transferencia transferencia) {
        Transferencia transferenciaSalva = this.repository.save(transferencia);
        return transferenciaSalva;
    }

    public void deletarTransferencia(Long id) throws TransferenciaNaoEncontradaException {
        Optional<Transferencia> transferencia = this.repository.findById(id);
        if (!transferencia.isPresent()) throw new TransferenciaNaoEncontradaException(id);
        this.repository.deleteById(id);
    }
}
