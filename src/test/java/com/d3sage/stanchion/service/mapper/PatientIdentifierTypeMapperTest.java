package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientIdentifierTypeMapperTest {

    private PatientIdentifierTypeMapper patientIdentifierTypeMapper;

    @BeforeEach
    public void setUp() {
        patientIdentifierTypeMapper = new PatientIdentifierTypeMapperImpl();
    }
}
