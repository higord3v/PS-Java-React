package br.com.banco.controllers;

import br.com.banco.entities.Conta;
import br.com.banco.exceptions.ContaNaoEncontradaException;
import br.com.banco.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "${apiPrefix}/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Conta>> index() {
        List<Conta> contas = this.contaService.buscarContas();
        return ResponseEntity.status(HttpStatus.OK).body(contas);
    }

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody Conta conta) {
        Conta contaCriada = this.contaService.criarConta(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> indexOne(@PathVariable Long id) throws ContaNaoEncontradaException {
        Conta contaRecuperada = this.contaService.encontrarConta(id);
        return ResponseEntity.status(HttpStatus.OK).body(contaRecuperada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) throws ContaNaoEncontradaException {
        this.contaService.deletarConta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
