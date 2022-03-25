package com.d3sage.stanchion.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LaboratoryRequest.
 */
@Entity
@Table(name = "laboratory_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LaboratoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_request_id")
    private String clientRequestId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LaboratoryRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientRequestId() {
        return this.clientRequestId;
    }

    public LaboratoryRequest clientRequestId(String clientRequestId) {
        this.setClientRequestId(clientRequestId);
        return this;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratoryRequest)) {
            return false;
        }
        return id != null && id.equals(((LaboratoryRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LaboratoryRequest{" +
            "id=" + getId() +
            ", clientRequestId='" + getClientRequestId() + "'" +
            "}";
    }
}
