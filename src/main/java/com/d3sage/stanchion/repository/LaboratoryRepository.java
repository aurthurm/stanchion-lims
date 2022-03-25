package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.Laboratory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Laboratory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {}
