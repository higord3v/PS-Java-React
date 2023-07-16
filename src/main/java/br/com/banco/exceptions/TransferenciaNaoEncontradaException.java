package br.com.banco.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransferenciaNaoEncontradaException extends Exception {
    public TransferenciaNaoEncontradaException(Long id) {
    }
}