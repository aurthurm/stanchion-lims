package com.d3sage.stanchion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.d3sage.stanchion.domain.Patient} entity.
 */
public class PatientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private PatientIdentifierDTO identifier;

    private LaboratoryRequestDTO laboratoryRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PatientIdentifierDTO getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PatientIdentifierDTO identifier) {
        this.identifier = identifier;
    }

    public LaboratoryRequestDTO getLaboratoryRequest() {
        return laboratoryRequest;
    }

    public void setLaboratoryRequest(LaboratoryRequestDTO laboratoryRequest) {
        this.laboratoryRequest = laboratoryRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", identifier=" + getIdentifier() +
            ", laboratoryRequest=" + getLaboratoryRequest() +
            "}";
    }
}
