package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Employee;
import com.d3sage.stanchion.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DepartmentMapper.class })
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "manager", source = "manager", qualifiedByName = "id")
    @Mapping(target = "department", source = "department", qualifiedByName = "id")
    EmployeeDTO toDto(Employee s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoId(Employee employee);
}
