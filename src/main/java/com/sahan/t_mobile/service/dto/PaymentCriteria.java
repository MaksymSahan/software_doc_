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
 * Criteria class for the {@link com.sahan.t_mobile.domain.Payment} entity. This class is used
 * in {@link com.sahan.t_mobile.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private StringFilter transactionProvider;

    private LongFilter byWhoId;

    public PaymentCriteria() {
    }

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.transactionProvider = other.transactionProvider == null ? null : other.transactionProvider.copy();
        this.byWhoId = other.byWhoId == null ? null : other.byWhoId.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getTransactionProvider() {
        return transactionProvider;
    }

    public void setTransactionProvider(StringFilter transactionProvider) {
        this.transactionProvider = transactionProvider;
    }

    public LongFilter getByWhoId() {
        return byWhoId;
    }

    public void setByWhoId(LongFilter byWhoId) {
        this.byWhoId = byWhoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(transactionProvider, that.transactionProvider) &&
            Objects.equals(byWhoId, that.byWhoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        transactionProvider,
        byWhoId
        );
    }

    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (transactionProvider != null ? "transactionProvider=" + transactionProvider + ", " : "") +
                (byWhoId != null ? "byWhoId=" + byWhoId + ", " : "") +
            "}";
    }

}
