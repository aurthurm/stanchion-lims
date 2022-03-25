package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalysisServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisServiceDTO.class);
        AnalysisServiceDTO analysisServiceDTO1 = new AnalysisServiceDTO();
        analysisServiceDTO1.setId(1L);
        AnalysisServiceDTO analysisServiceDTO2 = new AnalysisServiceDTO();
        assertThat(analysisServiceDTO1).isNotEqualTo(analysisServiceDTO2);
        analysisServiceDTO2.setId(analysisServiceDTO1.getId());
        assertThat(analysisServiceDTO1).isEqualTo(analysisServiceDTO2);
        analysisServiceDTO2.setId(2L);
        assertThat(analysisServiceDTO1).isNotEqualTo(analysisServiceDTO2);
        analysisServiceDTO1.setId(null);
        assertThat(analysisServiceDTO1).isNotEqualTo(analysisServiceDTO2);
    }
}
