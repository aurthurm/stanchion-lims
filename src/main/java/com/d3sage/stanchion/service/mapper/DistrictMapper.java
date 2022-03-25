package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.District;
import com.d3sage.stanchion.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoName(District district);
}
