package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Analysis;
import com.d3sage.stanchion.service.dto.AnalysisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Analysis} and its DTO {@link AnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalysisMapper extends EntityMapper<AnalysisDTO, Analysis> {}
