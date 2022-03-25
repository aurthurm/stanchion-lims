package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.Patient;
import com.d3sage.stanchion.repository.PatientRepository;
import com.d3sage.stanchion.service.PatientService;
import com.d3sage.stanchion.service.dto.PatientDTO;
import com.d3sage.stanchion.service.mapper.PatientMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public Optional<PatientDTO> partialUpdate(PatientDTO patientDTO) {
        log.debug("Request to partially update Patient : {}", patientDTO);

        return patientRepository
            .findById(patientDTO.getId())
            .map(existingPatient -> {
                patientMapper.partialUpdate(existingPatient, patientDTO);

                return existingPatient;
            })
            .map(patientRepository::save)
            .map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        log.debug("Request to get all Patients");
        return patientRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(patientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<PatientDTO> findAllWithEagerRelationships(Pageable pageable) {
        return patientRepository.findAllWithEagerRelationships(pageable).map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findOneWithEagerRelationships(id).map(patientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
