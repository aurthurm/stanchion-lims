package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.AnalysisProfile;
import com.d3sage.stanchion.repository.AnalysisProfileRepository;
import com.d3sage.stanchion.service.dto.AnalysisProfileDTO;
import com.d3sage.stanchion.service.mapper.AnalysisProfileMapper;
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
 * Integration tests for the {@link AnalysisProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalysisProfileResourceIT {

    private static final String ENTITY_API_URL = "/api/analysis-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalysisProfileRepository analysisProfileRepository;

    @Autowired
    private AnalysisProfileMapper analysisProfileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalysisProfileMockMvc;

    private AnalysisProfile analysisProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalysisProfile createEntity(EntityManager em) {
        AnalysisProfile analysisProfile = new AnalysisProfile();
        return analysisProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalysisProfile createUpdatedEntity(EntityManager em) {
        AnalysisProfile analysisProfile = new AnalysisProfile();
        return analysisProfile;
    }

    @BeforeEach
    public void initTest() {
        analysisProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalysisProfile() throws Exception {
        int databaseSizeBeforeCreate = analysisProfileRepository.findAll().size();
        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);
        restAnalysisProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeCreate + 1);
        AnalysisProfile testAnalysisProfile = analysisProfileList.get(analysisProfileList.size() - 1);
    }

    @Test
    @Transactional
    void createAnalysisProfileWithExistingId() throws Exception {
        // Create the AnalysisProfile with an existing ID
        analysisProfile.setId(1L);
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        int databaseSizeBeforeCreate = analysisProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnalysisProfiles() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        // Get all the analysisProfileList
        restAnalysisProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysisProfile.getId().intValue())));
    }

    @Test
    @Transactional
    void getAnalysisProfile() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        // Get the analysisProfile
        restAnalysisProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, analysisProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analysisProfile.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnalysisProfile() throws Exception {
        // Get the analysisProfile
        restAnalysisProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalysisProfile() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();

        // Update the analysisProfile
        AnalysisProfile updatedAnalysisProfile = analysisProfileRepository.findById(analysisProfile.getId()).get();
        // Disconnect from session so that the updates on updatedAnalysisProfile are not directly saved in db
        em.detach(updatedAnalysisProfile);
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(updatedAnalysisProfile);

        restAnalysisProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analysisProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
        AnalysisProfile testAnalysisProfile = analysisProfileList.get(analysisProfileList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analysisProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalysisProfileWithPatch() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();

        // Update the analysisProfile using partial update
        AnalysisProfile partialUpdatedAnalysisProfile = new AnalysisProfile();
        partialUpdatedAnalysisProfile.setId(analysisProfile.getId());

        restAnalysisProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysisProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysisProfile))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
        AnalysisProfile testAnalysisProfile = analysisProfileList.get(analysisProfileList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAnalysisProfileWithPatch() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();

        // Update the analysisProfile using partial update
        AnalysisProfile partialUpdatedAnalysisProfile = new AnalysisProfile();
        partialUpdatedAnalysisProfile.setId(analysisProfile.getId());

        restAnalysisProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysisProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysisProfile))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
        AnalysisProfile testAnalysisProfile = analysisProfileList.get(analysisProfileList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analysisProfileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalysisProfile() throws Exception {
        int databaseSizeBeforeUpdate = analysisProfileRepository.findAll().size();
        analysisProfile.setId(count.incrementAndGet());

        // Create the AnalysisProfile
        AnalysisProfileDTO analysisProfileDTO = analysisProfileMapper.toDto(analysisProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalysisProfile in the database
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalysisProfile() throws Exception {
        // Initialize the database
        analysisProfileRepository.saveAndFlush(analysisProfile);

        int databaseSizeBeforeDelete = analysisProfileRepository.findAll().size();

        // Delete the analysisProfile
        restAnalysisProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, analysisProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalysisProfile> analysisProfileList = analysisProfileRepository.findAll();
        assertThat(analysisProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
