package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.SampleTypeRepository;
import com.d3sage.stanchion.service.SampleTypeService;
import com.d3sage.stanchion.service.dto.SampleTypeDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.SampleType}.
 */
@RestController
@RequestMapping("/api")
public class SampleTypeResource {

    private final Logger log = LoggerFactory.getLogger(SampleTypeResource.class);

    private static final String ENTITY_NAME = "sampleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SampleTypeService sampleTypeService;

    private final SampleTypeRepository sampleTypeRepository;

    public SampleTypeResource(SampleTypeService sampleTypeService, SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeService = sampleTypeService;
        this.sampleTypeRepository = sampleTypeRepository;
    }

    /**
     * {@code POST  /sample-types} : Create a new sampleType.
     *
     * @param sampleTypeDTO the sampleTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sampleTypeDTO, or with status {@code 400 (Bad Request)} if the sampleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sample-types")
    public ResponseEntity<SampleTypeDTO> createSampleType(@RequestBody SampleTypeDTO sampleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SampleType : {}", sampleTypeDTO);
        if (sampleTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sampleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleTypeDTO result = sampleTypeService.save(sampleTypeDTO);
        return ResponseEntity
            .created(new URI("/api/sample-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sample-types/:id} : Updates an existing sampleType.
     *
     * @param id the id of the sampleTypeDTO to save.
     * @param sampleTypeDTO the sampleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the sampleTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sampleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sample-types/{id}")
    public ResponseEntity<SampleTypeDTO> updateSampleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleTypeDTO sampleTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SampleType : {}, {}", id, sampleTypeDTO);
        if (sampleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SampleTypeDTO result = sampleTypeService.save(sampleTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sample-types/:id} : Partial updates given fields of an existing sampleType, field will ignore if it is null
     *
     * @param id the id of the sampleTypeDTO to save.
     * @param sampleTypeDTO the sampleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the sampleTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sampleTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sampleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sample-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SampleTypeDTO> partialUpdateSampleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleTypeDTO sampleTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SampleType partially : {}, {}", id, sampleTypeDTO);
        if (sampleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SampleTypeDTO> result = sampleTypeService.partialUpdate(sampleTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sample-types} : get all the sampleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sampleTypes in body.
     */
    @GetMapping("/sample-types")
    public List<SampleTypeDTO> getAllSampleTypes() {
        log.debug("REST request to get all SampleTypes");
        return sampleTypeService.findAll();
    }

    /**
     * {@code GET  /sample-types/:id} : get the "id" sampleType.
     *
     * @param id the id of the sampleTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sampleTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sample-types/{id}")
    public ResponseEntity<SampleTypeDTO> getSampleType(@PathVariable Long id) {
        log.debug("REST request to get SampleType : {}", id);
        Optional<SampleTypeDTO> sampleTypeDTO = sampleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sampleTypeDTO);
    }

    /**
     * {@code DELETE  /sample-types/:id} : delete the "id" sampleType.
     *
     * @param id the id of the sampleTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sample-types/{id}")
    public ResponseEntity<Void> deleteSampleType(@PathVariable Long id) {
        log.debug("REST request to delete SampleType : {}", id);
        sampleTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
