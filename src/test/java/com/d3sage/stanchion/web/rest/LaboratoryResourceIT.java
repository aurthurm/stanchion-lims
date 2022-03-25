package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.Laboratory;
import com.d3sage.stanchion.repository.LaboratoryRepository;
import com.d3sage.stanchion.service.dto.LaboratoryDTO;
import com.d3sage.stanchion.service.mapper.LaboratoryMapper;
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
 * Integration tests for the {@link LaboratoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LaboratoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/laboratories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private LaboratoryMapper laboratoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLaboratoryMockMvc;

    private Laboratory laboratory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratory createEntity(EntityManager em) {
        Laboratory laboratory = new Laboratory().name(DEFAULT_NAME);
        return laboratory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratory createUpdatedEntity(EntityManager em) {
        Laboratory laboratory = new Laboratory().name(UPDATED_NAME);
        return laboratory;
    }

    @BeforeEach
    public void initTest() {
        laboratory = createEntity(em);
    }

    @Test
    @Transactional
    void createLaboratory() throws Exception {
        int databaseSizeBeforeCreate = laboratoryRepository.findAll().size();
        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);
        restLaboratoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeCreate + 1);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createLaboratoryWithExistingId() throws Exception {
        // Create the Laboratory with an existing ID
        laboratory.setId(1L);
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        int databaseSizeBeforeCreate = laboratoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLaboratories() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        // Get all the laboratoryList
        restLaboratoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getLaboratory() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        // Get the laboratory
        restLaboratoryMockMvc
            .perform(get(ENTITY_API_URL_ID, laboratory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(laboratory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingLaboratory() throws Exception {
        // Get the laboratory
        restLaboratoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLaboratory() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory
        Laboratory updatedLaboratory = laboratoryRepository.findById(laboratory.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratory are not directly saved in db
        em.detach(updatedLaboratory);
        updatedLaboratory.name(UPDATED_NAME);
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(updatedLaboratory);

        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLaboratoryWithPatch() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory using partial update
        Laboratory partialUpdatedLaboratory = new Laboratory();
        partialUpdatedLaboratory.setId(laboratory.getId());

        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratory))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateLaboratoryWithPatch() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory using partial update
        Laboratory partialUpdatedLaboratory = new Laboratory();
        partialUpdatedLaboratory.setId(laboratory.getId());

        partialUpdatedLaboratory.name(UPDATED_NAME);

        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratory))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, laboratoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(count.incrementAndGet());

        // Create the Laboratory
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDto(laboratory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(laboratoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLaboratory() throws Exception {
        // Initialize the database
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeDelete = laboratoryRepository.findAll().size();

        // Delete the laboratory
        restLaboratoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, laboratory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
