package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.SampleDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.Sample}.
 */
public interface SampleService {
    /**
     * Save a sample.
     *
     * @param sampleDTO the entity to save.
     * @return the persisted entity.
     */
    SampleDTO save(SampleDTO sampleDTO);

    /**
     * Partially updates a sample.
     *
     * @param sampleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SampleDTO> partialUpdate(SampleDTO sampleDTO);

    /**
     * Get all the samples.
     *
     * @return the list of entities.
     */
    List<SampleDTO> findAll();

    /**
     * Get the "id" sample.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SampleDTO> findOne(Long id);

    /**
     * Delete the "id" sample.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
