package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.PatientIdentifierDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.PatientIdentifier}.
 */
public interface PatientIdentifierService {
    /**
     * Save a patientIdentifier.
     *
     * @param patientIdentifierDTO the entity to save.
     * @return the persisted entity.
     */
    PatientIdentifierDTO save(PatientIdentifierDTO patientIdentifierDTO);

    /**
     * Partially updates a patientIdentifier.
     *
     * @param patientIdentifierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientIdentifierDTO> partialUpdate(PatientIdentifierDTO patientIdentifierDTO);

    /**
     * Get all the patientIdentifiers.
     *
     * @return the list of entities.
     */
    List<PatientIdentifierDTO> findAll();

    /**
     * Get the "id" patientIdentifier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientIdentifierDTO> findOne(Long id);

    /**
     * Delete the "id" patientIdentifier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
