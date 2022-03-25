package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Client;
import com.d3sage.stanchion.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClientContactMapper.class, PatientMapper.class })
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "contact", source = "contact", qualifiedByName = "name")
    @Mapping(target = "patient", source = "patient", qualifiedByName = "name")
    ClientDTO toDto(Client s);
}
