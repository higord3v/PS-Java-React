package br.com.banco.services;

import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.entities.Conta;
import br.com.banco.entities.Transferencia;
import br.com.banco.exceptions.TransferenciaNaoEncontradaException;
import br.com.banco.mappers.TransferenciaMapper;
import br.com.banco.repositories.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {
    @Mock
    private TransferenciaRepository repository;

    private TransferenciaService transferenciaService;

    // Create a mock instance of Transferencia
    @Mock
    Transferencia transferenciaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferenciaService = new TransferenciaService();
        transferenciaService.setRepository(repository);
    }

    @Test
    void testEncontrarTransferencias() {
        // Mock data
        Long id = 1L;
        Optional<String> nomeOperadorTransacao = Optional.of("John Doe");
        Optional<String> inicio = Optional.of("2023-07-01T00:00:00Z");
        Optional<String> fim = Optional.of("2023-07-31T23:59:59Z");
        Long idMock = 1L;
        Long idMock2 = 2L;
        OffsetDateTime dataTransferenciaMock = OffsetDateTime.now();
        BigDecimal valorMock = BigDecimal.TEN;
        String tipoMock = "Tipo";
        String nomeOperadorTransacaoMock = "John Doe";
        Conta contaMock = mock(Conta.class);


        OffsetDateTime dataInicio = OffsetDateTime.parse(inicio.get());
        OffsetDateTime dataFim = OffsetDateTime.parse(fim.get());

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia(
                idMock,
                dataTransferenciaMock,
                valorMock,
                tipoMock,
                nomeOperadorTransacaoMock,
                contaMock
        ));
        transferencias.add(new Transferencia(
                idMock,
                dataTransferenciaMock,
                valorMock,
                tipoMock,
                nomeOperadorTransacaoMock,
                contaMock
        ));

        // Mock the repository behavior
        when(repository.findByDataTransferenciaBetweenAndContaIdAndNomeOperadorTransacao(
                dataInicio, dataFim, id, nomeOperadorTransacao.get()))
                .thenReturn(transferencias);

        // Invoke the service method
        List<Transferencia> result = transferenciaService.encontrarTransferencias(
                id, nomeOperadorTransacao, inicio, fim);

        // Verify the result
        assertEquals(transferencias.size(), result.size());
        // Add additional assertions for each transferencia if needed
        verify(repository, times(1))
                .findByDataTransferenciaBetweenAndContaIdAndNomeOperadorTransacao(
                        dataInicio, dataFim, id, nomeOperadorTransacao.get());
    }

    @Test
    void testEncontrarUmaTransferencia() throws TransferenciaNaoEncontradaException {
        // Mock data
        Long id = 1L;

        TransferenciaDTO expectedDTO = TransferenciaMapper.INSTANCE.transferenciaToTransferenciaDto(transferenciaMock);

        // Mock the repository behavior
        when(repository.findById(id)).thenReturn(Optional.of(transferenciaMock));

        // Invoke the service method
        TransferenciaDTO result = transferenciaService.encontrarUmaTransferencia(id);

        // Verify the result
        assertEquals(expectedDTO.getConta(), result.getConta());
        assertEquals(expectedDTO.getDataTransferencia(), result.getDataTransferencia());
        assertEquals(expectedDTO.getNomeOperadorTransacao(), result.getNomeOperadorTransacao());
        // Add additional assertions for each property if needed
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testCriarTransferencia() {
        // Mock data
        Conta conta = new Conta(3L, "Higor");
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(
                OffsetDateTime.now(),
                BigDecimal.TEN,
                "Description",
                "John Doe",
                conta
        );
        TransferenciaDTO expectedDTO = new TransferenciaDTO(
                transferenciaDTO.getDataTransferencia(),
                transferenciaDTO.getValor(),
                transferenciaDTO.getTipo(),
                transferenciaDTO.getNomeOperadorTransacao(),
                transferenciaDTO.getConta()
        );

        // Mock the repository behavior
        when(repository.save(any(Transferencia.class))).thenAnswer(invocation -> {
            Transferencia savedTransferencia = invocation.getArgument(0);
            return savedTransferencia;
        });

        // Invoke the service method
        TransferenciaDTO result = this.transferenciaService.criarTransferencia(transferenciaDTO);

        // Verify the result is not null
        assertNotNull(result, "The created TransferenciaDTO should not be null.");

        // Verify the properties of the result
        assertEquals(expectedDTO.getConta(), result.getConta());
        assertEquals(expectedDTO.getDataTransferencia(), result.getDataTransferencia());
        assertEquals(expectedDTO.getNomeOperadorTransacao(), result.getNomeOperadorTransacao());

        // Verify the repository method was called
        verify(repository, times(1)).save(any(Transferencia.class));
    }




    @Test
    void testDeletarTransferencia() throws TransferenciaNaoEncontradaException {
        // Mock data
        Long id = 1L;

        // Invoke the service method
        transferenciaService.deletarTransferencia(id);

        // Verify the repository method was called
        verify(repository, times(1)).deleteById(id);
    }
}
