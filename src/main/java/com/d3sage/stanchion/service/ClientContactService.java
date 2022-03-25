package com.d3sage.stanchion.service;

import com.d3sage.stanchion.service.dto.ClientContactDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.d3sage.stanchion.domain.ClientContact}.
 */
public interface ClientContactService {
    /**
     * Save a clientContact.
     *
     * @param clientContactDTO the entity to save.
     * @return the persisted entity.
     */
    ClientContactDTO save(ClientContactDTO clientContactDTO);

    /**
     * Partially updates a clientContact.
     *
     * @param clientContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientContactDTO> partialUpdate(ClientContactDTO clientContactDTO);

    /**
     * Get all the clientContacts.
     *
     * @return the list of entities.
     */
    List<ClientContactDTO> findAll();

    /**
     * Get the "id" clientContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientContactDTO> findOne(Long id);

    /**
     * Delete the "id" clientContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
