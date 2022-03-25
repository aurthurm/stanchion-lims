package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.AnalysisServiceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.AnalysisService}.
 */
public interface AnalysisServiceService {
    /**
     * Save a analysisService.
     *
     * @param analysisServiceDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisServiceDTO save(AnalysisServiceDTO analysisServiceDTO);

    /**
     * Partially updates a analysisService.
     *
     * @param analysisServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalysisServiceDTO> partialUpdate(AnalysisServiceDTO analysisServiceDTO);

    /**
     * Get all the analysisServices.
     *
     * @return the list of entities.
     */
    List<AnalysisServiceDTO> findAll();

    /**
     * Get the "id" analysisService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalysisServiceDTO> findOne(Long id);

    /**
     * Delete the "id" analysisService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
