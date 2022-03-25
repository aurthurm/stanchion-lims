package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LaboratoryMapperTest {

    private LaboratoryMapper laboratoryMapper;

    @BeforeEach
    public void setUp() {
        laboratoryMapper = new LaboratoryMapperImpl();
    }
}
