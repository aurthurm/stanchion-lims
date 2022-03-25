package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.SampleTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.SampleType}.
 */
public interface SampleTypeService {
    /**
     * Save a sampleType.
     *
     * @param sampleTypeDTO the entity to save.
     * @return the persisted entity.
     */
    SampleTypeDTO save(SampleTypeDTO sampleTypeDTO);

    /**
     * Partially updates a sampleType.
     *
     * @param sampleTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SampleTypeDTO> partialUpdate(SampleTypeDTO sampleTypeDTO);

    /**
     * Get all the sampleTypes.
     *
     * @return the list of entities.
     */
    List<SampleTypeDTO> findAll();

    /**
     * Get the "id" sampleType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SampleTypeDTO> findOne(Long id);

    /**
     * Delete the "id" sampleType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
