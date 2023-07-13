package br.com.banco.repositories;

import br.com.banco.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaId(Long id);

    List<Transferencia> findByDataTransferenciaBetweenAndContaIdAndNomeOperadorTransacao(OffsetDateTime data_inicio, OffsetDateTime data_fim, Long id, String nome_operador_transacao);

    List<Transferencia> findByDataTransferenciaBetweenAndContaId(OffsetDateTime dataInicio, OffsetDateTime dataFim, Long id);
    List<Transferencia> findByNomeOperadorTransacaoAndContaId(String nome_operador_transacao, Long id);
}
