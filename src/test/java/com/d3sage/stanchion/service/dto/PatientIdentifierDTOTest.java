package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientIdentifierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientIdentifierDTO.class);
        PatientIdentifierDTO patientIdentifierDTO1 = new PatientIdentifierDTO();
        patientIdentifierDTO1.setId(1L);
        PatientIdentifierDTO patientIdentifierDTO2 = new PatientIdentifierDTO();
        assertThat(patientIdentifierDTO1).isNotEqualTo(patientIdentifierDTO2);
        patientIdentifierDTO2.setId(patientIdentifierDTO1.getId());
        assertThat(patientIdentifierDTO1).isEqualTo(patientIdentifierDTO2);
        patientIdentifierDTO2.setId(2L);
        assertThat(patientIdentifierDTO1).isNotEqualTo(patientIdentifierDTO2);
        patientIdentifierDTO1.setId(null);
        assertThat(patientIdentifierDTO1).isNotEqualTo(patientIdentifierDTO2);
    }
}
