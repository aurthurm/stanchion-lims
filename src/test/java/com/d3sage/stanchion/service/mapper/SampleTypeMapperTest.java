package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SampleTypeMapperTest {

    private SampleTypeMapper sampleTypeMapper;

    @BeforeEach
    public void setUp() {
        sampleTypeMapper = new SampleTypeMapperImpl();
    }
}
