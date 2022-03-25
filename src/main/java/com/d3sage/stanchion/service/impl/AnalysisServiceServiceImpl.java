package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.AnalysisService;
import com.d3sage.stanchion.repository.AnalysisServiceRepository;
import com.d3sage.stanchion.service.AnalysisServiceService;
import com.d3sage.stanchion.service.dto.AnalysisServiceDTO;
import com.d3sage.stanchion.service.mapper.AnalysisServiceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalysisService}.
 */
@Service
@Transactional
public class AnalysisServiceServiceImpl implements AnalysisServiceService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceServiceImpl.class);

    private final AnalysisServiceRepository analysisServiceRepository;

    private final AnalysisServiceMapper analysisServiceMapper;

    public AnalysisServiceServiceImpl(AnalysisServiceRepository analysisServiceRepository, AnalysisServiceMapper analysisServiceMapper) {
        this.analysisServiceRepository = analysisServiceRepository;
        this.analysisServiceMapper = analysisServiceMapper;
    }

    @Override
    public AnalysisServiceDTO save(AnalysisServiceDTO analysisServiceDTO) {
        log.debug("Request to save AnalysisService : {}", analysisServiceDTO);
        AnalysisService analysisService = analysisServiceMapper.toEntity(analysisServiceDTO);
        analysisService = analysisServiceRepository.save(analysisService);
        return analysisServiceMapper.toDto(analysisService);
    }

    @Override
    public Optional<AnalysisServiceDTO> partialUpdate(AnalysisServiceDTO analysisServiceDTO) {
        log.debug("Request to partially update AnalysisService : {}", analysisServiceDTO);

        return analysisServiceRepository
            .findById(analysisServiceDTO.getId())
            .map(existingAnalysisService -> {
                analysisServiceMapper.partialUpdate(existingAnalysisService, analysisServiceDTO);

                return existingAnalysisService;
            })
            .map(analysisServiceRepository::save)
            .map(analysisServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalysisServiceDTO> findAll() {
        log.debug("Request to get all AnalysisServices");
        return analysisServiceRepository
            .findAll()
            .stream()
            .map(analysisServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalysisServiceDTO> findOne(Long id) {
        log.debug("Request to get AnalysisService : {}", id);
        return analysisServiceRepository.findById(id).map(analysisServiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalysisService : {}", id);
        analysisServiceRepository.deleteById(id);
    }
}
