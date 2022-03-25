package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.PatientIdentifier;
import com.d3sage.stanchion.repository.PatientIdentifierRepository;
import com.d3sage.stanchion.service.dto.PatientIdentifierDTO;
import com.d3sage.stanchion.service.mapper.PatientIdentifierMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientIdentifierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientIdentifierResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patient-identifiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientIdentifierRepository patientIdentifierRepository;

    @Autowired
    private PatientIdentifierMapper patientIdentifierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientIdentifierMockMvc;

    private PatientIdentifier patientIdentifier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientIdentifier createEntity(EntityManager em) {
        PatientIdentifier patientIdentifier = new PatientIdentifier().type(DEFAULT_TYPE).value(DEFAULT_VALUE);
        return patientIdentifier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientIdentifier createUpdatedEntity(EntityManager em) {
        PatientIdentifier patientIdentifier = new PatientIdentifier().type(UPDATED_TYPE).value(UPDATED_VALUE);
        return patientIdentifier;
    }

    @BeforeEach
    public void initTest() {
        patientIdentifier = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientIdentifier() throws Exception {
        int databaseSizeBeforeCreate = patientIdentifierRepository.findAll().size();
        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);
        restPatientIdentifierMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeCreate + 1);
        PatientIdentifier testPatientIdentifier = patientIdentifierList.get(patientIdentifierList.size() - 1);
        assertThat(testPatientIdentifier.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPatientIdentifier.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createPatientIdentifierWithExistingId() throws Exception {
        // Create the PatientIdentifier with an existing ID
        patientIdentifier.setId(1L);
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        int databaseSizeBeforeCreate = patientIdentifierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientIdentifierMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientIdentifiers() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        // Get all the patientIdentifierList
        restPatientIdentifierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientIdentifier.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getPatientIdentifier() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        // Get the patientIdentifier
        restPatientIdentifierMockMvc
            .perform(get(ENTITY_API_URL_ID, patientIdentifier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientIdentifier.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingPatientIdentifier() throws Exception {
        // Get the patientIdentifier
        restPatientIdentifierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPatientIdentifier() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();

        // Update the patientIdentifier
        PatientIdentifier updatedPatientIdentifier = patientIdentifierRepository.findById(patientIdentifier.getId()).get();
        // Disconnect from session so that the updates on updatedPatientIdentifier are not directly saved in db
        em.detach(updatedPatientIdentifier);
        updatedPatientIdentifier.type(UPDATED_TYPE).value(UPDATED_VALUE);
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(updatedPatientIdentifier);

        restPatientIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientIdentifierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifier testPatientIdentifier = patientIdentifierList.get(patientIdentifierList.size() - 1);
        assertThat(testPatientIdentifier.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatientIdentifier.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientIdentifierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientIdentifierWithPatch() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();

        // Update the patientIdentifier using partial update
        PatientIdentifier partialUpdatedPatientIdentifier = new PatientIdentifier();
        partialUpdatedPatientIdentifier.setId(patientIdentifier.getId());

        partialUpdatedPatientIdentifier.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restPatientIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientIdentifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientIdentifier))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifier testPatientIdentifier = patientIdentifierList.get(patientIdentifierList.size() - 1);
        assertThat(testPatientIdentifier.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatientIdentifier.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdatePatientIdentifierWithPatch() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();

        // Update the patientIdentifier using partial update
        PatientIdentifier partialUpdatedPatientIdentifier = new PatientIdentifier();
        partialUpdatedPatientIdentifier.setId(patientIdentifier.getId());

        partialUpdatedPatientIdentifier.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restPatientIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientIdentifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientIdentifier))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifier testPatientIdentifier = patientIdentifierList.get(patientIdentifierList.size() - 1);
        assertThat(testPatientIdentifier.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatientIdentifier.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientIdentifierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierRepository.findAll().size();
        patientIdentifier.setId(count.incrementAndGet());

        // Create the PatientIdentifier
        PatientIdentifierDTO patientIdentifierDTO = patientIdentifierMapper.toDto(patientIdentifier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientIdentifier in the database
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientIdentifier() throws Exception {
        // Initialize the database
        patientIdentifierRepository.saveAndFlush(patientIdentifier);

        int databaseSizeBeforeDelete = patientIdentifierRepository.findAll().size();

        // Delete the patientIdentifier
        restPatientIdentifierMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientIdentifier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientIdentifier> patientIdentifierList = patientIdentifierRepository.findAll();
        assertThat(patientIdentifierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
