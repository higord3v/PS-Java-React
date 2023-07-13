package br.com.banco.controllers;

import br.com.banco.entities.Transferencia;
import br.com.banco.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "${apiPrefix}")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping(value = "/{conta_id}/transferencia")
    public ResponseEntity<List<Transferencia>> index(@PathVariable Long conta_id,
                                                     @RequestParam(value = "operador", required = false) Optional<String> nome_operador_transacao,
                                                     @RequestParam(value = "inicio", required = false) Optional<String> inicio,
                                                     @RequestParam(value = "fim", required = false) Optional<String> fim) {

        List<Transferencia> transferencias = this.transferenciaService.encontrarTransferencias(conta_id, nome_operador_transacao, inicio, fim);
        return ResponseEntity.status(HttpStatus.OK).body(transferencias);
    }
}
