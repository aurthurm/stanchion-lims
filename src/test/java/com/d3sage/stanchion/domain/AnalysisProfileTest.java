package com.d3sage.stanchion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalysisProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisProfile.class);
        AnalysisProfile analysisProfile1 = new AnalysisProfile();
        analysisProfile1.setId(1L);
        AnalysisProfile analysisProfile2 = new AnalysisProfile();
        analysisProfile2.setId(analysisProfile1.getId());
        assertThat(analysisProfile1).isEqualTo(analysisProfile2);
        analysisProfile2.setId(2L);
        assertThat(analysisProfile1).isNotEqualTo(analysisProfile2);
        analysisProfile1.setId(null);
        assertThat(analysisProfile1).isNotEqualTo(analysisProfile2);
    }
}
