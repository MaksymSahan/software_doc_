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

import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.domain.*; // for static metamodels
import com.sahan.t_mobile.repository.TariffPlanRepository;
import com.sahan.t_mobile.service.dto.TariffPlanCriteria;

/**
 * Service for executing complex queries for {@link TariffPlan} entities in the database.
 * The main input is a {@link TariffPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TariffPlan} or a {@link Page} of {@link TariffPlan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TariffPlanQueryService extends QueryService<TariffPlan> {

    private final Logger log = LoggerFactory.getLogger(TariffPlanQueryService.class);

    private final TariffPlanRepository tariffPlanRepository;

    public TariffPlanQueryService(TariffPlanRepository tariffPlanRepository) {
        this.tariffPlanRepository = tariffPlanRepository;
    }

    /**
     * Return a {@link List} of {@link TariffPlan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TariffPlan> findByCriteria(TariffPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TariffPlan> specification = createSpecification(criteria);
        return tariffPlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TariffPlan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TariffPlan> findByCriteria(TariffPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TariffPlan> specification = createSpecification(criteria);
        return tariffPlanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TariffPlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TariffPlan> specification = createSpecification(criteria);
        return tariffPlanRepository.count(specification);
    }

    /**
     * Function to convert {@link TariffPlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TariffPlan> createSpecification(TariffPlanCriteria criteria) {
        Specification<TariffPlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TariffPlan_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TariffPlan_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TariffPlan_.description));
            }
            if (criteria.getInternet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternet(), TariffPlan_.internet));
            }
            if (criteria.getCalls() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalls(), TariffPlan_.calls));
            }
            if (criteria.getSms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSms(), TariffPlan_.sms));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), TariffPlan_.price));
            }
            if (criteria.getUpdatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatedById(),
                    root -> root.join(TariffPlan_.updatedBy, JoinType.LEFT).get(Admin_.id)));
            }
            if (criteria.getUsersId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsersId(),
                    root -> root.join(TariffPlan_.users, JoinType.LEFT).get(MobileUser_.id)));
            }
        }
        return specification;
    }
}
