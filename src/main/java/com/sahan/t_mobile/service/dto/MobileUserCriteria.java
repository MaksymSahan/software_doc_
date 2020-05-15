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
 * Criteria class for the {@link com.sahan.t_mobile.domain.MobileUser} entity. This class is used
 * in {@link com.sahan.t_mobile.web.rest.MobileUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mobile-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MobileUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter surname;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter address;

    private LongFilter paymentsId;

    private LongFilter tariffsId;

    private LongFilter supportedById;

    private LongFilter tariffPlanOrderId;

    public MobileUserCriteria() {
    }

    public MobileUserCriteria(MobileUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.surname = other.surname == null ? null : other.surname.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.paymentsId = other.paymentsId == null ? null : other.paymentsId.copy();
        this.tariffsId = other.tariffsId == null ? null : other.tariffsId.copy();
        this.supportedById = other.supportedById == null ? null : other.supportedById.copy();
        this.tariffPlanOrderId = other.tariffPlanOrderId == null ? null : other.tariffPlanOrderId.copy();
    }

    @Override
    public MobileUserCriteria copy() {
        return new MobileUserCriteria(this);
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

    public StringFilter getSurname() {
        return surname;
    }

    public void setSurname(StringFilter surname) {
        this.surname = surname;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public LongFilter getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(LongFilter paymentsId) {
        this.paymentsId = paymentsId;
    }

    public LongFilter getTariffsId() {
        return tariffsId;
    }

    public void setTariffsId(LongFilter tariffsId) {
        this.tariffsId = tariffsId;
    }

    public LongFilter getSupportedById() {
        return supportedById;
    }

    public void setSupportedById(LongFilter supportedById) {
        this.supportedById = supportedById;
    }

    public LongFilter getTariffPlanOrderId() {
        return tariffPlanOrderId;
    }

    public void setTariffPlanOrderId(LongFilter tariffPlanOrderId) {
        this.tariffPlanOrderId = tariffPlanOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MobileUserCriteria that = (MobileUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(surname, that.surname) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(paymentsId, that.paymentsId) &&
            Objects.equals(tariffsId, that.tariffsId) &&
            Objects.equals(supportedById, that.supportedById) &&
            Objects.equals(tariffPlanOrderId, that.tariffPlanOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        surname,
        email,
        phone,
        address,
        paymentsId,
        tariffsId,
        supportedById,
        tariffPlanOrderId
        );
    }

    @Override
    public String toString() {
        return "MobileUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (surname != null ? "surname=" + surname + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (paymentsId != null ? "paymentsId=" + paymentsId + ", " : "") +
                (tariffsId != null ? "tariffsId=" + tariffsId + ", " : "") +
                (supportedById != null ? "supportedById=" + supportedById + ", " : "") +
                (tariffPlanOrderId != null ? "tariffPlanOrderId=" + tariffPlanOrderId + ", " : "") +
            "}";
    }

}
