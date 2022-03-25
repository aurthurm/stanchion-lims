package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.PatientIdentifier;
import com.d3sage.stanchion.repository.PatientIdentifierRepository;
import com.d3sage.stanchion.service.PatientIdentifierService;
import com.d3sage.stanchion.service.dto.PatientIdentifierDTO;
import com.d3sage.stanchion.service.mapper.PatientIdentifierMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PatientIdentifier}.
 */
@Service
@Transactional
public class PatientIdentifierServiceImpl implements PatientIdentifierService {

    private final Logger log = LoggerFactory.getLogger(PatientIdentifierServiceImpl.class);

    private final PatientIdentifierRepository patientIdentifierRepository;

    private final PatientIdentifierMapper patientIdentifierMapper;

    public PatientIdentifierServiceImpl(
        PatientIdentifierRepository patientIdentifierRepository,
        PatientIdentifierMapper patientIdentifierMapper
    ) {
        this.patientIdentifierRepository = patientIdentifierRepository;
        this.patientIdentifierMapper = patientIdentifierMapper;
    }

    @Override
    public PatientIdentifierDTO save(PatientIdentifierDTO patientIdentifierDTO) {
        log.debug("Request to save PatientIdentifier : {}", patientIdentifierDTO);
        PatientIdentifier patientIdentifier = patientIdentifierMapper.toEntity(patientIdentifierDTO);
        patientIdentifier = patientIdentifierRepository.save(patientIdentifier);
        return patientIdentifierMapper.toDto(patientIdentifier);
    }

    @Override
    public Optional<PatientIdentifierDTO> partialUpdate(PatientIdentifierDTO patientIdentifierDTO) {
        log.debug("Request to partially update PatientIdentifier : {}", patientIdentifierDTO);

        return patientIdentifierRepository
            .findById(patientIdentifierDTO.getId())
            .map(existingPatientIdentifier -> {
                patientIdentifierMapper.partialUpdate(existingPatientIdentifier, patientIdentifierDTO);

                return existingPatientIdentifier;
            })
            .map(patientIdentifierRepository::save)
            .map(patientIdentifierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientIdentifierDTO> findAll() {
        log.debug("Request to get all PatientIdentifiers");
        return patientIdentifierRepository
            .findAll()
            .stream()
            .map(patientIdentifierMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientIdentifierDTO> findOne(Long id) {
        log.debug("Request to get PatientIdentifier : {}", id);
        return patientIdentifierRepository.findById(id).map(patientIdentifierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientIdentifier : {}", id);
        patientIdentifierRepository.deleteById(id);
    }
}
