package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Country;
import com.d3sage.stanchion.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProvinceMapper.class })
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "province", source = "province", qualifiedByName = "id")
    @Mapping(target = "province", source = "province", qualifiedByName = "name")
    CountryDTO toDto(Country s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoId(Country country);
}
