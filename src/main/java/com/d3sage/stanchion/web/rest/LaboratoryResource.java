package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.LaboratoryRepository;
import com.d3sage.stanchion.service.LaboratoryService;
import com.d3sage.stanchion.service.dto.LaboratoryDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.Laboratory}.
 */
@RestController
@RequestMapping("/api")
public class LaboratoryResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryResource.class);

    private static final String ENTITY_NAME = "laboratory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryService laboratoryService;

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryResource(LaboratoryService laboratoryService, LaboratoryRepository laboratoryRepository) {
        this.laboratoryService = laboratoryService;
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * {@code POST  /laboratories} : Create a new laboratory.
     *
     * @param laboratoryDTO the laboratoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratoryDTO, or with status {@code 400 (Bad Request)} if the laboratory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratories")
    public ResponseEntity<LaboratoryDTO> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) throws URISyntaxException {
        log.debug("REST request to save Laboratory : {}", laboratoryDTO);
        if (laboratoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new laboratory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LaboratoryDTO result = laboratoryService.save(laboratoryDTO);
        return ResponseEntity
            .created(new URI("/api/laboratories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratories/:id} : Updates an existing laboratory.
     *
     * @param id the id of the laboratoryDTO to save.
     * @param laboratoryDTO the laboratoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryDTO,
     * or with status {@code 400 (Bad Request)} if the laboratoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratories/{id}")
    public ResponseEntity<LaboratoryDTO> updateLaboratory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LaboratoryDTO laboratoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Laboratory : {}, {}", id, laboratoryDTO);
        if (laboratoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LaboratoryDTO result = laboratoryService.save(laboratoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /laboratories/:id} : Partial updates given fields of an existing laboratory, field will ignore if it is null
     *
     * @param id the id of the laboratoryDTO to save.
     * @param laboratoryDTO the laboratoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryDTO,
     * or with status {@code 400 (Bad Request)} if the laboratoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the laboratoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/laboratories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LaboratoryDTO> partialUpdateLaboratory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LaboratoryDTO laboratoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Laboratory partially : {}, {}", id, laboratoryDTO);
        if (laboratoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LaboratoryDTO> result = laboratoryService.partialUpdate(laboratoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /laboratories} : get all the laboratories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratories in body.
     */
    @GetMapping("/laboratories")
    public List<LaboratoryDTO> getAllLaboratories() {
        log.debug("REST request to get all Laboratories");
        return laboratoryService.findAll();
    }

    /**
     * {@code GET  /laboratories/:id} : get the "id" laboratory.
     *
     * @param id the id of the laboratoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratories/{id}")
    public ResponseEntity<LaboratoryDTO> getLaboratory(@PathVariable Long id) {
        log.debug("REST request to get Laboratory : {}", id);
        Optional<LaboratoryDTO> laboratoryDTO = laboratoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratoryDTO);
    }

    /**
     * {@code DELETE  /laboratories/:id} : delete the "id" laboratory.
     *
     * @param id the id of the laboratoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratories/{id}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable Long id) {
        log.debug("REST request to delete Laboratory : {}", id);
        laboratoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
