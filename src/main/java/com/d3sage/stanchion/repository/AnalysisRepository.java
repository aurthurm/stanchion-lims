package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.Analysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Analysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {}
