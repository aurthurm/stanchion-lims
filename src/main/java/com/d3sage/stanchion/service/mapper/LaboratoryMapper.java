package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Laboratory;
import com.d3sage.stanchion.service.dto.LaboratoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Laboratory} and its DTO {@link LaboratoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LaboratoryMapper extends EntityMapper<LaboratoryDTO, Laboratory> {}
