package com.d3sage.stanchion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.d3sage.stanchion.domain.PatientIdentifier} entity.
 */
public class PatientIdentifierDTO implements Serializable {

    private Long id;

    private String type;

    private String value;

    private PatientIdentifierTypeDTO type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PatientIdentifierTypeDTO getType() {
        return type;
    }

    public void setType(PatientIdentifierTypeDTO type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientIdentifierDTO)) {
            return false;
        }

        PatientIdentifierDTO patientIdentifierDTO = (PatientIdentifierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientIdentifierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientIdentifierDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", type=" + getType() +
            "}";
    }
}
