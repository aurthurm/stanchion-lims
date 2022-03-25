package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.AnalysisServiceRepository;
import com.d3sage.stanchion.service.AnalysisServiceService;
import com.d3sage.stanchion.service.dto.AnalysisServiceDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.AnalysisService}.
 */
@RestController
@RequestMapping("/api")
public class AnalysisServiceResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceResource.class);

    private static final String ENTITY_NAME = "analysisService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisServiceService analysisServiceService;

    private final AnalysisServiceRepository analysisServiceRepository;

    public AnalysisServiceResource(AnalysisServiceService analysisServiceService, AnalysisServiceRepository analysisServiceRepository) {
        this.analysisServiceService = analysisServiceService;
        this.analysisServiceRepository = analysisServiceRepository;
    }

    /**
     * {@code POST  /analysis-services} : Create a new analysisService.
     *
     * @param analysisServiceDTO the analysisServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysisServiceDTO, or with status {@code 400 (Bad Request)} if the analysisService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analysis-services")
    public ResponseEntity<AnalysisServiceDTO> createAnalysisService(@RequestBody AnalysisServiceDTO analysisServiceDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnalysisService : {}", analysisServiceDTO);
        if (analysisServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new analysisService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalysisServiceDTO result = analysisServiceService.save(analysisServiceDTO);
        return ResponseEntity
            .created(new URI("/api/analysis-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analysis-services/:id} : Updates an existing analysisService.
     *
     * @param id the id of the analysisServiceDTO to save.
     * @param analysisServiceDTO the analysisServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisServiceDTO,
     * or with status {@code 400 (Bad Request)} if the analysisServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysisServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analysis-services/{id}")
    public ResponseEntity<AnalysisServiceDTO> updateAnalysisService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisServiceDTO analysisServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalysisService : {}, {}", id, analysisServiceDTO);
        if (analysisServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalysisServiceDTO result = analysisServiceService.save(analysisServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analysis-services/:id} : Partial updates given fields of an existing analysisService, field will ignore if it is null
     *
     * @param id the id of the analysisServiceDTO to save.
     * @param analysisServiceDTO the analysisServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisServiceDTO,
     * or with status {@code 400 (Bad Request)} if the analysisServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analysisServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analysisServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analysis-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalysisServiceDTO> partialUpdateAnalysisService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisServiceDTO analysisServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalysisService partially : {}, {}", id, analysisServiceDTO);
        if (analysisServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalysisServiceDTO> result = analysisServiceService.partialUpdate(analysisServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analysis-services} : get all the analysisServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analysisServices in body.
     */
    @GetMapping("/analysis-services")
    public List<AnalysisServiceDTO> getAllAnalysisServices() {
        log.debug("REST request to get all AnalysisServices");
        return analysisServiceService.findAll();
    }

    /**
     * {@code GET  /analysis-services/:id} : get the "id" analysisService.
     *
     * @param id the id of the analysisServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysisServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analysis-services/{id}")
    public ResponseEntity<AnalysisServiceDTO> getAnalysisService(@PathVariable Long id) {
        log.debug("REST request to get AnalysisService : {}", id);
        Optional<AnalysisServiceDTO> analysisServiceDTO = analysisServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analysisServiceDTO);
    }

    /**
     * {@code DELETE  /analysis-services/:id} : delete the "id" analysisService.
     *
     * @param id the id of the analysisServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analysis-services/{id}")
    public ResponseEntity<Void> deleteAnalysisService(@PathVariable Long id) {
        log.debug("REST request to delete AnalysisService : {}", id);
        analysisServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
