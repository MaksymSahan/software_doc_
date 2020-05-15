package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.TMobileApp;
import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.domain.Admin;
import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.repository.TariffPlanRepository;
import com.sahan.t_mobile.service.TariffPlanService;
import com.sahan.t_mobile.service.dto.TariffPlanCriteria;
import com.sahan.t_mobile.service.TariffPlanQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TariffPlanResource} REST controller.
 */
@SpringBootTest(classes = TMobileApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TariffPlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNET = "AAAAAAAAAA";
    private static final String UPDATED_INTERNET = "BBBBBBBBBB";

    private static final String DEFAULT_CALLS = "AAAAAAAAAA";
    private static final String UPDATED_CALLS = "BBBBBBBBBB";

    private static final String DEFAULT_SMS = "AAAAAAAAAA";
    private static final String UPDATED_SMS = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    @Autowired
    private TariffPlanRepository tariffPlanRepository;

    @Autowired
    private TariffPlanService tariffPlanService;

    @Autowired
    private TariffPlanQueryService tariffPlanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTariffPlanMockMvc;

    private TariffPlan tariffPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TariffPlan createEntity(EntityManager em) {
        TariffPlan tariffPlan = new TariffPlan()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .internet(DEFAULT_INTERNET)
            .calls(DEFAULT_CALLS)
            .sms(DEFAULT_SMS)
            .price(DEFAULT_PRICE);
        return tariffPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TariffPlan createUpdatedEntity(EntityManager em) {
        TariffPlan tariffPlan = new TariffPlan()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .internet(UPDATED_INTERNET)
            .calls(UPDATED_CALLS)
            .sms(UPDATED_SMS)
            .price(UPDATED_PRICE);
        return tariffPlan;
    }

    @BeforeEach
    public void initTest() {
        tariffPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createTariffPlan() throws Exception {
        int databaseSizeBeforeCreate = tariffPlanRepository.findAll().size();

        // Create the TariffPlan
        restTariffPlanMockMvc.perform(post("/api/tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tariffPlan)))
            .andExpect(status().isCreated());

        // Validate the TariffPlan in the database
        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TariffPlan testTariffPlan = tariffPlanList.get(tariffPlanList.size() - 1);
        assertThat(testTariffPlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTariffPlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTariffPlan.getInternet()).isEqualTo(DEFAULT_INTERNET);
        assertThat(testTariffPlan.getCalls()).isEqualTo(DEFAULT_CALLS);
        assertThat(testTariffPlan.getSms()).isEqualTo(DEFAULT_SMS);
        assertThat(testTariffPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createTariffPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tariffPlanRepository.findAll().size();

        // Create the TariffPlan with an existing ID
        tariffPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTariffPlanMockMvc.perform(post("/api/tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tariffPlan)))
            .andExpect(status().isBadRequest());

        // Validate the TariffPlan in the database
        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffPlanRepository.findAll().size();
        // set the field null
        tariffPlan.setName(null);

        // Create the TariffPlan, which fails.

        restTariffPlanMockMvc.perform(post("/api/tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tariffPlan)))
            .andExpect(status().isBadRequest());

        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTariffPlans() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList
        restTariffPlanMockMvc.perform(get("/api/tariff-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tariffPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].internet").value(hasItem(DEFAULT_INTERNET)))
            .andExpect(jsonPath("$.[*].calls").value(hasItem(DEFAULT_CALLS)))
            .andExpect(jsonPath("$.[*].sms").value(hasItem(DEFAULT_SMS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTariffPlan() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get the tariffPlan
        restTariffPlanMockMvc.perform(get("/api/tariff-plans/{id}", tariffPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tariffPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.internet").value(DEFAULT_INTERNET))
            .andExpect(jsonPath("$.calls").value(DEFAULT_CALLS))
            .andExpect(jsonPath("$.sms").value(DEFAULT_SMS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }


    @Test
    @Transactional
    public void getTariffPlansByIdFiltering() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        Long id = tariffPlan.getId();

        defaultTariffPlanShouldBeFound("id.equals=" + id);
        defaultTariffPlanShouldNotBeFound("id.notEquals=" + id);

        defaultTariffPlanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTariffPlanShouldNotBeFound("id.greaterThan=" + id);

        defaultTariffPlanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTariffPlanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name equals to DEFAULT_NAME
        defaultTariffPlanShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tariffPlanList where name equals to UPDATED_NAME
        defaultTariffPlanShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name not equals to DEFAULT_NAME
        defaultTariffPlanShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tariffPlanList where name not equals to UPDATED_NAME
        defaultTariffPlanShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTariffPlanShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tariffPlanList where name equals to UPDATED_NAME
        defaultTariffPlanShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name is not null
        defaultTariffPlanShouldBeFound("name.specified=true");

        // Get all the tariffPlanList where name is null
        defaultTariffPlanShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTariffPlansByNameContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name contains DEFAULT_NAME
        defaultTariffPlanShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tariffPlanList where name contains UPDATED_NAME
        defaultTariffPlanShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where name does not contain DEFAULT_NAME
        defaultTariffPlanShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tariffPlanList where name does not contain UPDATED_NAME
        defaultTariffPlanShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description equals to DEFAULT_DESCRIPTION
        defaultTariffPlanShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tariffPlanList where description equals to UPDATED_DESCRIPTION
        defaultTariffPlanShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description not equals to DEFAULT_DESCRIPTION
        defaultTariffPlanShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the tariffPlanList where description not equals to UPDATED_DESCRIPTION
        defaultTariffPlanShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTariffPlanShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tariffPlanList where description equals to UPDATED_DESCRIPTION
        defaultTariffPlanShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description is not null
        defaultTariffPlanShouldBeFound("description.specified=true");

        // Get all the tariffPlanList where description is null
        defaultTariffPlanShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTariffPlansByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description contains DEFAULT_DESCRIPTION
        defaultTariffPlanShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the tariffPlanList where description contains UPDATED_DESCRIPTION
        defaultTariffPlanShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where description does not contain DEFAULT_DESCRIPTION
        defaultTariffPlanShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the tariffPlanList where description does not contain UPDATED_DESCRIPTION
        defaultTariffPlanShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByInternetIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet equals to DEFAULT_INTERNET
        defaultTariffPlanShouldBeFound("internet.equals=" + DEFAULT_INTERNET);

        // Get all the tariffPlanList where internet equals to UPDATED_INTERNET
        defaultTariffPlanShouldNotBeFound("internet.equals=" + UPDATED_INTERNET);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByInternetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet not equals to DEFAULT_INTERNET
        defaultTariffPlanShouldNotBeFound("internet.notEquals=" + DEFAULT_INTERNET);

        // Get all the tariffPlanList where internet not equals to UPDATED_INTERNET
        defaultTariffPlanShouldBeFound("internet.notEquals=" + UPDATED_INTERNET);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByInternetIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet in DEFAULT_INTERNET or UPDATED_INTERNET
        defaultTariffPlanShouldBeFound("internet.in=" + DEFAULT_INTERNET + "," + UPDATED_INTERNET);

        // Get all the tariffPlanList where internet equals to UPDATED_INTERNET
        defaultTariffPlanShouldNotBeFound("internet.in=" + UPDATED_INTERNET);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByInternetIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet is not null
        defaultTariffPlanShouldBeFound("internet.specified=true");

        // Get all the tariffPlanList where internet is null
        defaultTariffPlanShouldNotBeFound("internet.specified=false");
    }
                @Test
    @Transactional
    public void getAllTariffPlansByInternetContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet contains DEFAULT_INTERNET
        defaultTariffPlanShouldBeFound("internet.contains=" + DEFAULT_INTERNET);

        // Get all the tariffPlanList where internet contains UPDATED_INTERNET
        defaultTariffPlanShouldNotBeFound("internet.contains=" + UPDATED_INTERNET);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByInternetNotContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where internet does not contain DEFAULT_INTERNET
        defaultTariffPlanShouldNotBeFound("internet.doesNotContain=" + DEFAULT_INTERNET);

        // Get all the tariffPlanList where internet does not contain UPDATED_INTERNET
        defaultTariffPlanShouldBeFound("internet.doesNotContain=" + UPDATED_INTERNET);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls equals to DEFAULT_CALLS
        defaultTariffPlanShouldBeFound("calls.equals=" + DEFAULT_CALLS);

        // Get all the tariffPlanList where calls equals to UPDATED_CALLS
        defaultTariffPlanShouldNotBeFound("calls.equals=" + UPDATED_CALLS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByCallsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls not equals to DEFAULT_CALLS
        defaultTariffPlanShouldNotBeFound("calls.notEquals=" + DEFAULT_CALLS);

        // Get all the tariffPlanList where calls not equals to UPDATED_CALLS
        defaultTariffPlanShouldBeFound("calls.notEquals=" + UPDATED_CALLS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByCallsIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls in DEFAULT_CALLS or UPDATED_CALLS
        defaultTariffPlanShouldBeFound("calls.in=" + DEFAULT_CALLS + "," + UPDATED_CALLS);

        // Get all the tariffPlanList where calls equals to UPDATED_CALLS
        defaultTariffPlanShouldNotBeFound("calls.in=" + UPDATED_CALLS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls is not null
        defaultTariffPlanShouldBeFound("calls.specified=true");

        // Get all the tariffPlanList where calls is null
        defaultTariffPlanShouldNotBeFound("calls.specified=false");
    }
                @Test
    @Transactional
    public void getAllTariffPlansByCallsContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls contains DEFAULT_CALLS
        defaultTariffPlanShouldBeFound("calls.contains=" + DEFAULT_CALLS);

        // Get all the tariffPlanList where calls contains UPDATED_CALLS
        defaultTariffPlanShouldNotBeFound("calls.contains=" + UPDATED_CALLS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByCallsNotContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where calls does not contain DEFAULT_CALLS
        defaultTariffPlanShouldNotBeFound("calls.doesNotContain=" + DEFAULT_CALLS);

        // Get all the tariffPlanList where calls does not contain UPDATED_CALLS
        defaultTariffPlanShouldBeFound("calls.doesNotContain=" + UPDATED_CALLS);
    }


    @Test
    @Transactional
    public void getAllTariffPlansBySmsIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms equals to DEFAULT_SMS
        defaultTariffPlanShouldBeFound("sms.equals=" + DEFAULT_SMS);

        // Get all the tariffPlanList where sms equals to UPDATED_SMS
        defaultTariffPlanShouldNotBeFound("sms.equals=" + UPDATED_SMS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansBySmsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms not equals to DEFAULT_SMS
        defaultTariffPlanShouldNotBeFound("sms.notEquals=" + DEFAULT_SMS);

        // Get all the tariffPlanList where sms not equals to UPDATED_SMS
        defaultTariffPlanShouldBeFound("sms.notEquals=" + UPDATED_SMS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansBySmsIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms in DEFAULT_SMS or UPDATED_SMS
        defaultTariffPlanShouldBeFound("sms.in=" + DEFAULT_SMS + "," + UPDATED_SMS);

        // Get all the tariffPlanList where sms equals to UPDATED_SMS
        defaultTariffPlanShouldNotBeFound("sms.in=" + UPDATED_SMS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansBySmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms is not null
        defaultTariffPlanShouldBeFound("sms.specified=true");

        // Get all the tariffPlanList where sms is null
        defaultTariffPlanShouldNotBeFound("sms.specified=false");
    }
                @Test
    @Transactional
    public void getAllTariffPlansBySmsContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms contains DEFAULT_SMS
        defaultTariffPlanShouldBeFound("sms.contains=" + DEFAULT_SMS);

        // Get all the tariffPlanList where sms contains UPDATED_SMS
        defaultTariffPlanShouldNotBeFound("sms.contains=" + UPDATED_SMS);
    }

    @Test
    @Transactional
    public void getAllTariffPlansBySmsNotContainsSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where sms does not contain DEFAULT_SMS
        defaultTariffPlanShouldNotBeFound("sms.doesNotContain=" + DEFAULT_SMS);

        // Get all the tariffPlanList where sms does not contain UPDATED_SMS
        defaultTariffPlanShouldBeFound("sms.doesNotContain=" + UPDATED_SMS);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price equals to DEFAULT_PRICE
        defaultTariffPlanShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price equals to UPDATED_PRICE
        defaultTariffPlanShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price not equals to DEFAULT_PRICE
        defaultTariffPlanShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price not equals to UPDATED_PRICE
        defaultTariffPlanShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultTariffPlanShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the tariffPlanList where price equals to UPDATED_PRICE
        defaultTariffPlanShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price is not null
        defaultTariffPlanShouldBeFound("price.specified=true");

        // Get all the tariffPlanList where price is null
        defaultTariffPlanShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price is greater than or equal to DEFAULT_PRICE
        defaultTariffPlanShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price is greater than or equal to UPDATED_PRICE
        defaultTariffPlanShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price is less than or equal to DEFAULT_PRICE
        defaultTariffPlanShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price is less than or equal to SMALLER_PRICE
        defaultTariffPlanShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price is less than DEFAULT_PRICE
        defaultTariffPlanShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price is less than UPDATED_PRICE
        defaultTariffPlanShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTariffPlansByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);

        // Get all the tariffPlanList where price is greater than DEFAULT_PRICE
        defaultTariffPlanShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the tariffPlanList where price is greater than SMALLER_PRICE
        defaultTariffPlanShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllTariffPlansByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);
        Admin updatedBy = AdminResourceIT.createEntity(em);
        em.persist(updatedBy);
        em.flush();
        tariffPlan.setUpdatedBy(updatedBy);
        tariffPlanRepository.saveAndFlush(tariffPlan);
        Long updatedById = updatedBy.getId();

        // Get all the tariffPlanList where updatedBy equals to updatedById
        defaultTariffPlanShouldBeFound("updatedById.equals=" + updatedById);

        // Get all the tariffPlanList where updatedBy equals to updatedById + 1
        defaultTariffPlanShouldNotBeFound("updatedById.equals=" + (updatedById + 1));
    }


    @Test
    @Transactional
    public void getAllTariffPlansByUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        tariffPlanRepository.saveAndFlush(tariffPlan);
        MobileUser users = MobileUserResourceIT.createEntity(em);
        em.persist(users);
        em.flush();
        tariffPlan.addUsers(users);
        tariffPlanRepository.saveAndFlush(tariffPlan);
        Long usersId = users.getId();

        // Get all the tariffPlanList where users equals to usersId
        defaultTariffPlanShouldBeFound("usersId.equals=" + usersId);

        // Get all the tariffPlanList where users equals to usersId + 1
        defaultTariffPlanShouldNotBeFound("usersId.equals=" + (usersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTariffPlanShouldBeFound(String filter) throws Exception {
        restTariffPlanMockMvc.perform(get("/api/tariff-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tariffPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].internet").value(hasItem(DEFAULT_INTERNET)))
            .andExpect(jsonPath("$.[*].calls").value(hasItem(DEFAULT_CALLS)))
            .andExpect(jsonPath("$.[*].sms").value(hasItem(DEFAULT_SMS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restTariffPlanMockMvc.perform(get("/api/tariff-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTariffPlanShouldNotBeFound(String filter) throws Exception {
        restTariffPlanMockMvc.perform(get("/api/tariff-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTariffPlanMockMvc.perform(get("/api/tariff-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTariffPlan() throws Exception {
        // Get the tariffPlan
        restTariffPlanMockMvc.perform(get("/api/tariff-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTariffPlan() throws Exception {
        // Initialize the database
        tariffPlanService.save(tariffPlan);

        int databaseSizeBeforeUpdate = tariffPlanRepository.findAll().size();

        // Update the tariffPlan
        TariffPlan updatedTariffPlan = tariffPlanRepository.findById(tariffPlan.getId()).get();
        // Disconnect from session so that the updates on updatedTariffPlan are not directly saved in db
        em.detach(updatedTariffPlan);
        updatedTariffPlan
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .internet(UPDATED_INTERNET)
            .calls(UPDATED_CALLS)
            .sms(UPDATED_SMS)
            .price(UPDATED_PRICE);

        restTariffPlanMockMvc.perform(put("/api/tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTariffPlan)))
            .andExpect(status().isOk());

        // Validate the TariffPlan in the database
        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeUpdate);
        TariffPlan testTariffPlan = tariffPlanList.get(tariffPlanList.size() - 1);
        assertThat(testTariffPlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTariffPlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTariffPlan.getInternet()).isEqualTo(UPDATED_INTERNET);
        assertThat(testTariffPlan.getCalls()).isEqualTo(UPDATED_CALLS);
        assertThat(testTariffPlan.getSms()).isEqualTo(UPDATED_SMS);
        assertThat(testTariffPlan.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTariffPlan() throws Exception {
        int databaseSizeBeforeUpdate = tariffPlanRepository.findAll().size();

        // Create the TariffPlan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariffPlanMockMvc.perform(put("/api/tariff-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tariffPlan)))
            .andExpect(status().isBadRequest());

        // Validate the TariffPlan in the database
        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTariffPlan() throws Exception {
        // Initialize the database
        tariffPlanService.save(tariffPlan);

        int databaseSizeBeforeDelete = tariffPlanRepository.findAll().size();

        // Delete the tariffPlan
        restTariffPlanMockMvc.perform(delete("/api/tariff-plans/{id}", tariffPlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TariffPlan> tariffPlanList = tariffPlanRepository.findAll();
        assertThat(tariffPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
