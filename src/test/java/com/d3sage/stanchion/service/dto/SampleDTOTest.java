package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SampleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleDTO.class);
        SampleDTO sampleDTO1 = new SampleDTO();
        sampleDTO1.setId(1L);
        SampleDTO sampleDTO2 = new SampleDTO();
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
        sampleDTO2.setId(sampleDTO1.getId());
        assertThat(sampleDTO1).isEqualTo(sampleDTO2);
        sampleDTO2.setId(2L);
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
        sampleDTO1.setId(null);
        assertThat(sampleDTO1).isNotEqualTo(sampleDTO2);
    }
}
