package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.SampleType;
import com.d3sage.stanchion.repository.SampleTypeRepository;
import com.d3sage.stanchion.service.SampleTypeService;
import com.d3sage.stanchion.service.dto.SampleTypeDTO;
import com.d3sage.stanchion.service.mapper.SampleTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SampleType}.
 */
@Service
@Transactional
public class SampleTypeServiceImpl implements SampleTypeService {

    private final Logger log = LoggerFactory.getLogger(SampleTypeServiceImpl.class);

    private final SampleTypeRepository sampleTypeRepository;

    private final SampleTypeMapper sampleTypeMapper;

    public SampleTypeServiceImpl(SampleTypeRepository sampleTypeRepository, SampleTypeMapper sampleTypeMapper) {
        this.sampleTypeRepository = sampleTypeRepository;
        this.sampleTypeMapper = sampleTypeMapper;
    }

    @Override
    public SampleTypeDTO save(SampleTypeDTO sampleTypeDTO) {
        log.debug("Request to save SampleType : {}", sampleTypeDTO);
        SampleType sampleType = sampleTypeMapper.toEntity(sampleTypeDTO);
        sampleType = sampleTypeRepository.save(sampleType);
        return sampleTypeMapper.toDto(sampleType);
    }

    @Override
    public Optional<SampleTypeDTO> partialUpdate(SampleTypeDTO sampleTypeDTO) {
        log.debug("Request to partially update SampleType : {}", sampleTypeDTO);

        return sampleTypeRepository
            .findById(sampleTypeDTO.getId())
            .map(existingSampleType -> {
                sampleTypeMapper.partialUpdate(existingSampleType, sampleTypeDTO);

                return existingSampleType;
            })
            .map(sampleTypeRepository::save)
            .map(sampleTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SampleTypeDTO> findAll() {
        log.debug("Request to get all SampleTypes");
        return sampleTypeRepository.findAll().stream().map(sampleTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SampleTypeDTO> findOne(Long id) {
        log.debug("Request to get SampleType : {}", id);
        return sampleTypeRepository.findById(id).map(sampleTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SampleType : {}", id);
        sampleTypeRepository.deleteById(id);
    }
}
