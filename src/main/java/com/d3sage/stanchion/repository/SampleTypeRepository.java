package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.SampleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SampleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleTypeRepository extends JpaRepository<SampleType, Long> {}
