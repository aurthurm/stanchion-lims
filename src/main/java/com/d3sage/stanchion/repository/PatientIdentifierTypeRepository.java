package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.PatientIdentifierType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PatientIdentifierType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientIdentifierTypeRepository extends JpaRepository<PatientIdentifierType, Long> {}
