package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.LaboratoryRequestRepository;
import com.d3sage.stanchion.service.LaboratoryRequestService;
import com.d3sage.stanchion.service.dto.LaboratoryRequestDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.LaboratoryRequest}.
 */
@RestController
@RequestMapping("/api")
public class LaboratoryRequestResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestResource.class);

    private static final String ENTITY_NAME = "laboratoryRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryRequestService laboratoryRequestService;

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestResource(
        LaboratoryRequestService laboratoryRequestService,
        LaboratoryRequestRepository laboratoryRequestRepository
    ) {
        this.laboratoryRequestService = laboratoryRequestService;
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    /**
     * {@code POST  /laboratory-requests} : Create a new laboratoryRequest.
     *
     * @param laboratoryRequestDTO the laboratoryRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratoryRequestDTO, or with status {@code 400 (Bad Request)} if the laboratoryRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratory-requests")
    public ResponseEntity<LaboratoryRequestDTO> createLaboratoryRequest(@RequestBody LaboratoryRequestDTO laboratoryRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save LaboratoryRequest : {}", laboratoryRequestDTO);
        if (laboratoryRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new laboratoryRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LaboratoryRequestDTO result = laboratoryRequestService.save(laboratoryRequestDTO);
        return ResponseEntity
            .created(new URI("/api/laboratory-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratory-requests/:id} : Updates an existing laboratoryRequest.
     *
     * @param id the id of the laboratoryRequestDTO to save.
     * @param laboratoryRequestDTO the laboratoryRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryRequestDTO,
     * or with status {@code 400 (Bad Request)} if the laboratoryRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratory-requests/{id}")
    public ResponseEntity<LaboratoryRequestDTO> updateLaboratoryRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LaboratoryRequestDTO laboratoryRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LaboratoryRequest : {}, {}", id, laboratoryRequestDTO);
        if (laboratoryRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LaboratoryRequestDTO result = laboratoryRequestService.save(laboratoryRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /laboratory-requests/:id} : Partial updates given fields of an existing laboratoryRequest, field will ignore if it is null
     *
     * @param id the id of the laboratoryRequestDTO to save.
     * @param laboratoryRequestDTO the laboratoryRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryRequestDTO,
     * or with status {@code 400 (Bad Request)} if the laboratoryRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the laboratoryRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/laboratory-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LaboratoryRequestDTO> partialUpdateLaboratoryRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LaboratoryRequestDTO laboratoryRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LaboratoryRequest partially : {}, {}", id, laboratoryRequestDTO);
        if (laboratoryRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LaboratoryRequestDTO> result = laboratoryRequestService.partialUpdate(laboratoryRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /laboratory-requests} : get all the laboratoryRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratoryRequests in body.
     */
    @GetMapping("/laboratory-requests")
    public List<LaboratoryRequestDTO> getAllLaboratoryRequests() {
        log.debug("REST request to get all LaboratoryRequests");
        return laboratoryRequestService.findAll();
    }

    /**
     * {@code GET  /laboratory-requests/:id} : get the "id" laboratoryRequest.
     *
     * @param id the id of the laboratoryRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratoryRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratory-requests/{id}")
    public ResponseEntity<LaboratoryRequestDTO> getLaboratoryRequest(@PathVariable Long id) {
        log.debug("REST request to get LaboratoryRequest : {}", id);
        Optional<LaboratoryRequestDTO> laboratoryRequestDTO = laboratoryRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratoryRequestDTO);
    }

    /**
     * {@code DELETE  /laboratory-requests/:id} : delete the "id" laboratoryRequest.
     *
     * @param id the id of the laboratoryRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratory-requests/{id}")
    public ResponseEntity<Void> deleteLaboratoryRequest(@PathVariable Long id) {
        log.debug("REST request to delete LaboratoryRequest : {}", id);
        laboratoryRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
