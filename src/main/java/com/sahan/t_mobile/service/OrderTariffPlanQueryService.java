package com.sahan.t_mobile.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sahan.t_mobile.domain.OrderTariffPlan;
import com.sahan.t_mobile.domain.*; // for static metamodels
import com.sahan.t_mobile.repository.OrderTariffPlanRepository;
import com.sahan.t_mobile.service.dto.OrderTariffPlanCriteria;

/**
 * Service for executing complex queries for {@link OrderTariffPlan} entities in the database.
 * The main input is a {@link OrderTariffPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderTariffPlan} or a {@link Page} of {@link OrderTariffPlan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderTariffPlanQueryService extends QueryService<OrderTariffPlan> {

    private final Logger log = LoggerFactory.getLogger(OrderTariffPlanQueryService.class);

    private final OrderTariffPlanRepository orderTariffPlanRepository;

    public OrderTariffPlanQueryService(OrderTariffPlanRepository orderTariffPlanRepository) {
        this.orderTariffPlanRepository = orderTariffPlanRepository;
    }

    /**
     * Return a {@link List} of {@link OrderTariffPlan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderTariffPlan> findByCriteria(OrderTariffPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderTariffPlan> specification = createSpecification(criteria);
        return orderTariffPlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OrderTariffPlan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderTariffPlan> findByCriteria(OrderTariffPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderTariffPlan> specification = createSpecification(criteria);
        return orderTariffPlanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderTariffPlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderTariffPlan> specification = createSpecification(criteria);
        return orderTariffPlanRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderTariffPlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderTariffPlan> createSpecification(OrderTariffPlanCriteria criteria) {
        Specification<OrderTariffPlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderTariffPlan_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrderTariffPlan_.name));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), OrderTariffPlan_.price));
            }
            if (criteria.getWhoOrderedId() != null) {
                specification = specification.and(buildSpecification(criteria.getWhoOrderedId(),
                    root -> root.join(OrderTariffPlan_.whoOrdereds, JoinType.LEFT).get(MobileUser_.id)));
            }
        }
        return specification;
    }
}
