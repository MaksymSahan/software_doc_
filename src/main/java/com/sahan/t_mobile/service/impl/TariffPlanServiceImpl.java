package com.sahan.t_mobile.service.impl;

import com.sahan.t_mobile.service.TariffPlanService;
import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.repository.TariffPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TariffPlan}.
 */
@Service
@Transactional
public class TariffPlanServiceImpl implements TariffPlanService {

    private final Logger log = LoggerFactory.getLogger(TariffPlanServiceImpl.class);

    private final TariffPlanRepository tariffPlanRepository;

    public TariffPlanServiceImpl(TariffPlanRepository tariffPlanRepository) {
        this.tariffPlanRepository = tariffPlanRepository;
    }

    /**
     * Save a tariffPlan.
     *
     * @param tariffPlan the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TariffPlan save(TariffPlan tariffPlan) {
        log.debug("Request to save TariffPlan : {}", tariffPlan);
        return tariffPlanRepository.save(tariffPlan);
    }

    /**
     * Get all the tariffPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TariffPlan> findAll(Pageable pageable) {
        log.debug("Request to get all TariffPlans");
        return tariffPlanRepository.findAll(pageable);
    }

    /**
     * Get one tariffPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TariffPlan> findOne(Long id) {
        log.debug("Request to get TariffPlan : {}", id);
        return tariffPlanRepository.findById(id);
    }

    /**
     * Delete the tariffPlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TariffPlan : {}", id);
        tariffPlanRepository.deleteById(id);
    }
}
