package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Patient;
import com.d3sage.stanchion.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring", uses = { PatientIdentifierMapper.class, LaboratoryRequestMapper.class })
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Mapping(target = "identifier", source = "identifier", qualifiedByName = "id")
    @Mapping(target = "laboratoryRequest", source = "laboratoryRequest", qualifiedByName = "clientRequestId")
    PatientDTO toDto(Patient s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoId(Patient patient);
}
