package com.d3sage.stanchion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.d3sage.stanchion.domain.ClientContact} entity.
 */
public class ClientContactDTO implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientContactDTO)) {
            return false;
        }

        ClientContactDTO clientContactDTO = (ClientContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
