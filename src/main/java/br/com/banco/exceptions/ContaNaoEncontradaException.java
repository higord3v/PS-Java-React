package br.com.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaNaoEncontradaException extends Exception {
    public ContaNaoEncontradaException(Long id) {
    }
}