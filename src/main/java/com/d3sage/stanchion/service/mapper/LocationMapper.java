package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Location;
import com.d3sage.stanchion.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "country", source = "country", qualifiedByName = "id")
    LocationDTO toDto(Location s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoId(Location location);
}
