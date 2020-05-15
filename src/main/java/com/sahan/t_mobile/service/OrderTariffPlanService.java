package com.sahan.t_mobile.service;

import com.sahan.t_mobile.domain.OrderTariffPlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link OrderTariffPlan}.
 */
public interface OrderTariffPlanService {

    /**
     * Save a orderTariffPlan.
     *
     * @param orderTariffPlan the entity to save.
     * @return the persisted entity.
     */
    OrderTariffPlan save(OrderTariffPlan orderTariffPlan);

    /**
     * Get all the orderTariffPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderTariffPlan> findAll(Pageable pageable);

    /**
     * Get all the orderTariffPlans with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<OrderTariffPlan> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" orderTariffPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderTariffPlan> findOne(Long id);

    /**
     * Delete the "id" orderTariffPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
