package com.d3sage.stanchion.repository;

import com.d3sage.stanchion.domain.ClientContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientContactRepository extends JpaRepository<ClientContact, Long> {}
