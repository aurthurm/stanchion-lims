package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalysisMapperTest {

    private AnalysisMapper analysisMapper;

    @BeforeEach
    public void setUp() {
        analysisMapper = new AnalysisMapperImpl();
    }
}
