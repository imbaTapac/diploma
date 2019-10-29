package com.student.rating.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Common interface that used in process mapping entity/dto
 * @param <E> entity
 * @param <D> dto
 *
 * @since 0.7
 */
public interface AbstractDTOMapper<E, D> {

    default E toEntity(D dto) {
        return null;
    }

    default D toDto(E entity) {
        return null;
    }

    default List<D> toDTOs(List<E> entities) {
        return entities.stream().
                map(this::toDto).
                collect(Collectors.toList());
    }
}
