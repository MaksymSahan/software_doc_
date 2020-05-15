package com.sahan.t_mobile.service;

import com.sahan.t_mobile.domain.TariffPlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TariffPlan}.
 */
public interface TariffPlanService {

    /**
     * Save a tariffPlan.
     *
     * @param tariffPlan the entity to save.
     * @return the persisted entity.
     */
    TariffPlan save(TariffPlan tariffPlan);

    /**
     * Get all the tariffPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TariffPlan> findAll(Pageable pageable);

    /**
     * Get the "id" tariffPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TariffPlan> findOne(Long id);

    /**
     * Delete the "id" tariffPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
