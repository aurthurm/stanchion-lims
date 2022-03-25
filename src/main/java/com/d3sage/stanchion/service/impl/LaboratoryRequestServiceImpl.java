package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.LaboratoryRequest;
import com.d3sage.stanchion.repository.LaboratoryRequestRepository;
import com.d3sage.stanchion.service.LaboratoryRequestService;
import com.d3sage.stanchion.service.dto.LaboratoryRequestDTO;
import com.d3sage.stanchion.service.mapper.LaboratoryRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LaboratoryRequest}.
 */
@Service
@Transactional
public class LaboratoryRequestServiceImpl implements LaboratoryRequestService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestServiceImpl.class);

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    private final LaboratoryRequestMapper laboratoryRequestMapper;

    public LaboratoryRequestServiceImpl(
        LaboratoryRequestRepository laboratoryRequestRepository,
        LaboratoryRequestMapper laboratoryRequestMapper
    ) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
        this.laboratoryRequestMapper = laboratoryRequestMapper;
    }

    @Override
    public LaboratoryRequestDTO save(LaboratoryRequestDTO laboratoryRequestDTO) {
        log.debug("Request to save LaboratoryRequest : {}", laboratoryRequestDTO);
        LaboratoryRequest laboratoryRequest = laboratoryRequestMapper.toEntity(laboratoryRequestDTO);
        laboratoryRequest = laboratoryRequestRepository.save(laboratoryRequest);
        return laboratoryRequestMapper.toDto(laboratoryRequest);
    }

    @Override
    public Optional<LaboratoryRequestDTO> partialUpdate(LaboratoryRequestDTO laboratoryRequestDTO) {
        log.debug("Request to partially update LaboratoryRequest : {}", laboratoryRequestDTO);

        return laboratoryRequestRepository
            .findById(laboratoryRequestDTO.getId())
            .map(existingLaboratoryRequest -> {
                laboratoryRequestMapper.partialUpdate(existingLaboratoryRequest, laboratoryRequestDTO);

                return existingLaboratoryRequest;
            })
            .map(laboratoryRequestRepository::save)
            .map(laboratoryRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LaboratoryRequestDTO> findAll() {
        log.debug("Request to get all LaboratoryRequests");
        return laboratoryRequestRepository
            .findAll()
            .stream()
            .map(laboratoryRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LaboratoryRequestDTO> findOne(Long id) {
        log.debug("Request to get LaboratoryRequest : {}", id);
        return laboratoryRequestRepository.findById(id).map(laboratoryRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LaboratoryRequest : {}", id);
        laboratoryRequestRepository.deleteById(id);
    }
}
