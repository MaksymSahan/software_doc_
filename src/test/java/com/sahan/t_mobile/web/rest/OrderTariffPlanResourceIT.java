package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.TMobileApp;
import com.sahan.t_mobile.domain.OrderTariffPlan;
import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.repository.OrderTariffPlanRepository;
import com.sahan.t_mobile.service.OrderTariffPlanService;
import com.sahan.t_mobile.service.dto.OrderTariffPlanCriteria;
import com.sahan.t_mobile.service.OrderTariffPlanQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderTariffPlanResource} REST controller.
 */
@SpringBootTest(classes = TMobileApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderTariffPlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    @Autowired
    private OrderTariffPlanRepository orderTariffPlanRepository;

    @Mock
    private OrderTariffPlanRepository orderTariffPlanRepositoryMock;

    @Mock
    private OrderTariffPlanService orderTariffPlanServiceMock;

    @Autowired
    private OrderTariffPlanService orderTariffPlanService;

    @Autowired
    private OrderTariffPlanQueryService orderTariffPlanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTariffPlanMockMvc;

    private OrderTariffPlan orderTariffPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTariffPlan createEntity(EntityManager em) {
        OrderTariffPlan orderTariffPlan = new OrderTariffPlan()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return orderTariffPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTariffPlan createUpdatedEntity(EntityManager em) {
        OrderTariffPlan orderTariffPlan = new OrderTariffPlan()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        return orderTariffPlan;
    }

    @BeforeEach
    public void initTest() {
        orderTariffPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTariffPlan() throws Exception {
        int databaseSizeBeforeCreate = orderTariffPlanRepository.findAll().size();

        // Create the OrderTariffPlan
        restOrderTariffPlanMockMvc.perform(post("/api/order-tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTariffPlan)))
            .andExpect(status().isCreated());

        // Validate the OrderTariffPlan in the database
        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTariffPlan testOrderTariffPlan = orderTariffPlanList.get(orderTariffPlanList.size() - 1);
        assertThat(testOrderTariffPlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderTariffPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createOrderTariffPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTariffPlanRepository.findAll().size();

        // Create the OrderTariffPlan with an existing ID
        orderTariffPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTariffPlanMockMvc.perform(post("/api/order-tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTariffPlan)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTariffPlan in the database
        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTariffPlanRepository.findAll().size();
        // set the field null
        orderTariffPlan.setName(null);

        // Create the OrderTariffPlan, which fails.

        restOrderTariffPlanMockMvc.perform(post("/api/order-tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTariffPlan)))
            .andExpect(status().isBadRequest());

        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlans() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTariffPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOrderTariffPlansWithEagerRelationshipsIsEnabled() throws Exception {
        when(orderTariffPlanServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans?eagerload=true"))
            .andExpect(status().isOk());

        verify(orderTariffPlanServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOrderTariffPlansWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(orderTariffPlanServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans?eagerload=true"))
            .andExpect(status().isOk());

        verify(orderTariffPlanServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrderTariffPlan() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get the orderTariffPlan
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans/{id}", orderTariffPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTariffPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }


    @Test
    @Transactional
    public void getOrderTariffPlansByIdFiltering() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        Long id = orderTariffPlan.getId();

        defaultOrderTariffPlanShouldBeFound("id.equals=" + id);
        defaultOrderTariffPlanShouldNotBeFound("id.notEquals=" + id);

        defaultOrderTariffPlanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderTariffPlanShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderTariffPlanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderTariffPlanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderTariffPlansByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name equals to DEFAULT_NAME
        defaultOrderTariffPlanShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the orderTariffPlanList where name equals to UPDATED_NAME
        defaultOrderTariffPlanShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name not equals to DEFAULT_NAME
        defaultOrderTariffPlanShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the orderTariffPlanList where name not equals to UPDATED_NAME
        defaultOrderTariffPlanShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByNameIsInShouldWork() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrderTariffPlanShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the orderTariffPlanList where name equals to UPDATED_NAME
        defaultOrderTariffPlanShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name is not null
        defaultOrderTariffPlanShouldBeFound("name.specified=true");

        // Get all the orderTariffPlanList where name is null
        defaultOrderTariffPlanShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderTariffPlansByNameContainsSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name contains DEFAULT_NAME
        defaultOrderTariffPlanShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the orderTariffPlanList where name contains UPDATED_NAME
        defaultOrderTariffPlanShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByNameNotContainsSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where name does not contain DEFAULT_NAME
        defaultOrderTariffPlanShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the orderTariffPlanList where name does not contain UPDATED_NAME
        defaultOrderTariffPlanShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price equals to DEFAULT_PRICE
        defaultOrderTariffPlanShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price equals to UPDATED_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price not equals to DEFAULT_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price not equals to UPDATED_PRICE
        defaultOrderTariffPlanShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultOrderTariffPlanShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the orderTariffPlanList where price equals to UPDATED_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price is not null
        defaultOrderTariffPlanShouldBeFound("price.specified=true");

        // Get all the orderTariffPlanList where price is null
        defaultOrderTariffPlanShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price is greater than or equal to DEFAULT_PRICE
        defaultOrderTariffPlanShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price is greater than or equal to UPDATED_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price is less than or equal to DEFAULT_PRICE
        defaultOrderTariffPlanShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price is less than or equal to SMALLER_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price is less than DEFAULT_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price is less than UPDATED_PRICE
        defaultOrderTariffPlanShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderTariffPlansByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);

        // Get all the orderTariffPlanList where price is greater than DEFAULT_PRICE
        defaultOrderTariffPlanShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the orderTariffPlanList where price is greater than SMALLER_PRICE
        defaultOrderTariffPlanShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderTariffPlansByWhoOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);
        MobileUser whoOrdered = MobileUserResourceIT.createEntity(em);
        em.persist(whoOrdered);
        em.flush();
        orderTariffPlan.addWhoOrdered(whoOrdered);
        orderTariffPlanRepository.saveAndFlush(orderTariffPlan);
        Long whoOrderedId = whoOrdered.getId();

        // Get all the orderTariffPlanList where whoOrdered equals to whoOrderedId
        defaultOrderTariffPlanShouldBeFound("whoOrderedId.equals=" + whoOrderedId);

        // Get all the orderTariffPlanList where whoOrdered equals to whoOrderedId + 1
        defaultOrderTariffPlanShouldNotBeFound("whoOrderedId.equals=" + (whoOrderedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderTariffPlanShouldBeFound(String filter) throws Exception {
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTariffPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderTariffPlanShouldNotBeFound(String filter) throws Exception {
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrderTariffPlan() throws Exception {
        // Get the orderTariffPlan
        restOrderTariffPlanMockMvc.perform(get("/api/order-tariff-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTariffPlan() throws Exception {
        // Initialize the database
        orderTariffPlanService.save(orderTariffPlan);

        int databaseSizeBeforeUpdate = orderTariffPlanRepository.findAll().size();

        // Update the orderTariffPlan
        OrderTariffPlan updatedOrderTariffPlan = orderTariffPlanRepository.findById(orderTariffPlan.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTariffPlan are not directly saved in db
        em.detach(updatedOrderTariffPlan);
        updatedOrderTariffPlan
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);

        restOrderTariffPlanMockMvc.perform(put("/api/order-tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTariffPlan)))
            .andExpect(status().isOk());

        // Validate the OrderTariffPlan in the database
        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeUpdate);
        OrderTariffPlan testOrderTariffPlan = orderTariffPlanList.get(orderTariffPlanList.size() - 1);
        assertThat(testOrderTariffPlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderTariffPlan.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTariffPlan() throws Exception {
        int databaseSizeBeforeUpdate = orderTariffPlanRepository.findAll().size();

        // Create the OrderTariffPlan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTariffPlanMockMvc.perform(put("/api/order-tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTariffPlan)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTariffPlan in the database
        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTariffPlan() throws Exception {
        // Initialize the database
        orderTariffPlanService.save(orderTariffPlan);

        int databaseSizeBeforeDelete = orderTariffPlanRepository.findAll().size();

        // Delete the orderTariffPlan
        restOrderTariffPlanMockMvc.perform(delete("/api/order-tariff-plans/{id}", orderTariffPlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTariffPlan> orderTariffPlanList = orderTariffPlanRepository.findAll();
        assertThat(orderTariffPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
