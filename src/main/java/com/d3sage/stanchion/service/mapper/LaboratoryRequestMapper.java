package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.LaboratoryRequest;
import com.d3sage.stanchion.service.dto.LaboratoryRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LaboratoryRequest} and its DTO {@link LaboratoryRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LaboratoryRequestMapper extends EntityMapper<LaboratoryRequestDTO, LaboratoryRequest> {
    @Named("clientRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientRequestId", source = "clientRequestId")
    LaboratoryRequestDTO toDtoClientRequestId(LaboratoryRequest laboratoryRequest);
}
