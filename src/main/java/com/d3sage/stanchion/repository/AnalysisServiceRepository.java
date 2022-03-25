package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.AnalysisService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalysisService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisServiceRepository extends JpaRepository<AnalysisService, Long> {}
