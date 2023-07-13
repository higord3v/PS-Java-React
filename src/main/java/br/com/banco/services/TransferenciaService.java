package br.com.banco.services;

import br.com.banco.entities.Transferencia;
import br.com.banco.repositories.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {
    @Autowired
    private TransferenciaRepository repository;

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
}
