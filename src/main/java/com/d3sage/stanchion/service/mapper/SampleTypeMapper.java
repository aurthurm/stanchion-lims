package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.SampleType;
import com.d3sage.stanchion.service.dto.SampleTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SampleType} and its DTO {@link SampleTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SampleTypeMapper extends EntityMapper<SampleTypeDTO, SampleType> {}
