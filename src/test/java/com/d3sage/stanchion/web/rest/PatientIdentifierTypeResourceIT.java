package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.PatientIdentifierType;
import com.d3sage.stanchion.repository.PatientIdentifierTypeRepository;
import com.d3sage.stanchion.service.dto.PatientIdentifierTypeDTO;
import com.d3sage.stanchion.service.mapper.PatientIdentifierTypeMapper;
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
 * Integration tests for the {@link PatientIdentifierTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientIdentifierTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patient-identifier-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientIdentifierTypeRepository patientIdentifierTypeRepository;

    @Autowired
    private PatientIdentifierTypeMapper patientIdentifierTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientIdentifierTypeMockMvc;

    private PatientIdentifierType patientIdentifierType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientIdentifierType createEntity(EntityManager em) {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType().name(DEFAULT_NAME);
        return patientIdentifierType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientIdentifierType createUpdatedEntity(EntityManager em) {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType().name(UPDATED_NAME);
        return patientIdentifierType;
    }

    @BeforeEach
    public void initTest() {
        patientIdentifierType = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientIdentifierType() throws Exception {
        int databaseSizeBeforeCreate = patientIdentifierTypeRepository.findAll().size();
        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);
        restPatientIdentifierTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PatientIdentifierType testPatientIdentifierType = patientIdentifierTypeList.get(patientIdentifierTypeList.size() - 1);
        assertThat(testPatientIdentifierType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPatientIdentifierTypeWithExistingId() throws Exception {
        // Create the PatientIdentifierType with an existing ID
        patientIdentifierType.setId(1L);
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        int databaseSizeBeforeCreate = patientIdentifierTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientIdentifierTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientIdentifierTypes() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        // Get all the patientIdentifierTypeList
        restPatientIdentifierTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientIdentifierType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPatientIdentifierType() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        // Get the patientIdentifierType
        restPatientIdentifierTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, patientIdentifierType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientIdentifierType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPatientIdentifierType() throws Exception {
        // Get the patientIdentifierType
        restPatientIdentifierTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPatientIdentifierType() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();

        // Update the patientIdentifierType
        PatientIdentifierType updatedPatientIdentifierType = patientIdentifierTypeRepository.findById(patientIdentifierType.getId()).get();
        // Disconnect from session so that the updates on updatedPatientIdentifierType are not directly saved in db
        em.detach(updatedPatientIdentifierType);
        updatedPatientIdentifierType.name(UPDATED_NAME);
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(updatedPatientIdentifierType);

        restPatientIdentifierTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientIdentifierTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifierType testPatientIdentifierType = patientIdentifierTypeList.get(patientIdentifierTypeList.size() - 1);
        assertThat(testPatientIdentifierType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientIdentifierTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientIdentifierTypeWithPatch() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();

        // Update the patientIdentifierType using partial update
        PatientIdentifierType partialUpdatedPatientIdentifierType = new PatientIdentifierType();
        partialUpdatedPatientIdentifierType.setId(patientIdentifierType.getId());

        restPatientIdentifierTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientIdentifierType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientIdentifierType))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifierType testPatientIdentifierType = patientIdentifierTypeList.get(patientIdentifierTypeList.size() - 1);
        assertThat(testPatientIdentifierType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePatientIdentifierTypeWithPatch() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();

        // Update the patientIdentifierType using partial update
        PatientIdentifierType partialUpdatedPatientIdentifierType = new PatientIdentifierType();
        partialUpdatedPatientIdentifierType.setId(patientIdentifierType.getId());

        partialUpdatedPatientIdentifierType.name(UPDATED_NAME);

        restPatientIdentifierTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientIdentifierType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientIdentifierType))
            )
            .andExpect(status().isOk());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentifierType testPatientIdentifierType = patientIdentifierTypeList.get(patientIdentifierTypeList.size() - 1);
        assertThat(testPatientIdentifierType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientIdentifierTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentifierTypeRepository.findAll().size();
        patientIdentifierType.setId(count.incrementAndGet());

        // Create the PatientIdentifierType
        PatientIdentifierTypeDTO patientIdentifierTypeDTO = patientIdentifierTypeMapper.toDto(patientIdentifierType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientIdentifierTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientIdentifierTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientIdentifierType in the database
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientIdentifierType() throws Exception {
        // Initialize the database
        patientIdentifierTypeRepository.saveAndFlush(patientIdentifierType);

        int databaseSizeBeforeDelete = patientIdentifierTypeRepository.findAll().size();

        // Delete the patientIdentifierType
        restPatientIdentifierTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientIdentifierType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientIdentifierType> patientIdentifierTypeList = patientIdentifierTypeRepository.findAll();
        assertThat(patientIdentifierTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
