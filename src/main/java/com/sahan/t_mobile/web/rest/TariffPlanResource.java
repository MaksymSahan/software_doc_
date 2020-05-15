package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.service.TariffPlanService;
import com.sahan.t_mobile.web.rest.errors.BadRequestAlertException;
import com.sahan.t_mobile.service.dto.TariffPlanCriteria;
import com.sahan.t_mobile.service.TariffPlanQueryService;

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
 * REST controller for managing {@link com.sahan.t_mobile.domain.TariffPlan}.
 */
@RestController
@RequestMapping("/api")
public class TariffPlanResource {

    private final Logger log = LoggerFactory.getLogger(TariffPlanResource.class);

    private static final String ENTITY_NAME = "tariffPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TariffPlanService tariffPlanService;

    private final TariffPlanQueryService tariffPlanQueryService;

    public TariffPlanResource(TariffPlanService tariffPlanService, TariffPlanQueryService tariffPlanQueryService) {
        this.tariffPlanService = tariffPlanService;
        this.tariffPlanQueryService = tariffPlanQueryService;
    }

    /**
     * {@code POST  /tariff-plans} : Create a new tariffPlan.
     *
     * @param tariffPlan the tariffPlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tariffPlan, or with status {@code 400 (Bad Request)} if the tariffPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tariff-plans")
    public ResponseEntity<TariffPlan> createTariffPlan(@Valid @RequestBody TariffPlan tariffPlan) throws URISyntaxException {
        log.debug("REST request to save TariffPlan : {}", tariffPlan);
        if (tariffPlan.getId() != null) {
            throw new BadRequestAlertException("A new tariffPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TariffPlan result = tariffPlanService.save(tariffPlan);
        return ResponseEntity.created(new URI("/api/tariff-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tariff-plans} : Updates an existing tariffPlan.
     *
     * @param tariffPlan the tariffPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tariffPlan,
     * or with status {@code 400 (Bad Request)} if the tariffPlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tariffPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tariff-plans")
    public ResponseEntity<TariffPlan> updateTariffPlan(@Valid @RequestBody TariffPlan tariffPlan) throws URISyntaxException {
        log.debug("REST request to update TariffPlan : {}", tariffPlan);
        if (tariffPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TariffPlan result = tariffPlanService.save(tariffPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tariffPlan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tariff-plans} : get all the tariffPlans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tariffPlans in body.
     */
    @GetMapping("/tariff-plans")
    public ResponseEntity<List<TariffPlan>> getAllTariffPlans(TariffPlanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TariffPlans by criteria: {}", criteria);
        Page<TariffPlan> page = tariffPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tariff-plans/count} : count all the tariffPlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tariff-plans/count")
    public ResponseEntity<Long> countTariffPlans(TariffPlanCriteria criteria) {
        log.debug("REST request to count TariffPlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(tariffPlanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tariff-plans/:id} : get the "id" tariffPlan.
     *
     * @param id the id of the tariffPlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tariffPlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tariff-plans/{id}")
    public ResponseEntity<TariffPlan> getTariffPlan(@PathVariable Long id) {
        log.debug("REST request to get TariffPlan : {}", id);
        Optional<TariffPlan> tariffPlan = tariffPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tariffPlan);
    }

    /**
     * {@code DELETE  /tariff-plans/:id} : delete the "id" tariffPlan.
     *
     * @param id the id of the tariffPlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tariff-plans/{id}")
    public ResponseEntity<Void> deleteTariffPlan(@PathVariable Long id) {
        log.debug("REST request to delete TariffPlan : {}", id);
        tariffPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
