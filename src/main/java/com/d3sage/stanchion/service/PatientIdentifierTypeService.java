package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.PatientIdentifierTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.PatientIdentifierType}.
 */
public interface PatientIdentifierTypeService {
    /**
     * Save a patientIdentifierType.
     *
     * @param patientIdentifierTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PatientIdentifierTypeDTO save(PatientIdentifierTypeDTO patientIdentifierTypeDTO);

    /**
     * Partially updates a patientIdentifierType.
     *
     * @param patientIdentifierTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientIdentifierTypeDTO> partialUpdate(PatientIdentifierTypeDTO patientIdentifierTypeDTO);

    /**
     * Get all the patientIdentifierTypes.
     *
     * @return the list of entities.
     */
    List<PatientIdentifierTypeDTO> findAll();

    /**
     * Get the "id" patientIdentifierType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientIdentifierTypeDTO> findOne(Long id);

    /**
     * Delete the "id" patientIdentifierType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
