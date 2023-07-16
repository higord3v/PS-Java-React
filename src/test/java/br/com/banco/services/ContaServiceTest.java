package br.com.banco.services;

import br.com.banco.entities.Conta;
import br.com.banco.exceptions.ContaNaoEncontradaException;
import br.com.banco.repositories.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceTest {
    @Mock
    private ContaRepository repository;

    private ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contaService = new ContaService();
        contaService.setRepository(repository);
    }

    @Test
    void testBuscarContas() {
        // Mock data
        List<Conta> contas = new ArrayList<>();
        contas.add(new Conta(4L, "Maria"));
        contas.add(new Conta(3L, "Joao"));

        // Mock the repository behavior
        when(repository.findAll()).thenReturn(contas);

        // Invoke the service method
        List<Conta> result = contaService.buscarContas();

        // Verify the result
        assertEquals(contas, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testCriarConta() {
        // Mock data
        Conta conta = new Conta(5L, "Joao");
        Conta contaSalva = new Conta(4L, "Maria");

        // Mock the repository behavior
        when(repository.save(conta)).thenReturn(contaSalva);

        // Invoke the service method
        Conta result = contaService.criarConta(conta);

        // Verify the result
        assertEquals(contaSalva, result);
        verify(repository, times(1)).save(conta);
    }

    @Test
    void testEncontrarConta() throws ContaNaoEncontradaException {
        // Mock data
        Long id = 1L;
        Conta conta = new Conta(5L, "Joao");

        // Mock the repository behavior
        when(repository.findById(id)).thenReturn(Optional.of(conta));

        // Invoke the service method
        Conta result = contaService.encontrarConta(id);

        // Verify the result
        assertEquals(conta, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testEncontrarConta_NaoEncontrada() {
        // Mock data
        Long id = 1L;

        // Mock the repository behavior
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Invoke and verify the exception
        assertThrows(ContaNaoEncontradaException.class, () -> {
            contaService.encontrarConta(id);
        });
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testDeletarConta() throws ContaNaoEncontradaException {
        // Mock data
        Long id = 1L;
        Conta conta = new Conta(5L, "Joao");

        // Mock the repository behavior
        when(repository.findById(id)).thenReturn(Optional.of(conta));

        // Invoke the service method
        assertDoesNotThrow(() -> {
            contaService.deletarConta(id);
        });

        // Verify the repository method was called
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testDeletarConta_NaoEncontrada() {
        // Mock data
        Long id = 1L;

        // Mock the repository behavior
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Invoke and verify the exception
        assertThrows(ContaNaoEncontradaException.class, () -> {
            contaService.deletarConta(id);
        });

        // Verify the repository method was called
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(id);
    }
}
