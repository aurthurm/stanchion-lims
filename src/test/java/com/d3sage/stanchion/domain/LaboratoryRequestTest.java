package com.d3sage.stanchion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LaboratoryRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratoryRequest.class);
        LaboratoryRequest laboratoryRequest1 = new LaboratoryRequest();
        laboratoryRequest1.setId(1L);
        LaboratoryRequest laboratoryRequest2 = new LaboratoryRequest();
        laboratoryRequest2.setId(laboratoryRequest1.getId());
        assertThat(laboratoryRequest1).isEqualTo(laboratoryRequest2);
        laboratoryRequest2.setId(2L);
        assertThat(laboratoryRequest1).isNotEqualTo(laboratoryRequest2);
        laboratoryRequest1.setId(null);
        assertThat(laboratoryRequest1).isNotEqualTo(laboratoryRequest2);
    }
}
