package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.Analysis;
import com.d3sage.stanchion.repository.AnalysisRepository;
import com.d3sage.stanchion.service.AnalysisService;
import com.d3sage.stanchion.service.dto.AnalysisDTO;
import com.d3sage.stanchion.service.mapper.AnalysisMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Analysis}.
 */
@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;

    private final AnalysisMapper analysisMapper;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, AnalysisMapper analysisMapper) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
    }

    @Override
    public AnalysisDTO save(AnalysisDTO analysisDTO) {
        log.debug("Request to save Analysis : {}", analysisDTO);
        Analysis analysis = analysisMapper.toEntity(analysisDTO);
        analysis = analysisRepository.save(analysis);
        return analysisMapper.toDto(analysis);
    }

    @Override
    public Optional<AnalysisDTO> partialUpdate(AnalysisDTO analysisDTO) {
        log.debug("Request to partially update Analysis : {}", analysisDTO);

        return analysisRepository
            .findById(analysisDTO.getId())
            .map(existingAnalysis -> {
                analysisMapper.partialUpdate(existingAnalysis, analysisDTO);

                return existingAnalysis;
            })
            .map(analysisRepository::save)
            .map(analysisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalysisDTO> findAll() {
        log.debug("Request to get all Analyses");
        return analysisRepository.findAll().stream().map(analysisMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalysisDTO> findOne(Long id) {
        log.debug("Request to get Analysis : {}", id);
        return analysisRepository.findById(id).map(analysisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Analysis : {}", id);
        analysisRepository.deleteById(id);
    }
}
