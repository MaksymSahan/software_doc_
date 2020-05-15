package com.sahan.t_mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A MobileUser.
 */
@Entity
@Table(name = "mobile_user")
public class MobileUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @NotNull
    @Size(max = 100)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @Column(name = "phone", unique = true)
    private String phone;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @OneToMany(mappedBy = "byWho")
    private Set<Payment> payments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "mobile_user_tariffs",
               joinColumns = @JoinColumn(name = "mobile_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tariffs_id", referencedColumnName = "id"))
    private Set<TariffPlan> tariffs = new HashSet<>();

    @ManyToMany(mappedBy = "whoHelpeds")
    @JsonIgnore
    private Set<Admin> supportedBies = new HashSet<>();

    @ManyToMany(mappedBy = "whoOrdereds")
    @JsonIgnore
    private Set<OrderTariffPlan> tariffPlanOrders = new HashSet<>();

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

    public MobileUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public MobileUser surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public MobileUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public MobileUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public MobileUser address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public MobileUser payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public MobileUser addPayments(Payment payment) {
        this.payments.add(payment);
        payment.setByWho(this);
        return this;
    }

    public MobileUser removePayments(Payment payment) {
        this.payments.remove(payment);
        payment.setByWho(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<TariffPlan> getTariffs() {
        return tariffs;
    }

    public MobileUser tariffs(Set<TariffPlan> tariffPlans) {
        this.tariffs = tariffPlans;
        return this;
    }

    public MobileUser addTariffs(TariffPlan tariffPlan) {
        this.tariffs.add(tariffPlan);
        tariffPlan.getUsers().add(this);
        return this;
    }

    public MobileUser removeTariffs(TariffPlan tariffPlan) {
        this.tariffs.remove(tariffPlan);
        tariffPlan.getUsers().remove(this);
        return this;
    }

    public void setTariffs(Set<TariffPlan> tariffPlans) {
        this.tariffs = tariffPlans;
    }

    public Set<Admin> getSupportedBies() {
        return supportedBies;
    }

    public MobileUser supportedBies(Set<Admin> admins) {
        this.supportedBies = admins;
        return this;
    }

    public MobileUser addSupportedBy(Admin admin) {
        this.supportedBies.add(admin);
        admin.getWhoHelpeds().add(this);
        return this;
    }

    public MobileUser removeSupportedBy(Admin admin) {
        this.supportedBies.remove(admin);
        admin.getWhoHelpeds().remove(this);
        return this;
    }

    public void setSupportedBies(Set<Admin> admins) {
        this.supportedBies = admins;
    }

    public Set<OrderTariffPlan> getTariffPlanOrders() {
        return tariffPlanOrders;
    }

    public MobileUser tariffPlanOrders(Set<OrderTariffPlan> orderTariffPlans) {
        this.tariffPlanOrders = orderTariffPlans;
        return this;
    }

    public MobileUser addTariffPlanOrder(OrderTariffPlan orderTariffPlan) {
        this.tariffPlanOrders.add(orderTariffPlan);
        orderTariffPlan.getWhoOrdereds().add(this);
        return this;
    }

    public MobileUser removeTariffPlanOrder(OrderTariffPlan orderTariffPlan) {
        this.tariffPlanOrders.remove(orderTariffPlan);
        orderTariffPlan.getWhoOrdereds().remove(this);
        return this;
    }

    public void setTariffPlanOrders(Set<OrderTariffPlan> orderTariffPlans) {
        this.tariffPlanOrders = orderTariffPlans;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobileUser)) {
            return false;
        }
        return id != null && id.equals(((MobileUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MobileUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
