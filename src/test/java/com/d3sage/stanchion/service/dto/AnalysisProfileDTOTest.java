package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalysisProfileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisProfileDTO.class);
        AnalysisProfileDTO analysisProfileDTO1 = new AnalysisProfileDTO();
        analysisProfileDTO1.setId(1L);
        AnalysisProfileDTO analysisProfileDTO2 = new AnalysisProfileDTO();
        assertThat(analysisProfileDTO1).isNotEqualTo(analysisProfileDTO2);
        analysisProfileDTO2.setId(analysisProfileDTO1.getId());
        assertThat(analysisProfileDTO1).isEqualTo(analysisProfileDTO2);
        analysisProfileDTO2.setId(2L);
        assertThat(analysisProfileDTO1).isNotEqualTo(analysisProfileDTO2);
        analysisProfileDTO1.setId(null);
        assertThat(analysisProfileDTO1).isNotEqualTo(analysisProfileDTO2);
    }
}
