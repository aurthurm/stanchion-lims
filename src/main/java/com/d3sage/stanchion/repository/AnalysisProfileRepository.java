package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.AnalysisProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalysisProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisProfileRepository extends JpaRepository<AnalysisProfile, Long> {}
