package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.ClientContact;
import com.d3sage.stanchion.service.dto.ClientContactDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientContact} and its DTO {@link ClientContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientContactMapper extends EntityMapper<ClientContactDTO, ClientContact> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ClientContactDTO toDtoName(ClientContact clientContact);
}
