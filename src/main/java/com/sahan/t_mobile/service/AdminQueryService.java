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

import com.sahan.t_mobile.domain.Admin;
import com.sahan.t_mobile.domain.*; // for static metamodels
import com.sahan.t_mobile.repository.AdminRepository;
import com.sahan.t_mobile.service.dto.AdminCriteria;

/**
 * Service for executing complex queries for {@link Admin} entities in the database.
 * The main input is a {@link AdminCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Admin} or a {@link Page} of {@link Admin} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdminQueryService extends QueryService<Admin> {

    private final Logger log = LoggerFactory.getLogger(AdminQueryService.class);

    private final AdminRepository adminRepository;

    public AdminQueryService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Return a {@link List} of {@link Admin} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Admin> findByCriteria(AdminCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Admin> specification = createSpecification(criteria);
        return adminRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Admin} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Admin> findByCriteria(AdminCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Admin> specification = createSpecification(criteria);
        return adminRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdminCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Admin> specification = createSpecification(criteria);
        return adminRepository.count(specification);
    }

    /**
     * Function to convert {@link AdminCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Admin> createSpecification(AdminCriteria criteria) {
        Specification<Admin> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Admin_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Admin_.name));
            }
            if (criteria.getUpdatedTariffsId() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatedTariffsId(),
                    root -> root.join(Admin_.updatedTariffs, JoinType.LEFT).get(TariffPlan_.id)));
            }
            if (criteria.getWhoHelpedId() != null) {
                specification = specification.and(buildSpecification(criteria.getWhoHelpedId(),
                    root -> root.join(Admin_.whoHelpeds, JoinType.LEFT).get(MobileUser_.id)));
            }
        }
        return specification;
    }
}
