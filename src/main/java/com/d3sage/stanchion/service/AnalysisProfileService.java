package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.AnalysisProfileDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.AnalysisProfile}.
 */
public interface AnalysisProfileService {
    /**
     * Save a analysisProfile.
     *
     * @param analysisProfileDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisProfileDTO save(AnalysisProfileDTO analysisProfileDTO);

    /**
     * Partially updates a analysisProfile.
     *
     * @param analysisProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalysisProfileDTO> partialUpdate(AnalysisProfileDTO analysisProfileDTO);

    /**
     * Get all the analysisProfiles.
     *
     * @return the list of entities.
     */
    List<AnalysisProfileDTO> findAll();

    /**
     * Get the "id" analysisProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalysisProfileDTO> findOne(Long id);

    /**
     * Delete the "id" analysisProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
