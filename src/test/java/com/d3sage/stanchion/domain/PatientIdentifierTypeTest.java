package com.d3sage.stanchion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientIdentifierTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientIdentifierType.class);
        PatientIdentifierType patientIdentifierType1 = new PatientIdentifierType();
        patientIdentifierType1.setId(1L);
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType();
        patientIdentifierType2.setId(patientIdentifierType1.getId());
        assertThat(patientIdentifierType1).isEqualTo(patientIdentifierType2);
        patientIdentifierType2.setId(2L);
        assertThat(patientIdentifierType1).isNotEqualTo(patientIdentifierType2);
        patientIdentifierType1.setId(null);
        assertThat(patientIdentifierType1).isNotEqualTo(patientIdentifierType2);
    }
}
