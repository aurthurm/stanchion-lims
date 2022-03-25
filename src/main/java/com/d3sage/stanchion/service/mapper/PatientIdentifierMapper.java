package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.PatientIdentifier;
import com.d3sage.stanchion.service.dto.PatientIdentifierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientIdentifier} and its DTO {@link PatientIdentifierDTO}.
 */
@Mapper(componentModel = "spring", uses = { PatientIdentifierTypeMapper.class })
public interface PatientIdentifierMapper extends EntityMapper<PatientIdentifierDTO, PatientIdentifier> {
    @Mapping(target = "type", source = "type", qualifiedByName = "id")
    PatientIdentifierDTO toDto(PatientIdentifier s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientIdentifierDTO toDtoId(PatientIdentifier patientIdentifier);
}
