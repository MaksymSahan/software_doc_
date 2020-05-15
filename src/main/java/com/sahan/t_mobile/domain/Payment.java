package com.sahan.t_mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_provider")
    private String transactionProvider;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private MobileUser byWho;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Payment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionProvider() {
        return transactionProvider;
    }

    public Payment transactionProvider(String transactionProvider) {
        this.transactionProvider = transactionProvider;
        return this;
    }

    public void setTransactionProvider(String transactionProvider) {
        this.transactionProvider = transactionProvider;
    }

    public MobileUser getByWho() {
        return byWho;
    }

    public Payment byWho(MobileUser mobileUser) {
        this.byWho = mobileUser;
        return this;
    }

    public void setByWho(MobileUser mobileUser) {
        this.byWho = mobileUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", transactionProvider='" + getTransactionProvider() + "'" +
            "}";
    }
}
