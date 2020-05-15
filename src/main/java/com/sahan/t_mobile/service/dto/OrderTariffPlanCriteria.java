package com.sahan.t_mobile.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sahan.t_mobile.domain.OrderTariffPlan} entity. This class is used
 * in {@link com.sahan.t_mobile.web.rest.OrderTariffPlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-tariff-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderTariffPlanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private DoubleFilter price;

    private LongFilter whoOrderedId;

    public OrderTariffPlanCriteria() {
    }

    public OrderTariffPlanCriteria(OrderTariffPlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.whoOrderedId = other.whoOrderedId == null ? null : other.whoOrderedId.copy();
    }

    @Override
    public OrderTariffPlanCriteria copy() {
        return new OrderTariffPlanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public LongFilter getWhoOrderedId() {
        return whoOrderedId;
    }

    public void setWhoOrderedId(LongFilter whoOrderedId) {
        this.whoOrderedId = whoOrderedId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderTariffPlanCriteria that = (OrderTariffPlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(price, that.price) &&
            Objects.equals(whoOrderedId, that.whoOrderedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        price,
        whoOrderedId
        );
    }

    @Override
    public String toString() {
        return "OrderTariffPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (whoOrderedId != null ? "whoOrderedId=" + whoOrderedId + ", " : "") +
            "}";
    }

}
