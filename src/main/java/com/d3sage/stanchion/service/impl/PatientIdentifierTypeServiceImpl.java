package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.PatientIdentifierType;
import com.d3sage.stanchion.repository.PatientIdentifierTypeRepository;
import com.d3sage.stanchion.service.PatientIdentifierTypeService;
import com.d3sage.stanchion.service.dto.PatientIdentifierTypeDTO;
import com.d3sage.stanchion.service.mapper.PatientIdentifierTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PatientIdentifierType}.
 */
@Service
@Transactional
public class PatientIdentifierTypeServiceImpl implements PatientIdentifierTypeService {

    private final Logger log = LoggerFactory.getLogger(PatientIdentifierTypeServiceImpl.class);

    private final PatientIdentifierTypeRepository patientIdentifierTypeRepository;

    private final PatientIdentifierTypeMapper patientIdentifierTypeMapper;

    public PatientIdentifierTypeServiceImpl(
        PatientIdentifierTypeRepository patientIdentifierTypeRepository,
        PatientIdentifierTypeMapper patientIdentifierTypeMapper
    ) {
        this.patientIdentifierTypeRepository = patientIdentifierTypeRepository;
        this.patientIdentifierTypeMapper = patientIdentifierTypeMapper;
    }

    @Override
    public PatientIdentifierTypeDTO save(PatientIdentifierTypeDTO patientIdentifierTypeDTO) {
        log.debug("Request to save PatientIdentifierType : {}", patientIdentifierTypeDTO);
        PatientIdentifierType patientIdentifierType = patientIdentifierTypeMapper.toEntity(patientIdentifierTypeDTO);
        patientIdentifierType = patientIdentifierTypeRepository.save(patientIdentifierType);
        return patientIdentifierTypeMapper.toDto(patientIdentifierType);
    }

    @Override
    public Optional<PatientIdentifierTypeDTO> partialUpdate(PatientIdentifierTypeDTO patientIdentifierTypeDTO) {
        log.debug("Request to partially update PatientIdentifierType : {}", patientIdentifierTypeDTO);

        return patientIdentifierTypeRepository
            .findById(patientIdentifierTypeDTO.getId())
            .map(existingPatientIdentifierType -> {
                patientIdentifierTypeMapper.partialUpdate(existingPatientIdentifierType, patientIdentifierTypeDTO);

                return existingPatientIdentifierType;
            })
            .map(patientIdentifierTypeRepository::save)
            .map(patientIdentifierTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientIdentifierTypeDTO> findAll() {
        log.debug("Request to get all PatientIdentifierTypes");
        return patientIdentifierTypeRepository
            .findAll()
            .stream()
            .map(patientIdentifierTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientIdentifierTypeDTO> findOne(Long id) {
        log.debug("Request to get PatientIdentifierType : {}", id);
        return patientIdentifierTypeRepository.findById(id).map(patientIdentifierTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientIdentifierType : {}", id);
        patientIdentifierTypeRepository.deleteById(id);
    }
}
