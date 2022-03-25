package com.d3sage.stanchion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.d3sage.stanchion.domain.LaboratoryRequest} entity.
 */
public class LaboratoryRequestDTO implements Serializable {

    private Long id;

    private String clientRequestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratoryRequestDTO)) {
            return false;
        }

        LaboratoryRequestDTO laboratoryRequestDTO = (LaboratoryRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, laboratoryRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LaboratoryRequestDTO{" +
            "id=" + getId() +
            ", clientRequestId='" + getClientRequestId() + "'" +
            "}";
    }
}
