package com.d3sage.stanchion.service;

import com.d3sage.stanchion.domain.Job;
import com.d3sage.stanchion.repository.JobRepository;
import com.d3sage.stanchion.service.dto.JobDTO;
import com.d3sage.stanchion.service.mapper.JobMapper;
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
 * Service Implementation for managing {@link Job}.
 */
@Service
@Transactional
public class JobService {

    private final Logger log = LoggerFactory.getLogger(JobService.class);

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    /**
     * Save a job.
     *
     * @param jobDTO the entity to save.
     * @return the persisted entity.
     */
    public JobDTO save(JobDTO jobDTO) {
        log.debug("Request to save Job : {}", jobDTO);
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);
        return jobMapper.toDto(job);
    }

    /**
     * Partially update a job.
     *
     * @param jobDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobDTO> partialUpdate(JobDTO jobDTO) {
        log.debug("Request to partially update Job : {}", jobDTO);

        return jobRepository
            .findById(jobDTO.getId())
            .map(existingJob -> {
                jobMapper.partialUpdate(existingJob, jobDTO);

                return existingJob;
            })
            .map(jobRepository::save)
            .map(jobMapper::toDto);
    }

    /**
     * Get all the jobs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JobDTO> findAll() {
        log.debug("Request to get all Jobs");
        return jobRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(jobMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the jobs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<JobDTO> findAllWithEagerRelationships(Pageable pageable) {
        return jobRepository.findAllWithEagerRelationships(pageable).map(jobMapper::toDto);
    }

    /**
     * Get one job by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobDTO> findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        return jobRepository.findOneWithEagerRelationships(id).map(jobMapper::toDto);
    }

    /**
     * Delete the job by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Job : {}", id);
        jobRepository.deleteById(id);
    }
}
