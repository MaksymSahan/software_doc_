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
 * Criteria class for the {@link com.sahan.t_mobile.domain.TariffPlan} entity. This class is used
 * in {@link com.sahan.t_mobile.web.rest.TariffPlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tariff-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TariffPlanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter internet;

    private StringFilter calls;

    private StringFilter sms;

    private DoubleFilter price;

    private LongFilter updatedById;

    private LongFilter usersId;

    public TariffPlanCriteria() {
    }

    public TariffPlanCriteria(TariffPlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.internet = other.internet == null ? null : other.internet.copy();
        this.calls = other.calls == null ? null : other.calls.copy();
        this.sms = other.sms == null ? null : other.sms.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.updatedById = other.updatedById == null ? null : other.updatedById.copy();
        this.usersId = other.usersId == null ? null : other.usersId.copy();
    }

    @Override
    public TariffPlanCriteria copy() {
        return new TariffPlanCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getInternet() {
        return internet;
    }

    public void setInternet(StringFilter internet) {
        this.internet = internet;
    }

    public StringFilter getCalls() {
        return calls;
    }

    public void setCalls(StringFilter calls) {
        this.calls = calls;
    }

    public StringFilter getSms() {
        return sms;
    }

    public void setSms(StringFilter sms) {
        this.sms = sms;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public LongFilter getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(LongFilter updatedById) {
        this.updatedById = updatedById;
    }

    public LongFilter getUsersId() {
        return usersId;
    }

    public void setUsersId(LongFilter usersId) {
        this.usersId = usersId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TariffPlanCriteria that = (TariffPlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(internet, that.internet) &&
            Objects.equals(calls, that.calls) &&
            Objects.equals(sms, that.sms) &&
            Objects.equals(price, that.price) &&
            Objects.equals(updatedById, that.updatedById) &&
            Objects.equals(usersId, that.usersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        internet,
        calls,
        sms,
        price,
        updatedById,
        usersId
        );
    }

    @Override
    public String toString() {
        return "TariffPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (internet != null ? "internet=" + internet + ", " : "") +
                (calls != null ? "calls=" + calls + ", " : "") +
                (sms != null ? "sms=" + sms + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (updatedById != null ? "updatedById=" + updatedById + ", " : "") +
                (usersId != null ? "usersId=" + usersId + ", " : "") +
            "}";
    }

}
