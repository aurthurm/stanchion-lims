package com.d3sage.stanchion.service.mapper;

import com.d3sage.stanchion.domain.Task;
import com.d3sage.stanchion.service.dto.TaskDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Named("titleSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    Set<TaskDTO> toDtoTitleSet(Set<Task> task);
}
