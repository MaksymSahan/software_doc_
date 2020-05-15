package com.sahan.t_mobile.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Admin.
 */
@Entity
@Table(name = "admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "updatedBy")
    private Set<TariffPlan> updatedTariffs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "admin_who_helped",
               joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "who_helped_id", referencedColumnName = "id"))
    private Set<MobileUser> whoHelpeds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Admin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TariffPlan> getUpdatedTariffs() {
        return updatedTariffs;
    }

    public Admin updatedTariffs(Set<TariffPlan> tariffPlans) {
        this.updatedTariffs = tariffPlans;
        return this;
    }

    public Admin addUpdatedTariffs(TariffPlan tariffPlan) {
        this.updatedTariffs.add(tariffPlan);
        tariffPlan.setUpdatedBy(this);
        return this;
    }

    public Admin removeUpdatedTariffs(TariffPlan tariffPlan) {
        this.updatedTariffs.remove(tariffPlan);
        tariffPlan.setUpdatedBy(null);
        return this;
    }

    public void setUpdatedTariffs(Set<TariffPlan> tariffPlans) {
        this.updatedTariffs = tariffPlans;
    }

    public Set<MobileUser> getWhoHelpeds() {
        return whoHelpeds;
    }

    public Admin whoHelpeds(Set<MobileUser> mobileUsers) {
        this.whoHelpeds = mobileUsers;
        return this;
    }

    public Admin addWhoHelped(MobileUser mobileUser) {
        this.whoHelpeds.add(mobileUser);
        mobileUser.getSupportedBies().add(this);
        return this;
    }

    public Admin removeWhoHelped(MobileUser mobileUser) {
        this.whoHelpeds.remove(mobileUser);
        mobileUser.getSupportedBies().remove(this);
        return this;
    }

    public void setWhoHelpeds(Set<MobileUser> mobileUsers) {
        this.whoHelpeds = mobileUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        return id != null && id.equals(((Admin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
