package com.d3sage.stanchion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientIdentifierTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientIdentifierTypeDTO.class);
        PatientIdentifierTypeDTO patientIdentifierTypeDTO1 = new PatientIdentifierTypeDTO();
        patientIdentifierTypeDTO1.setId(1L);
        PatientIdentifierTypeDTO patientIdentifierTypeDTO2 = new PatientIdentifierTypeDTO();
        assertThat(patientIdentifierTypeDTO1).isNotEqualTo(patientIdentifierTypeDTO2);
        patientIdentifierTypeDTO2.setId(patientIdentifierTypeDTO1.getId());
        assertThat(patientIdentifierTypeDTO1).isEqualTo(patientIdentifierTypeDTO2);
        patientIdentifierTypeDTO2.setId(2L);
        assertThat(patientIdentifierTypeDTO1).isNotEqualTo(patientIdentifierTypeDTO2);
        patientIdentifierTypeDTO1.setId(null);
        assertThat(patientIdentifierTypeDTO1).isNotEqualTo(patientIdentifierTypeDTO2);
    }
}
