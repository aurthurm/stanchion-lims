package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.Laboratory;
import com.d3sage.stanchion.repository.LaboratoryRepository;
import com.d3sage.stanchion.service.LaboratoryService;
import com.d3sage.stanchion.service.dto.LaboratoryDTO;
import com.d3sage.stanchion.service.mapper.LaboratoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Laboratory}.
 */
@Service
@Transactional
public class LaboratoryServiceImpl implements LaboratoryService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryServiceImpl.class);

    private final LaboratoryRepository laboratoryRepository;

    private final LaboratoryMapper laboratoryMapper;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository, LaboratoryMapper laboratoryMapper) {
        this.laboratoryRepository = laboratoryRepository;
        this.laboratoryMapper = laboratoryMapper;
    }

    @Override
    public LaboratoryDTO save(LaboratoryDTO laboratoryDTO) {
        log.debug("Request to save Laboratory : {}", laboratoryDTO);
        Laboratory laboratory = laboratoryMapper.toEntity(laboratoryDTO);
        laboratory = laboratoryRepository.save(laboratory);
        return laboratoryMapper.toDto(laboratory);
    }

    @Override
    public Optional<LaboratoryDTO> partialUpdate(LaboratoryDTO laboratoryDTO) {
        log.debug("Request to partially update Laboratory : {}", laboratoryDTO);

        return laboratoryRepository
            .findById(laboratoryDTO.getId())
            .map(existingLaboratory -> {
                laboratoryMapper.partialUpdate(existingLaboratory, laboratoryDTO);

                return existingLaboratory;
            })
            .map(laboratoryRepository::save)
            .map(laboratoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LaboratoryDTO> findAll() {
        log.debug("Request to get all Laboratories");
        return laboratoryRepository.findAll().stream().map(laboratoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LaboratoryDTO> findOne(Long id) {
        log.debug("Request to get Laboratory : {}", id);
        return laboratoryRepository.findById(id).map(laboratoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Laboratory : {}", id);
        laboratoryRepository.deleteById(id);
    }
}
