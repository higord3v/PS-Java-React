package br.com.banco.mappers;

import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.entities.Transferencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface TransferenciaMapper {
    TransferenciaMapper INSTANCE = Mappers.getMapper( TransferenciaMapper.class );

//    @Mapping(target = "contaId", source = "conta.id")
    TransferenciaDTO transferenciaToTransferenciaDto(Transferencia transferencia);
    Transferencia transferenciaDtoToTransferencia(TransferenciaDTO transferenciaDto);
}
