package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.AnalysisProfile;
import com.d3sage.stanchion.repository.AnalysisProfileRepository;
import com.d3sage.stanchion.service.AnalysisProfileService;
import com.d3sage.stanchion.service.dto.AnalysisProfileDTO;
import com.d3sage.stanchion.service.mapper.AnalysisProfileMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalysisProfile}.
 */
@Service
@Transactional
public class AnalysisProfileServiceImpl implements AnalysisProfileService {

    private final Logger log = LoggerFactory.getLogger(AnalysisProfileServiceImpl.class);

    private final AnalysisProfileRepository analysisProfileRepository;

    private final AnalysisProfileMapper analysisProfileMapper;

    public AnalysisProfileServiceImpl(AnalysisProfileRepository analysisProfileRepository, AnalysisProfileMapper analysisProfileMapper) {
        this.analysisProfileRepository = analysisProfileRepository;
        this.analysisProfileMapper = analysisProfileMapper;
    }

    @Override
    public AnalysisProfileDTO save(AnalysisProfileDTO analysisProfileDTO) {
        log.debug("Request to save AnalysisProfile : {}", analysisProfileDTO);
        AnalysisProfile analysisProfile = analysisProfileMapper.toEntity(analysisProfileDTO);
        analysisProfile = analysisProfileRepository.save(analysisProfile);
        return analysisProfileMapper.toDto(analysisProfile);
    }

    @Override
    public Optional<AnalysisProfileDTO> partialUpdate(AnalysisProfileDTO analysisProfileDTO) {
        log.debug("Request to partially update AnalysisProfile : {}", analysisProfileDTO);

        return analysisProfileRepository
            .findById(analysisProfileDTO.getId())
            .map(existingAnalysisProfile -> {
                analysisProfileMapper.partialUpdate(existingAnalysisProfile, analysisProfileDTO);

                return existingAnalysisProfile;
            })
            .map(analysisProfileRepository::save)
            .map(analysisProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalysisProfileDTO> findAll() {
        log.debug("Request to get all AnalysisProfiles");
        return analysisProfileRepository
            .findAll()
            .stream()
            .map(analysisProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalysisProfileDTO> findOne(Long id) {
        log.debug("Request to get AnalysisProfile : {}", id);
        return analysisProfileRepository.findById(id).map(analysisProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalysisProfile : {}", id);
        analysisProfileRepository.deleteById(id);
    }
}
