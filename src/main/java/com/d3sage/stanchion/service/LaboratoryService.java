package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.LaboratoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.Laboratory}.
 */
public interface LaboratoryService {
    /**
     * Save a laboratory.
     *
     * @param laboratoryDTO the entity to save.
     * @return the persisted entity.
     */
    LaboratoryDTO save(LaboratoryDTO laboratoryDTO);

    /**
     * Partially updates a laboratory.
     *
     * @param laboratoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LaboratoryDTO> partialUpdate(LaboratoryDTO laboratoryDTO);

    /**
     * Get all the laboratories.
     *
     * @return the list of entities.
     */
    List<LaboratoryDTO> findAll();

    /**
     * Get the "id" laboratory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LaboratoryDTO> findOne(Long id);

    /**
     * Delete the "id" laboratory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
