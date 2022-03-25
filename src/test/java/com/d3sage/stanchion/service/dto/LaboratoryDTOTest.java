package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LaboratoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratoryDTO.class);
        LaboratoryDTO laboratoryDTO1 = new LaboratoryDTO();
        laboratoryDTO1.setId(1L);
        LaboratoryDTO laboratoryDTO2 = new LaboratoryDTO();
        assertThat(laboratoryDTO1).isNotEqualTo(laboratoryDTO2);
        laboratoryDTO2.setId(laboratoryDTO1.getId());
        assertThat(laboratoryDTO1).isEqualTo(laboratoryDTO2);
        laboratoryDTO2.setId(2L);
        assertThat(laboratoryDTO1).isNotEqualTo(laboratoryDTO2);
        laboratoryDTO1.setId(null);
        assertThat(laboratoryDTO1).isNotEqualTo(laboratoryDTO2);
    }
}
