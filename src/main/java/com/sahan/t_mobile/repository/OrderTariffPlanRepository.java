package com.sahan.t_mobile.repository;

import com.sahan.t_mobile.domain.OrderTariffPlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the OrderTariffPlan entity.
 */
@Repository
public interface OrderTariffPlanRepository extends JpaRepository<OrderTariffPlan, Long>, JpaSpecificationExecutor<OrderTariffPlan> {

    @Query(value = "select distinct orderTariffPlan from OrderTariffPlan orderTariffPlan left join fetch orderTariffPlan.whoOrdereds",
        countQuery = "select count(distinct orderTariffPlan) from OrderTariffPlan orderTariffPlan")
    Page<OrderTariffPlan> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct orderTariffPlan from OrderTariffPlan orderTariffPlan left join fetch orderTariffPlan.whoOrdereds")
    List<OrderTariffPlan> findAllWithEagerRelationships();

    @Query("select orderTariffPlan from OrderTariffPlan orderTariffPlan left join fetch orderTariffPlan.whoOrdereds where orderTariffPlan.id =:id")
    Optional<OrderTariffPlan> findOneWithEagerRelationships(@Param("id") Long id);
}
