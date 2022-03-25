package com.d3sage.stanchion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.d3sage.stanchion.IntegrationTest;
import com.d3sage.stanchion.domain.ClientContact;
import com.d3sage.stanchion.repository.ClientContactRepository;
import com.d3sage.stanchion.service.dto.ClientContactDTO;
import com.d3sage.stanchion.service.mapper.ClientContactMapper;
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
 * Integration tests for the {@link ClientContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientContactRepository clientContactRepository;

    @Autowired
    private ClientContactMapper clientContactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientContactMockMvc;

    private ClientContact clientContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientContact createEntity(EntityManager em) {
        ClientContact clientContact = new ClientContact().name(DEFAULT_NAME);
        return clientContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientContact createUpdatedEntity(EntityManager em) {
        ClientContact clientContact = new ClientContact().name(UPDATED_NAME);
        return clientContact;
    }

    @BeforeEach
    public void initTest() {
        clientContact = createEntity(em);
    }

    @Test
    @Transactional
    void createClientContact() throws Exception {
        int databaseSizeBeforeCreate = clientContactRepository.findAll().size();
        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);
        restClientContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeCreate + 1);
        ClientContact testClientContact = clientContactList.get(clientContactList.size() - 1);
        assertThat(testClientContact.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createClientContactWithExistingId() throws Exception {
        // Create the ClientContact with an existing ID
        clientContact.setId(1L);
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        int databaseSizeBeforeCreate = clientContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientContacts() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        // Get all the clientContactList
        restClientContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getClientContact() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        // Get the clientContact
        restClientContactMockMvc
            .perform(get(ENTITY_API_URL_ID, clientContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingClientContact() throws Exception {
        // Get the clientContact
        restClientContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClientContact() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();

        // Update the clientContact
        ClientContact updatedClientContact = clientContactRepository.findById(clientContact.getId()).get();
        // Disconnect from session so that the updates on updatedClientContact are not directly saved in db
        em.detach(updatedClientContact);
        updatedClientContact.name(UPDATED_NAME);
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(updatedClientContact);

        restClientContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
        ClientContact testClientContact = clientContactList.get(clientContactList.size() - 1);
        assertThat(testClientContact.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientContactWithPatch() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();

        // Update the clientContact using partial update
        ClientContact partialUpdatedClientContact = new ClientContact();
        partialUpdatedClientContact.setId(clientContact.getId());

        partialUpdatedClientContact.name(UPDATED_NAME);

        restClientContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientContact))
            )
            .andExpect(status().isOk());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
        ClientContact testClientContact = clientContactList.get(clientContactList.size() - 1);
        assertThat(testClientContact.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateClientContactWithPatch() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();

        // Update the clientContact using partial update
        ClientContact partialUpdatedClientContact = new ClientContact();
        partialUpdatedClientContact.setId(clientContact.getId());

        partialUpdatedClientContact.name(UPDATED_NAME);

        restClientContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientContact))
            )
            .andExpect(status().isOk());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
        ClientContact testClientContact = clientContactList.get(clientContactList.size() - 1);
        assertThat(testClientContact.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientContactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientContact() throws Exception {
        int databaseSizeBeforeUpdate = clientContactRepository.findAll().size();
        clientContact.setId(count.incrementAndGet());

        // Create the ClientContact
        ClientContactDTO clientContactDTO = clientContactMapper.toDto(clientContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientContactMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientContact in the database
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientContact() throws Exception {
        // Initialize the database
        clientContactRepository.saveAndFlush(clientContact);

        int databaseSizeBeforeDelete = clientContactRepository.findAll().size();

        // Delete the clientContact
        restClientContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientContact> clientContactList = clientContactRepository.findAll();
        assertThat(clientContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
