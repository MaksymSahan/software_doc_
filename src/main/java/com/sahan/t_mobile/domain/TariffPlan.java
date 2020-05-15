package com.sahan.t_mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A TariffPlan.
 */
@Entity
@Table(name = "tariff_plan")
public class TariffPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "internet")
    private String internet;

    @Column(name = "calls")
    private String calls;

    @Column(name = "sms")
    private String sms;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JsonIgnoreProperties("updatedTariffs")
    private Admin updatedBy;

    @ManyToMany(mappedBy = "tariffs")
    @JsonIgnore
    private Set<MobileUser> users = new HashSet<>();

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

    public TariffPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TariffPlan description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternet() {
        return internet;
    }

    public TariffPlan internet(String internet) {
        this.internet = internet;
        return this;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getCalls() {
        return calls;
    }

    public TariffPlan calls(String calls) {
        this.calls = calls;
        return this;
    }

    public void setCalls(String calls) {
        this.calls = calls;
    }

    public String getSms() {
        return sms;
    }

    public TariffPlan sms(String sms) {
        this.sms = sms;
        return this;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public Double getPrice() {
        return price;
    }

    public TariffPlan price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Admin getUpdatedBy() {
        return updatedBy;
    }

    public TariffPlan updatedBy(Admin admin) {
        this.updatedBy = admin;
        return this;
    }

    public void setUpdatedBy(Admin admin) {
        this.updatedBy = admin;
    }

    public Set<MobileUser> getUsers() {
        return users;
    }

    public TariffPlan users(Set<MobileUser> mobileUsers) {
        this.users = mobileUsers;
        return this;
    }

    public TariffPlan addUsers(MobileUser mobileUser) {
        this.users.add(mobileUser);
        mobileUser.getTariffs().add(this);
        return this;
    }

    public TariffPlan removeUsers(MobileUser mobileUser) {
        this.users.remove(mobileUser);
        mobileUser.getTariffs().remove(this);
        return this;
    }

    public void setUsers(Set<MobileUser> mobileUsers) {
        this.users = mobileUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TariffPlan)) {
            return false;
        }
        return id != null && id.equals(((TariffPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TariffPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", internet='" + getInternet() + "'" +
            ", calls='" + getCalls() + "'" +
            ", sms='" + getSms() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
