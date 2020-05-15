package com.sahan.t_mobile.service.impl;

import com.sahan.t_mobile.service.OrderTariffPlanService;
import com.sahan.t_mobile.domain.OrderTariffPlan;
import com.sahan.t_mobile.repository.OrderTariffPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderTariffPlan}.
 */
@Service
@Transactional
public class OrderTariffPlanServiceImpl implements OrderTariffPlanService {

    private final Logger log = LoggerFactory.getLogger(OrderTariffPlanServiceImpl.class);

    private final OrderTariffPlanRepository orderTariffPlanRepository;

    public OrderTariffPlanServiceImpl(OrderTariffPlanRepository orderTariffPlanRepository) {
        this.orderTariffPlanRepository = orderTariffPlanRepository;
    }

    /**
     * Save a orderTariffPlan.
     *
     * @param orderTariffPlan the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderTariffPlan save(OrderTariffPlan orderTariffPlan) {
        log.debug("Request to save OrderTariffPlan : {}", orderTariffPlan);
        return orderTariffPlanRepository.save(orderTariffPlan);
    }

    /**
     * Get all the orderTariffPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderTariffPlan> findAll(Pageable pageable) {
        log.debug("Request to get all OrderTariffPlans");
        return orderTariffPlanRepository.findAll(pageable);
    }

    /**
     * Get all the orderTariffPlans with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OrderTariffPlan> findAllWithEagerRelationships(Pageable pageable) {
        return orderTariffPlanRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one orderTariffPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderTariffPlan> findOne(Long id) {
        log.debug("Request to get OrderTariffPlan : {}", id);
        return orderTariffPlanRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the orderTariffPlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderTariffPlan : {}", id);
        orderTariffPlanRepository.deleteById(id);
    }
}
