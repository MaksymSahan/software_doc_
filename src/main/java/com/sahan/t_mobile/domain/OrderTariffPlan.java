package com.sahan.t_mobile.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrderTariffPlan.
 */
@Entity
@Table(name = "order_tariff_plan")
public class OrderTariffPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @ManyToMany
    @JoinTable(name = "order_tariff_plan_who_ordered",
               joinColumns = @JoinColumn(name = "order_tariff_plan_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "who_ordered_id", referencedColumnName = "id"))
    private Set<MobileUser> whoOrdereds = new HashSet<>();

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

    public OrderTariffPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public OrderTariffPlan price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<MobileUser> getWhoOrdereds() {
        return whoOrdereds;
    }

    public OrderTariffPlan whoOrdereds(Set<MobileUser> mobileUsers) {
        this.whoOrdereds = mobileUsers;
        return this;
    }

    public OrderTariffPlan addWhoOrdered(MobileUser mobileUser) {
        this.whoOrdereds.add(mobileUser);
        mobileUser.getTariffPlanOrders().add(this);
        return this;
    }

    public OrderTariffPlan removeWhoOrdered(MobileUser mobileUser) {
        this.whoOrdereds.remove(mobileUser);
        mobileUser.getTariffPlanOrders().remove(this);
        return this;
    }

    public void setWhoOrdereds(Set<MobileUser> mobileUsers) {
        this.whoOrdereds = mobileUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTariffPlan)) {
            return false;
        }
        return id != null && id.equals(((OrderTariffPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderTariffPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
