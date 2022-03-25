package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.ClientContact;
import com.d3sage.stanchion.repository.ClientContactRepository;
import com.d3sage.stanchion.service.ClientContactService;
import com.d3sage.stanchion.service.dto.ClientContactDTO;
import com.d3sage.stanchion.service.mapper.ClientContactMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientContact}.
 */
@Service
@Transactional
public class ClientContactServiceImpl implements ClientContactService {

    private final Logger log = LoggerFactory.getLogger(ClientContactServiceImpl.class);

    private final ClientContactRepository clientContactRepository;

    private final ClientContactMapper clientContactMapper;

    public ClientContactServiceImpl(ClientContactRepository clientContactRepository, ClientContactMapper clientContactMapper) {
        this.clientContactRepository = clientContactRepository;
        this.clientContactMapper = clientContactMapper;
    }

    @Override
    public ClientContactDTO save(ClientContactDTO clientContactDTO) {
        log.debug("Request to save ClientContact : {}", clientContactDTO);
        ClientContact clientContact = clientContactMapper.toEntity(clientContactDTO);
        clientContact = clientContactRepository.save(clientContact);
        return clientContactMapper.toDto(clientContact);
    }

    @Override
    public Optional<ClientContactDTO> partialUpdate(ClientContactDTO clientContactDTO) {
        log.debug("Request to partially update ClientContact : {}", clientContactDTO);

        return clientContactRepository
            .findById(clientContactDTO.getId())
            .map(existingClientContact -> {
                clientContactMapper.partialUpdate(existingClientContact, clientContactDTO);

                return existingClientContact;
            })
            .map(clientContactRepository::save)
            .map(clientContactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientContactDTO> findAll() {
        log.debug("Request to get all ClientContacts");
        return clientContactRepository.findAll().stream().map(clientContactMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientContactDTO> findOne(Long id) {
        log.debug("Request to get ClientContact : {}", id);
        return clientContactRepository.findById(id).map(clientContactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientContact : {}", id);
        clientContactRepository.deleteById(id);
    }
}
