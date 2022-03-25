package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalysisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisDTO.class);
        AnalysisDTO analysisDTO1 = new AnalysisDTO();
        analysisDTO1.setId(1L);
        AnalysisDTO analysisDTO2 = new AnalysisDTO();
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
        analysisDTO2.setId(analysisDTO1.getId());
        assertThat(analysisDTO1).isEqualTo(analysisDTO2);
        analysisDTO2.setId(2L);
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
        analysisDTO1.setId(null);
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
    }
}
