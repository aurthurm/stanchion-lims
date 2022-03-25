package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.LaboratoryRequestDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.LaboratoryRequest}.
 */
public interface LaboratoryRequestService {
    /**
     * Save a laboratoryRequest.
     *
     * @param laboratoryRequestDTO the entity to save.
     * @return the persisted entity.
     */
    LaboratoryRequestDTO save(LaboratoryRequestDTO laboratoryRequestDTO);

    /**
     * Partially updates a laboratoryRequest.
     *
     * @param laboratoryRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LaboratoryRequestDTO> partialUpdate(LaboratoryRequestDTO laboratoryRequestDTO);

    /**
     * Get all the laboratoryRequests.
     *
     * @return the list of entities.
     */
    List<LaboratoryRequestDTO> findAll();

    /**
     * Get the "id" laboratoryRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LaboratoryRequestDTO> findOne(Long id);

    /**
     * Delete the "id" laboratoryRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
