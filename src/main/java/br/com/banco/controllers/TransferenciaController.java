package br.com.banco.controllers;

import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.entities.Conta;
import br.com.banco.entities.Transferencia;
import br.com.banco.exceptions.ContaNaoEncontradaException;
import br.com.banco.mappers.TransferenciaMapper;
import br.com.banco.services.ContaService;
import br.com.banco.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "${apiPrefix}/transferencia")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<TransferenciaDTO> create(@Valid @RequestBody TransferenciaDTO transferenciaDTO) throws ContaNaoEncontradaException {
        Conta conta = this.contaService.encontrarConta(transferenciaDTO.getConta().getId());
        TransferenciaDTO dtoResposta = this.transferenciaService.criarTransferencia(transferenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResposta);
    }

    @GetMapping
    public ResponseEntity<List<Transferencia>> index(@RequestParam(value = "conta_id") Long conta_id,
                                                     @RequestParam(value = "operador", required = false) Optional<String> nome_operador_transacao,
                                                     @RequestParam(value = "inicio", required = false) Optional<String> inicio,
                                                     @RequestParam(value = "fim", required = false) Optional<String> fim) {

        List<Transferencia> transferencias = this.transferenciaService.encontrarTransferencias(conta_id, nome_operador_transacao, inicio, fim);
        return ResponseEntity.status(HttpStatus.OK).body(transferencias);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferenciaDTO> indexOne(@PathVariable Long id) {
        TransferenciaDTO transferencia = this.transferenciaService.encontrarUmaTransferencia(id);
        return ResponseEntity.status(HttpStatus.OK).body(transferencia);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        this.transferenciaService.encontrarUmaTransferencia(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
