package com.d3sage.stanchion.service.impl;

import com.d3sage.stanchion.domain.Province;
import com.d3sage.stanchion.repository.ProvinceRepository;
import com.d3sage.stanchion.service.ProvinceService;
import com.d3sage.stanchion.service.dto.ProvinceDTO;
import com.d3sage.stanchion.service.mapper.ProvinceMapper;
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
 * Service Implementation for managing {@link Province}.
 */
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    private final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    private final ProvinceRepository provinceRepository;

    private final ProvinceMapper provinceMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, ProvinceMapper provinceMapper) {
        this.provinceRepository = provinceRepository;
        this.provinceMapper = provinceMapper;
    }

    @Override
    public ProvinceDTO save(ProvinceDTO provinceDTO) {
        log.debug("Request to save Province : {}", provinceDTO);
        Province province = provinceMapper.toEntity(provinceDTO);
        province = provinceRepository.save(province);
        return provinceMapper.toDto(province);
    }

    @Override
    public Optional<ProvinceDTO> partialUpdate(ProvinceDTO provinceDTO) {
        log.debug("Request to partially update Province : {}", provinceDTO);

        return provinceRepository
            .findById(provinceDTO.getId())
            .map(existingProvince -> {
                provinceMapper.partialUpdate(existingProvince, provinceDTO);

                return existingProvince;
            })
            .map(provinceRepository::save)
            .map(provinceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvinceDTO> findAll() {
        log.debug("Request to get all Provinces");
        return provinceRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(provinceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ProvinceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return provinceRepository.findAllWithEagerRelationships(pageable).map(provinceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvinceDTO> findOne(Long id) {
        log.debug("Request to get Province : {}", id);
        return provinceRepository.findOneWithEagerRelationships(id).map(provinceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Province : {}", id);
        provinceRepository.deleteById(id);
    }
}
