package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.SampleRepository;
import com.d3sage.stanchion.service.SampleService;
import com.d3sage.stanchion.service.dto.SampleDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.Sample}.
 */
@RestController
@RequestMapping("/api")
public class SampleResource {

    private final Logger log = LoggerFactory.getLogger(SampleResource.class);

    private static final String ENTITY_NAME = "sample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SampleService sampleService;

    private final SampleRepository sampleRepository;

    public SampleResource(SampleService sampleService, SampleRepository sampleRepository) {
        this.sampleService = sampleService;
        this.sampleRepository = sampleRepository;
    }

    /**
     * {@code POST  /samples} : Create a new sample.
     *
     * @param sampleDTO the sampleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sampleDTO, or with status {@code 400 (Bad Request)} if the sample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/samples")
    public ResponseEntity<SampleDTO> createSample(@RequestBody SampleDTO sampleDTO) throws URISyntaxException {
        log.debug("REST request to save Sample : {}", sampleDTO);
        if (sampleDTO.getId() != null) {
            throw new BadRequestAlertException("A new sample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleDTO result = sampleService.save(sampleDTO);
        return ResponseEntity
            .created(new URI("/api/samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /samples/:id} : Updates an existing sample.
     *
     * @param id the id of the sampleDTO to save.
     * @param sampleDTO the sampleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleDTO,
     * or with status {@code 400 (Bad Request)} if the sampleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sampleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/samples/{id}")
    public ResponseEntity<SampleDTO> updateSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleDTO sampleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sample : {}, {}", id, sampleDTO);
        if (sampleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SampleDTO result = sampleService.save(sampleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /samples/:id} : Partial updates given fields of an existing sample, field will ignore if it is null
     *
     * @param id the id of the sampleDTO to save.
     * @param sampleDTO the sampleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleDTO,
     * or with status {@code 400 (Bad Request)} if the sampleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sampleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sampleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SampleDTO> partialUpdateSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleDTO sampleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sample partially : {}, {}", id, sampleDTO);
        if (sampleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SampleDTO> result = sampleService.partialUpdate(sampleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /samples} : get all the samples.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of samples in body.
     */
    @GetMapping("/samples")
    public List<SampleDTO> getAllSamples() {
        log.debug("REST request to get all Samples");
        return sampleService.findAll();
    }

    /**
     * {@code GET  /samples/:id} : get the "id" sample.
     *
     * @param id the id of the sampleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sampleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/samples/{id}")
    public ResponseEntity<SampleDTO> getSample(@PathVariable Long id) {
        log.debug("REST request to get Sample : {}", id);
        Optional<SampleDTO> sampleDTO = sampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sampleDTO);
    }

    /**
     * {@code DELETE  /samples/:id} : delete the "id" sample.
     *
     * @param id the id of the sampleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/samples/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        log.debug("REST request to delete Sample : {}", id);
        sampleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
