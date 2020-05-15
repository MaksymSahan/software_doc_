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
 * Criteria class for the {@link com.sahan.t_mobile.domain.Admin} entity. This class is used
 * in {@link com.sahan.t_mobile.web.rest.AdminResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /admins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdminCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter updatedTariffsId;

    private LongFilter whoHelpedId;

    public AdminCriteria() {
    }

    public AdminCriteria(AdminCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.updatedTariffsId = other.updatedTariffsId == null ? null : other.updatedTariffsId.copy();
        this.whoHelpedId = other.whoHelpedId == null ? null : other.whoHelpedId.copy();
    }

    @Override
    public AdminCriteria copy() {
        return new AdminCriteria(this);
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

    public LongFilter getUpdatedTariffsId() {
        return updatedTariffsId;
    }

    public void setUpdatedTariffsId(LongFilter updatedTariffsId) {
        this.updatedTariffsId = updatedTariffsId;
    }

    public LongFilter getWhoHelpedId() {
        return whoHelpedId;
    }

    public void setWhoHelpedId(LongFilter whoHelpedId) {
        this.whoHelpedId = whoHelpedId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdminCriteria that = (AdminCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(updatedTariffsId, that.updatedTariffsId) &&
            Objects.equals(whoHelpedId, that.whoHelpedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        updatedTariffsId,
        whoHelpedId
        );
    }

    @Override
    public String toString() {
        return "AdminCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (updatedTariffsId != null ? "updatedTariffsId=" + updatedTariffsId + ", " : "") +
                (whoHelpedId != null ? "whoHelpedId=" + whoHelpedId + ", " : "") +
            "}";
    }

}
