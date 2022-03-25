package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Sample;
import com.d3sage.stanchion.service.dto.SampleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sample} and its DTO {@link SampleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SampleMapper extends EntityMapper<SampleDTO, Sample> {}
