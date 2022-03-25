package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.LaboratoryRequest;
import com.d3sage.stanchion.repository.LaboratoryRequestRepository;
import com.d3sage.stanchion.service.dto.LaboratoryRequestDTO;
import com.d3sage.stanchion.service.mapper.LaboratoryRequestMapper;
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
 * Integration tests for the {@link LaboratoryRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LaboratoryRequestResourceIT {

    private static final String DEFAULT_CLIENT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_REQUEST_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/laboratory-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LaboratoryRequestRepository laboratoryRequestRepository;

    @Autowired
    private LaboratoryRequestMapper laboratoryRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLaboratoryRequestMockMvc;

    private LaboratoryRequest laboratoryRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratoryRequest createEntity(EntityManager em) {
        LaboratoryRequest laboratoryRequest = new LaboratoryRequest().clientRequestId(DEFAULT_CLIENT_REQUEST_ID);
        return laboratoryRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratoryRequest createUpdatedEntity(EntityManager em) {
        LaboratoryRequest laboratoryRequest = new LaboratoryRequest().clientRequestId(UPDATED_CLIENT_REQUEST_ID);
        return laboratoryRequest;
    }

    @BeforeEach
    public void initTest() {
        laboratoryRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createLaboratoryRequest() throws Exception {
        int databaseSizeBeforeCreate = laboratoryRequestRepository.findAll().size();
        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);
        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeCreate + 1);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getClientRequestId()).isEqualTo(DEFAULT_CLIENT_REQUEST_ID);
    }

    @Test
    @Transactional
    void createLaboratoryRequestWithExistingId() throws Exception {
        // Create the LaboratoryRequest with an existing ID
        laboratoryRequest.setId(1L);
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        int databaseSizeBeforeCreate = laboratoryRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLaboratoryRequests() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        // Get all the laboratoryRequestList
        restLaboratoryRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratoryRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientRequestId").value(hasItem(DEFAULT_CLIENT_REQUEST_ID)));
    }

    @Test
    @Transactional
    void getLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        // Get the laboratoryRequest
        restLaboratoryRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, laboratoryRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(laboratoryRequest.getId().intValue()))
            .andExpect(jsonPath("$.clientRequestId").value(DEFAULT_CLIENT_REQUEST_ID));
    }

    @Test
    @Transactional
    void getNonExistingLaboratoryRequest() throws Exception {
        // Get the laboratoryRequest
        restLaboratoryRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest
        LaboratoryRequest updatedLaboratoryRequest = laboratoryRequestRepository.findById(laboratoryRequest.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratoryRequest are not directly saved in db
        em.detach(updatedLaboratoryRequest);
        updatedLaboratoryRequest.clientRequestId(UPDATED_CLIENT_REQUEST_ID);
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(updatedLaboratoryRequest);

        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratoryRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getClientRequestId()).isEqualTo(UPDATED_CLIENT_REQUEST_ID);
    }

    @Test
    @Transactional
    void putNonExistingLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratoryRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLaboratoryRequestWithPatch() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest using partial update
        LaboratoryRequest partialUpdatedLaboratoryRequest = new LaboratoryRequest();
        partialUpdatedLaboratoryRequest.setId(laboratoryRequest.getId());

        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratoryRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratoryRequest))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getClientRequestId()).isEqualTo(DEFAULT_CLIENT_REQUEST_ID);
    }

    @Test
    @Transactional
    void fullUpdateLaboratoryRequestWithPatch() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest using partial update
        LaboratoryRequest partialUpdatedLaboratoryRequest = new LaboratoryRequest();
        partialUpdatedLaboratoryRequest.setId(laboratoryRequest.getId());

        partialUpdatedLaboratoryRequest.clientRequestId(UPDATED_CLIENT_REQUEST_ID);

        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratoryRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratoryRequest))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getClientRequestId()).isEqualTo(UPDATED_CLIENT_REQUEST_ID);
    }

    @Test
    @Transactional
    void patchNonExistingLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, laboratoryRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // Create the LaboratoryRequest
        LaboratoryRequestDTO laboratoryRequestDTO = laboratoryRequestMapper.toDto(laboratoryRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeDelete = laboratoryRequestRepository.findAll().size();

        // Delete the laboratoryRequest
        restLaboratoryRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, laboratoryRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
