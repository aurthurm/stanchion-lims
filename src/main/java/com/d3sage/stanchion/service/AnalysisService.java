package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.AnalysisDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.Analysis}.
 */
public interface AnalysisService {
    /**
     * Save a analysis.
     *
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisDTO save(AnalysisDTO analysisDTO);

    /**
     * Partially updates a analysis.
     *
     * @param analysisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalysisDTO> partialUpdate(AnalysisDTO analysisDTO);

    /**
     * Get all the analyses.
     *
     * @return the list of entities.
     */
    List<AnalysisDTO> findAll();

    /**
     * Get the "id" analysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalysisDTO> findOne(Long id);

    /**
     * Delete the "id" analysis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
