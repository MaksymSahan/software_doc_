package com.sahan.t_mobile.repository;

import com.sahan.t_mobile.domain.TariffPlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TariffPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TariffPlanRepository extends JpaRepository<TariffPlan, Long>, JpaSpecificationExecutor<TariffPlan> {
}
