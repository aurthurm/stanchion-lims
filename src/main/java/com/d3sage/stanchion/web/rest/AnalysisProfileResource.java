package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.AnalysisProfileRepository;
import com.d3sage.stanchion.service.AnalysisProfileService;
import com.d3sage.stanchion.service.dto.AnalysisProfileDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.AnalysisProfile}.
 */
@RestController
@RequestMapping("/api")
public class AnalysisProfileResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisProfileResource.class);

    private static final String ENTITY_NAME = "analysisProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisProfileService analysisProfileService;

    private final AnalysisProfileRepository analysisProfileRepository;

    public AnalysisProfileResource(AnalysisProfileService analysisProfileService, AnalysisProfileRepository analysisProfileRepository) {
        this.analysisProfileService = analysisProfileService;
        this.analysisProfileRepository = analysisProfileRepository;
    }

    /**
     * {@code POST  /analysis-profiles} : Create a new analysisProfile.
     *
     * @param analysisProfileDTO the analysisProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysisProfileDTO, or with status {@code 400 (Bad Request)} if the analysisProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analysis-profiles")
    public ResponseEntity<AnalysisProfileDTO> createAnalysisProfile(@RequestBody AnalysisProfileDTO analysisProfileDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnalysisProfile : {}", analysisProfileDTO);
        if (analysisProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new analysisProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalysisProfileDTO result = analysisProfileService.save(analysisProfileDTO);
        return ResponseEntity
            .created(new URI("/api/analysis-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analysis-profiles/:id} : Updates an existing analysisProfile.
     *
     * @param id the id of the analysisProfileDTO to save.
     * @param analysisProfileDTO the analysisProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisProfileDTO,
     * or with status {@code 400 (Bad Request)} if the analysisProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysisProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analysis-profiles/{id}")
    public ResponseEntity<AnalysisProfileDTO> updateAnalysisProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisProfileDTO analysisProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalysisProfile : {}, {}", id, analysisProfileDTO);
        if (analysisProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalysisProfileDTO result = analysisProfileService.save(analysisProfileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analysis-profiles/:id} : Partial updates given fields of an existing analysisProfile, field will ignore if it is null
     *
     * @param id the id of the analysisProfileDTO to save.
     * @param analysisProfileDTO the analysisProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisProfileDTO,
     * or with status {@code 400 (Bad Request)} if the analysisProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analysisProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analysisProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analysis-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalysisProfileDTO> partialUpdateAnalysisProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisProfileDTO analysisProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalysisProfile partially : {}, {}", id, analysisProfileDTO);
        if (analysisProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalysisProfileDTO> result = analysisProfileService.partialUpdate(analysisProfileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisProfileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analysis-profiles} : get all the analysisProfiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analysisProfiles in body.
     */
    @GetMapping("/analysis-profiles")
    public List<AnalysisProfileDTO> getAllAnalysisProfiles() {
        log.debug("REST request to get all AnalysisProfiles");
        return analysisProfileService.findAll();
    }

    /**
     * {@code GET  /analysis-profiles/:id} : get the "id" analysisProfile.
     *
     * @param id the id of the analysisProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysisProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analysis-profiles/{id}")
    public ResponseEntity<AnalysisProfileDTO> getAnalysisProfile(@PathVariable Long id) {
        log.debug("REST request to get AnalysisProfile : {}", id);
        Optional<AnalysisProfileDTO> analysisProfileDTO = analysisProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analysisProfileDTO);
    }

    /**
     * {@code DELETE  /analysis-profiles/:id} : delete the "id" analysisProfile.
     *
     * @param id the id of the analysisProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analysis-profiles/{id}")
    public ResponseEntity<Void> deleteAnalysisProfile(@PathVariable Long id) {
        log.debug("REST request to delete AnalysisProfile : {}", id);
        analysisProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
