package com.d3sage.stanchion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalysisServiceMapperTest {

    private AnalysisServiceMapper analysisServiceMapper;

    @BeforeEach
    public void setUp() {
        analysisServiceMapper = new AnalysisServiceMapperImpl();
    }
}
