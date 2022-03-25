package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LaboratoryRequestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratoryRequestDTO.class);
        LaboratoryRequestDTO laboratoryRequestDTO1 = new LaboratoryRequestDTO();
        laboratoryRequestDTO1.setId(1L);
        LaboratoryRequestDTO laboratoryRequestDTO2 = new LaboratoryRequestDTO();
        assertThat(laboratoryRequestDTO1).isNotEqualTo(laboratoryRequestDTO2);
        laboratoryRequestDTO2.setId(laboratoryRequestDTO1.getId());
        assertThat(laboratoryRequestDTO1).isEqualTo(laboratoryRequestDTO2);
        laboratoryRequestDTO2.setId(2L);
        assertThat(laboratoryRequestDTO1).isNotEqualTo(laboratoryRequestDTO2);
        laboratoryRequestDTO1.setId(null);
        assertThat(laboratoryRequestDTO1).isNotEqualTo(laboratoryRequestDTO2);
    }
}
