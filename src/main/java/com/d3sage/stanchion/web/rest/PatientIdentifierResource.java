package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.PatientIdentifierRepository;
import com.d3sage.stanchion.service.PatientIdentifierService;
import com.d3sage.stanchion.service.dto.PatientIdentifierDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.PatientIdentifier}.
 */
@RestController
@RequestMapping("/api")
public class PatientIdentifierResource {

    private final Logger log = LoggerFactory.getLogger(PatientIdentifierResource.class);

    private static final String ENTITY_NAME = "patientIdentifier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientIdentifierService patientIdentifierService;

    private final PatientIdentifierRepository patientIdentifierRepository;

    public PatientIdentifierResource(
        PatientIdentifierService patientIdentifierService,
        PatientIdentifierRepository patientIdentifierRepository
    ) {
        this.patientIdentifierService = patientIdentifierService;
        this.patientIdentifierRepository = patientIdentifierRepository;
    }

    /**
     * {@code POST  /patient-identifiers} : Create a new patientIdentifier.
     *
     * @param patientIdentifierDTO the patientIdentifierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientIdentifierDTO, or with status {@code 400 (Bad Request)} if the patientIdentifier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-identifiers")
    public ResponseEntity<PatientIdentifierDTO> createPatientIdentifier(@RequestBody PatientIdentifierDTO patientIdentifierDTO)
        throws URISyntaxException {
        log.debug("REST request to save PatientIdentifier : {}", patientIdentifierDTO);
        if (patientIdentifierDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientIdentifier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientIdentifierDTO result = patientIdentifierService.save(patientIdentifierDTO);
        return ResponseEntity
            .created(new URI("/api/patient-identifiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-identifiers/:id} : Updates an existing patientIdentifier.
     *
     * @param id the id of the patientIdentifierDTO to save.
     * @param patientIdentifierDTO the patientIdentifierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientIdentifierDTO,
     * or with status {@code 400 (Bad Request)} if the patientIdentifierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientIdentifierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-identifiers/{id}")
    public ResponseEntity<PatientIdentifierDTO> updatePatientIdentifier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientIdentifierDTO patientIdentifierDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PatientIdentifier : {}, {}", id, patientIdentifierDTO);
        if (patientIdentifierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientIdentifierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientIdentifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientIdentifierDTO result = patientIdentifierService.save(patientIdentifierDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientIdentifierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patient-identifiers/:id} : Partial updates given fields of an existing patientIdentifier, field will ignore if it is null
     *
     * @param id the id of the patientIdentifierDTO to save.
     * @param patientIdentifierDTO the patientIdentifierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientIdentifierDTO,
     * or with status {@code 400 (Bad Request)} if the patientIdentifierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientIdentifierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientIdentifierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/patient-identifiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientIdentifierDTO> partialUpdatePatientIdentifier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientIdentifierDTO patientIdentifierDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PatientIdentifier partially : {}, {}", id, patientIdentifierDTO);
        if (patientIdentifierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientIdentifierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientIdentifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientIdentifierDTO> result = patientIdentifierService.partialUpdate(patientIdentifierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientIdentifierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-identifiers} : get all the patientIdentifiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientIdentifiers in body.
     */
    @GetMapping("/patient-identifiers")
    public List<PatientIdentifierDTO> getAllPatientIdentifiers() {
        log.debug("REST request to get all PatientIdentifiers");
        return patientIdentifierService.findAll();
    }

    /**
     * {@code GET  /patient-identifiers/:id} : get the "id" patientIdentifier.
     *
     * @param id the id of the patientIdentifierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientIdentifierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-identifiers/{id}")
    public ResponseEntity<PatientIdentifierDTO> getPatientIdentifier(@PathVariable Long id) {
        log.debug("REST request to get PatientIdentifier : {}", id);
        Optional<PatientIdentifierDTO> patientIdentifierDTO = patientIdentifierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientIdentifierDTO);
    }

    /**
     * {@code DELETE  /patient-identifiers/:id} : delete the "id" patientIdentifier.
     *
     * @param id the id of the patientIdentifierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-identifiers/{id}")
    public ResponseEntity<Void> deletePatientIdentifier(@PathVariable Long id) {
        log.debug("REST request to delete PatientIdentifier : {}", id);
        patientIdentifierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
