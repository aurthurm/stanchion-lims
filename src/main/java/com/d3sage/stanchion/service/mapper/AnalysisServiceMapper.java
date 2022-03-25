package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.AnalysisService;
import com.d3sage.stanchion.service.dto.AnalysisServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalysisService} and its DTO {@link AnalysisServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalysisServiceMapper extends EntityMapper<AnalysisServiceDTO, AnalysisService> {}
