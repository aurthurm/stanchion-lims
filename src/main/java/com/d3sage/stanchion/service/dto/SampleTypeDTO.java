package com.d3sage.stanchion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.d3sage.stanchion.domain.SampleType} entity.
 */
public class SampleTypeDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SampleTypeDTO)) {
            return false;
        }

        SampleTypeDTO sampleTypeDTO = (SampleTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sampleTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SampleTypeDTO{" +
            "id=" + getId() +
            "}";
    }
}
