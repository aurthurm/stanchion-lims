package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.AnalysisProfile;
import com.d3sage.stanchion.service.dto.AnalysisProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalysisProfile} and its DTO {@link AnalysisProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalysisProfileMapper extends EntityMapper<AnalysisProfileDTO, AnalysisProfile> {}
