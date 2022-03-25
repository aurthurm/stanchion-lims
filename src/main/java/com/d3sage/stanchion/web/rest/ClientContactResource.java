package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.ClientContactRepository;
import com.d3sage.stanchion.service.ClientContactService;
import com.d3sage.stanchion.service.dto.ClientContactDTO;
import com.d3sage.stanchion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.d3sage.stanchion.domain.ClientContact}.
 */
@RestController
@RequestMapping("/api")
public class ClientContactResource {

    private final Logger log = LoggerFactory.getLogger(ClientContactResource.class);

    private static final String ENTITY_NAME = "clientContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientContactService clientContactService;

    private final ClientContactRepository clientContactRepository;

    public ClientContactResource(ClientContactService clientContactService, ClientContactRepository clientContactRepository) {
        this.clientContactService = clientContactService;
        this.clientContactRepository = clientContactRepository;
    }

    /**
     * {@code POST  /client-contacts} : Create a new clientContact.
     *
     * @param clientContactDTO the clientContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientContactDTO, or with status {@code 400 (Bad Request)} if the clientContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-contacts")
    public ResponseEntity<ClientContactDTO> createClientContact(@RequestBody ClientContactDTO clientContactDTO) throws URISyntaxException {
        log.debug("REST request to save ClientContact : {}", clientContactDTO);
        if (clientContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientContactDTO result = clientContactService.save(clientContactDTO);
        return ResponseEntity
            .created(new URI("/api/client-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-contacts/:id} : Updates an existing clientContact.
     *
     * @param id the id of the clientContactDTO to save.
     * @param clientContactDTO the clientContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientContactDTO,
     * or with status {@code 400 (Bad Request)} if the clientContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-contacts/{id}")
    public ResponseEntity<ClientContactDTO> updateClientContact(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientContactDTO clientContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClientContact : {}, {}", id, clientContactDTO);
        if (clientContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientContactDTO result = clientContactService.save(clientContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-contacts/:id} : Partial updates given fields of an existing clientContact, field will ignore if it is null
     *
     * @param id the id of the clientContactDTO to save.
     * @param clientContactDTO the clientContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientContactDTO,
     * or with status {@code 400 (Bad Request)} if the clientContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clientContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientContactDTO> partialUpdateClientContact(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientContactDTO clientContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientContact partially : {}, {}", id, clientContactDTO);
        if (clientContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientContactDTO> result = clientContactService.partialUpdate(clientContactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientContactDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /client-contacts} : get all the clientContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientContacts in body.
     */
    @GetMapping("/client-contacts")
    public List<ClientContactDTO> getAllClientContacts() {
        log.debug("REST request to get all ClientContacts");
        return clientContactService.findAll();
    }

    /**
     * {@code GET  /client-contacts/:id} : get the "id" clientContact.
     *
     * @param id the id of the clientContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-contacts/{id}")
    public ResponseEntity<ClientContactDTO> getClientContact(@PathVariable Long id) {
        log.debug("REST request to get ClientContact : {}", id);
        Optional<ClientContactDTO> clientContactDTO = clientContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientContactDTO);
    }

    /**
     * {@code DELETE  /client-contacts/:id} : delete the "id" clientContact.
     *
     * @param id the id of the clientContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-contacts/{id}")
    public ResponseEntity<Void> deleteClientContact(@PathVariable Long id) {
        log.debug("REST request to delete ClientContact : {}", id);
        clientContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
