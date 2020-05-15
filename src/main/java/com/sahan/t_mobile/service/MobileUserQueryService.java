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

import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.domain.*; // for static metamodels
import com.sahan.t_mobile.repository.MobileUserRepository;
import com.sahan.t_mobile.service.dto.MobileUserCriteria;

/**
 * Service for executing complex queries for {@link MobileUser} entities in the database.
 * The main input is a {@link MobileUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MobileUser} or a {@link Page} of {@link MobileUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MobileUserQueryService extends QueryService<MobileUser> {

    private final Logger log = LoggerFactory.getLogger(MobileUserQueryService.class);

    private final MobileUserRepository mobileUserRepository;

    public MobileUserQueryService(MobileUserRepository mobileUserRepository) {
        this.mobileUserRepository = mobileUserRepository;
    }

    /**
     * Return a {@link List} of {@link MobileUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MobileUser> findByCriteria(MobileUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MobileUser> specification = createSpecification(criteria);
        return mobileUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MobileUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MobileUser> findByCriteria(MobileUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MobileUser> specification = createSpecification(criteria);
        return mobileUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MobileUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MobileUser> specification = createSpecification(criteria);
        return mobileUserRepository.count(specification);
    }

    /**
     * Function to convert {@link MobileUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MobileUser> createSpecification(MobileUserCriteria criteria) {
        Specification<MobileUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MobileUser_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MobileUser_.name));
            }
            if (criteria.getSurname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurname(), MobileUser_.surname));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), MobileUser_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), MobileUser_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), MobileUser_.address));
            }
            if (criteria.getPaymentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsId(),
                    root -> root.join(MobileUser_.payments, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getTariffsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTariffsId(),
                    root -> root.join(MobileUser_.tariffs, JoinType.LEFT).get(TariffPlan_.id)));
            }
            if (criteria.getSupportedById() != null) {
                specification = specification.and(buildSpecification(criteria.getSupportedById(),
                    root -> root.join(MobileUser_.supportedBies, JoinType.LEFT).get(Admin_.id)));
            }
            if (criteria.getTariffPlanOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getTariffPlanOrderId(),
                    root -> root.join(MobileUser_.tariffPlanOrders, JoinType.LEFT).get(OrderTariffPlan_.id)));
            }
        }
        return specification;
    }
}
