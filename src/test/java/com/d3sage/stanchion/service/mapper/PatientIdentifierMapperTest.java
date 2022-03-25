package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientIdentifierMapperTest {

    private PatientIdentifierMapper patientIdentifierMapper;

    @BeforeEach
    public void setUp() {
        patientIdentifierMapper = new PatientIdentifierMapperImpl();
    }
}
