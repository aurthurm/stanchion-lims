package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.PatientIdentifierType;
import com.d3sage.stanchion.service.dto.PatientIdentifierTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientIdentifierType} and its DTO {@link PatientIdentifierTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatientIdentifierTypeMapper extends EntityMapper<PatientIdentifierTypeDTO, PatientIdentifierType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientIdentifierTypeDTO toDtoId(PatientIdentifierType patientIdentifierType);
}
