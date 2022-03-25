package com.d3sage.stanchion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.d3sage.stanchion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientIdentifierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientIdentifier.class);
        PatientIdentifier patientIdentifier1 = new PatientIdentifier();
        patientIdentifier1.setId(1L);
        PatientIdentifier patientIdentifier2 = new PatientIdentifier();
        patientIdentifier2.setId(patientIdentifier1.getId());
        assertThat(patientIdentifier1).isEqualTo(patientIdentifier2);
        patientIdentifier2.setId(2L);
        assertThat(patientIdentifier1).isNotEqualTo(patientIdentifier2);
        patientIdentifier1.setId(null);
        assertThat(patientIdentifier1).isNotEqualTo(patientIdentifier2);
    }
}
