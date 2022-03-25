package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.PatientIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PatientIdentifier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientIdentifierRepository extends JpaRepository<PatientIdentifier, Long> {}
