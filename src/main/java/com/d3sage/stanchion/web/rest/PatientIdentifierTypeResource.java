package com.d3sage.stanchion.web.rest;

import com.d3sage.stanchion.repository.PatientIdentifierTypeRepository;
import com.d3sage.stanchion.service.PatientIdentifierTypeService;
import com.d3sage.stanchion.service.dto.PatientIdentifierTypeDTO;
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
 * REST controller for managing {@link com.d3sage.stanchion.domain.PatientIdentifierType}.
 */
@RestController
@RequestMapping("/api")
public class PatientIdentifierTypeResource {

    private final Logger log = LoggerFactory.getLogger(PatientIdentifierTypeResource.class);

    private static final String ENTITY_NAME = "patientIdentifierType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientIdentifierTypeService patientIdentifierTypeService;

    private final PatientIdentifierTypeRepository patientIdentifierTypeRepository;

    public PatientIdentifierTypeResource(
        PatientIdentifierTypeService patientIdentifierTypeService,
        PatientIdentifierTypeRepository patientIdentifierTypeRepository
    ) {
        this.patientIdentifierTypeService = patientIdentifierTypeService;
        this.patientIdentifierTypeRepository = patientIdentifierTypeRepository;
    }

    /**
     * {@code POST  /patient-identifier-types} : Create a new patientIdentifierType.
     *
     * @param patientIdentifierTypeDTO the patientIdentifierTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientIdentifierTypeDTO, or with status {@code 400 (Bad Request)} if the patientIdentifierType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-identifier-types")
    public ResponseEntity<PatientIdentifierTypeDTO> createPatientIdentifierType(
        @RequestBody PatientIdentifierTypeDTO patientIdentifierTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PatientIdentifierType : {}", patientIdentifierTypeDTO);
        if (patientIdentifierTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientIdentifierType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientIdentifierTypeDTO result = patientIdentifierTypeService.save(patientIdentifierTypeDTO);
        return ResponseEntity
            .created(new URI("/api/patient-identifier-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-identifier-types/:id} : Updates an existing patientIdentifierType.
     *
     * @param id the id of the patientIdentifierTypeDTO to save.
     * @param patientIdentifierTypeDTO the patientIdentifierTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientIdentifierTypeDTO,
     * or with status {@code 400 (Bad Request)} if the patientIdentifierTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientIdentifierTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-identifier-types/{id}")
    public ResponseEntity<PatientIdentifierTypeDTO> updatePatientIdentifierType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientIdentifierTypeDTO patientIdentifierTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PatientIdentifierType : {}, {}", id, patientIdentifierTypeDTO);
        if (patientIdentifierTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientIdentifierTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientIdentifierTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientIdentifierTypeDTO result = patientIdentifierTypeService.save(patientIdentifierTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientIdentifierTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patient-identifier-types/:id} : Partial updates given fields of an existing patientIdentifierType, field will ignore if it is null
     *
     * @param id the id of the patientIdentifierTypeDTO to save.
     * @param patientIdentifierTypeDTO the patientIdentifierTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientIdentifierTypeDTO,
     * or with status {@code 400 (Bad Request)} if the patientIdentifierTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientIdentifierTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientIdentifierTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/patient-identifier-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientIdentifierTypeDTO> partialUpdatePatientIdentifierType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientIdentifierTypeDTO patientIdentifierTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PatientIdentifierType partially : {}, {}", id, patientIdentifierTypeDTO);
        if (patientIdentifierTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientIdentifierTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientIdentifierTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientIdentifierTypeDTO> result = patientIdentifierTypeService.partialUpdate(patientIdentifierTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientIdentifierTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-identifier-types} : get all the patientIdentifierTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientIdentifierTypes in body.
     */
    @GetMapping("/patient-identifier-types")
    public List<PatientIdentifierTypeDTO> getAllPatientIdentifierTypes() {
        log.debug("REST request to get all PatientIdentifierTypes");
        return patientIdentifierTypeService.findAll();
    }

    /**
     * {@code GET  /patient-identifier-types/:id} : get the "id" patientIdentifierType.
     *
     * @param id the id of the patientIdentifierTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientIdentifierTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-identifier-types/{id}")
    public ResponseEntity<PatientIdentifierTypeDTO> getPatientIdentifierType(@PathVariable Long id) {
        log.debug("REST request to get PatientIdentifierType : {}", id);
        Optional<PatientIdentifierTypeDTO> patientIdentifierTypeDTO = patientIdentifierTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientIdentifierTypeDTO);
    }

    /**
     * {@code DELETE  /patient-identifier-types/:id} : delete the "id" patientIdentifierType.
     *
     * @param id the id of the patientIdentifierTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-identifier-types/{id}")
    public ResponseEntity<Void> deletePatientIdentifierType(@PathVariable Long id) {
        log.debug("REST request to delete PatientIdentifierType : {}", id);
        patientIdentifierTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
