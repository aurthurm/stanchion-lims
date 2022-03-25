package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.Sample;
import com.d3sage.stanchion.repository.SampleRepository;
import com.d3sage.stanchion.service.SampleService;
import com.d3sage.stanchion.service.dto.SampleDTO;
import com.d3sage.stanchion.service.mapper.SampleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sample}.
 */
@Service
@Transactional
public class SampleServiceImpl implements SampleService {

    private final Logger log = LoggerFactory.getLogger(SampleServiceImpl.class);

    private final SampleRepository sampleRepository;

    private final SampleMapper sampleMapper;

    public SampleServiceImpl(SampleRepository sampleRepository, SampleMapper sampleMapper) {
        this.sampleRepository = sampleRepository;
        this.sampleMapper = sampleMapper;
    }

    @Override
    public SampleDTO save(SampleDTO sampleDTO) {
        log.debug("Request to save Sample : {}", sampleDTO);
        Sample sample = sampleMapper.toEntity(sampleDTO);
        sample = sampleRepository.save(sample);
        return sampleMapper.toDto(sample);
    }

    @Override
    public Optional<SampleDTO> partialUpdate(SampleDTO sampleDTO) {
        log.debug("Request to partially update Sample : {}", sampleDTO);

        return sampleRepository
            .findById(sampleDTO.getId())
            .map(existingSample -> {
                sampleMapper.partialUpdate(existingSample, sampleDTO);

                return existingSample;
            })
            .map(sampleRepository::save)
            .map(sampleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SampleDTO> findAll() {
        log.debug("Request to get all Samples");
        return sampleRepository.findAll().stream().map(sampleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SampleDTO> findOne(Long id) {
        log.debug("Request to get Sample : {}", id);
        return sampleRepository.findById(id).map(sampleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sample : {}", id);
        sampleRepository.deleteById(id);
    }
}
