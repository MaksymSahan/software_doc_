package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.domain.OrderTariffPlan;
import com.sahan.t_mobile.service.OrderTariffPlanService;
import com.sahan.t_mobile.web.rest.errors.BadRequestAlertException;
import com.sahan.t_mobile.service.dto.OrderTariffPlanCriteria;
import com.sahan.t_mobile.service.OrderTariffPlanQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sahan.t_mobile.domain.OrderTariffPlan}.
 */
@RestController
@RequestMapping("/api")
public class OrderTariffPlanResource {

    private final Logger log = LoggerFactory.getLogger(OrderTariffPlanResource.class);

    private static final String ENTITY_NAME = "orderTariffPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTariffPlanService orderTariffPlanService;

    private final OrderTariffPlanQueryService orderTariffPlanQueryService;

    public OrderTariffPlanResource(OrderTariffPlanService orderTariffPlanService, OrderTariffPlanQueryService orderTariffPlanQueryService) {
        this.orderTariffPlanService = orderTariffPlanService;
        this.orderTariffPlanQueryService = orderTariffPlanQueryService;
    }

    /**
     * {@code POST  /order-tariff-plans} : Create a new orderTariffPlan.
     *
     * @param orderTariffPlan the orderTariffPlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTariffPlan, or with status {@code 400 (Bad Request)} if the orderTariffPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-tariff-plans")
    public ResponseEntity<OrderTariffPlan> createOrderTariffPlan(@Valid @RequestBody OrderTariffPlan orderTariffPlan) throws URISyntaxException {
        log.debug("REST request to save OrderTariffPlan : {}", orderTariffPlan);
        if (orderTariffPlan.getId() != null) {
            throw new BadRequestAlertException("A new orderTariffPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTariffPlan result = orderTariffPlanService.save(orderTariffPlan);
        return ResponseEntity.created(new URI("/api/order-tariff-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-tariff-plans} : Updates an existing orderTariffPlan.
     *
     * @param orderTariffPlan the orderTariffPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTariffPlan,
     * or with status {@code 400 (Bad Request)} if the orderTariffPlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTariffPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-tariff-plans")
    public ResponseEntity<OrderTariffPlan> updateOrderTariffPlan(@Valid @RequestBody OrderTariffPlan orderTariffPlan) throws URISyntaxException {
        log.debug("REST request to update OrderTariffPlan : {}", orderTariffPlan);
        if (orderTariffPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTariffPlan result = orderTariffPlanService.save(orderTariffPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderTariffPlan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-tariff-plans} : get all the orderTariffPlans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTariffPlans in body.
     */
    @GetMapping("/order-tariff-plans")
    public ResponseEntity<List<OrderTariffPlan>> getAllOrderTariffPlans(OrderTariffPlanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderTariffPlans by criteria: {}", criteria);
        Page<OrderTariffPlan> page = orderTariffPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-tariff-plans/count} : count all the orderTariffPlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-tariff-plans/count")
    public ResponseEntity<Long> countOrderTariffPlans(OrderTariffPlanCriteria criteria) {
        log.debug("REST request to count OrderTariffPlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderTariffPlanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-tariff-plans/:id} : get the "id" orderTariffPlan.
     *
     * @param id the id of the orderTariffPlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTariffPlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-tariff-plans/{id}")
    public ResponseEntity<OrderTariffPlan> getOrderTariffPlan(@PathVariable Long id) {
        log.debug("REST request to get OrderTariffPlan : {}", id);
        Optional<OrderTariffPlan> orderTariffPlan = orderTariffPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTariffPlan);
    }

    /**
     * {@code DELETE  /order-tariff-plans/:id} : delete the "id" orderTariffPlan.
     *
     * @param id the id of the orderTariffPlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-tariff-plans/{id}")
    public ResponseEntity<Void> deleteOrderTariffPlan(@PathVariable Long id) {
        log.debug("REST request to delete OrderTariffPlan : {}", id);
        orderTariffPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
