package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.LaboratoryRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LaboratoryRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratoryRequestRepository extends JpaRepository<LaboratoryRequest, Long> {}
