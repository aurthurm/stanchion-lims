package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SampleTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleTypeDTO.class);
        SampleTypeDTO sampleTypeDTO1 = new SampleTypeDTO();
        sampleTypeDTO1.setId(1L);
        SampleTypeDTO sampleTypeDTO2 = new SampleTypeDTO();
        assertThat(sampleTypeDTO1).isNotEqualTo(sampleTypeDTO2);
        sampleTypeDTO2.setId(sampleTypeDTO1.getId());
        assertThat(sampleTypeDTO1).isEqualTo(sampleTypeDTO2);
        sampleTypeDTO2.setId(2L);
        assertThat(sampleTypeDTO1).isNotEqualTo(sampleTypeDTO2);
        sampleTypeDTO1.setId(null);
        assertThat(sampleTypeDTO1).isNotEqualTo(sampleTypeDTO2);
    }
}
