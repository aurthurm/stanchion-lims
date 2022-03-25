package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.AnalysisRepository;
import com.d3sage.stanchion.service.AnalysisService;
import com.d3sage.stanchion.service.dto.AnalysisDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.Analysis}.
 */
@RestController
@RequestMapping("/api")
public class AnalysisResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisResource.class);

    private static final String ENTITY_NAME = "analysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisService analysisService;

    private final AnalysisRepository analysisRepository;

    public AnalysisResource(AnalysisService analysisService, AnalysisRepository analysisRepository) {
        this.analysisService = analysisService;
        this.analysisRepository = analysisRepository;
    }

    /**
     * {@code POST  /analyses} : Create a new analysis.
     *
     * @param analysisDTO the analysisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysisDTO, or with status {@code 400 (Bad Request)} if the analysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyses")
    public ResponseEntity<AnalysisDTO> createAnalysis(@RequestBody AnalysisDTO analysisDTO) throws URISyntaxException {
        log.debug("REST request to save Analysis : {}", analysisDTO);
        if (analysisDTO.getId() != null) {
            throw new BadRequestAlertException("A new analysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalysisDTO result = analysisService.save(analysisDTO);
        return ResponseEntity
            .created(new URI("/api/analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyses/:id} : Updates an existing analysis.
     *
     * @param id the id of the analysisDTO to save.
     * @param analysisDTO the analysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisDTO,
     * or with status {@code 400 (Bad Request)} if the analysisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyses/{id}")
    public ResponseEntity<AnalysisDTO> updateAnalysis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisDTO analysisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Analysis : {}, {}", id, analysisDTO);
        if (analysisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalysisDTO result = analysisService.save(analysisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analyses/:id} : Partial updates given fields of an existing analysis, field will ignore if it is null
     *
     * @param id the id of the analysisDTO to save.
     * @param analysisDTO the analysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisDTO,
     * or with status {@code 400 (Bad Request)} if the analysisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analysisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analysisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analyses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalysisDTO> partialUpdateAnalysis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalysisDTO analysisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Analysis partially : {}, {}", id, analysisDTO);
        if (analysisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalysisDTO> result = analysisService.partialUpdate(analysisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analyses} : get all the analyses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyses in body.
     */
    @GetMapping("/analyses")
    public List<AnalysisDTO> getAllAnalyses() {
        log.debug("REST request to get all Analyses");
        return analysisService.findAll();
    }

    /**
     * {@code GET  /analyses/:id} : get the "id" analysis.
     *
     * @param id the id of the analysisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyses/{id}")
    public ResponseEntity<AnalysisDTO> getAnalysis(@PathVariable Long id) {
        log.debug("REST request to get Analysis : {}", id);
        Optional<AnalysisDTO> analysisDTO = analysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analysisDTO);
    }

    /**
     * {@code DELETE  /analyses/:id} : delete the "id" analysis.
     *
     * @param id the id of the analysisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyses/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete Analysis : {}", id);
        analysisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
