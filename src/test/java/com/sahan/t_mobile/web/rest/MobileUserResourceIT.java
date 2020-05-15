package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.TMobileApp;
import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.domain.Payment;
import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.domain.Admin;
import com.sahan.t_mobile.domain.OrderTariffPlan;
import com.sahan.t_mobile.repository.MobileUserRepository;
import com.sahan.t_mobile.service.MobileUserService;
import com.sahan.t_mobile.service.dto.MobileUserCriteria;
import com.sahan.t_mobile.service.MobileUserQueryService;

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
 * Integration tests for the {@link MobileUserResource} REST controller.
 */
@SpringBootTest(classes = TMobileApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MobileUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "4898197093";
    private static final String UPDATED_PHONE = "5996058264";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private MobileUserRepository mobileUserRepository;

    @Mock
    private MobileUserRepository mobileUserRepositoryMock;

    @Mock
    private MobileUserService mobileUserServiceMock;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private MobileUserQueryService mobileUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobileUserMockMvc;

    private MobileUser mobileUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileUser createEntity(EntityManager em) {
        MobileUser mobileUser = new MobileUser()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS);
        return mobileUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileUser createUpdatedEntity(EntityManager em) {
        MobileUser mobileUser = new MobileUser()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS);
        return mobileUser;
    }

    @BeforeEach
    public void initTest() {
        mobileUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMobileUser() throws Exception {
        int databaseSizeBeforeCreate = mobileUserRepository.findAll().size();

        // Create the MobileUser
        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isCreated());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeCreate + 1);
        MobileUser testMobileUser = mobileUserList.get(mobileUserList.size() - 1);
        assertThat(testMobileUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMobileUser.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testMobileUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMobileUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMobileUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createMobileUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mobileUserRepository.findAll().size();

        // Create the MobileUser with an existing ID
        mobileUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobileUserRepository.findAll().size();
        // set the field null
        mobileUser.setName(null);

        // Create the MobileUser, which fails.

        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobileUserRepository.findAll().size();
        // set the field null
        mobileUser.setSurname(null);

        // Create the MobileUser, which fails.

        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobileUserRepository.findAll().size();
        // set the field null
        mobileUser.setEmail(null);

        // Create the MobileUser, which fails.

        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMobileUsers() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList
        restMobileUserMockMvc.perform(get("/api/mobile-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMobileUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(mobileUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMobileUserMockMvc.perform(get("/api/mobile-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(mobileUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMobileUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mobileUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMobileUserMockMvc.perform(get("/api/mobile-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(mobileUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMobileUser() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get the mobileUser
        restMobileUserMockMvc.perform(get("/api/mobile-users/{id}", mobileUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobileUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }


    @Test
    @Transactional
    public void getMobileUsersByIdFiltering() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        Long id = mobileUser.getId();

        defaultMobileUserShouldBeFound("id.equals=" + id);
        defaultMobileUserShouldNotBeFound("id.notEquals=" + id);

        defaultMobileUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMobileUserShouldNotBeFound("id.greaterThan=" + id);

        defaultMobileUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMobileUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMobileUsersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name equals to DEFAULT_NAME
        defaultMobileUserShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mobileUserList where name equals to UPDATED_NAME
        defaultMobileUserShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name not equals to DEFAULT_NAME
        defaultMobileUserShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mobileUserList where name not equals to UPDATED_NAME
        defaultMobileUserShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMobileUserShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mobileUserList where name equals to UPDATED_NAME
        defaultMobileUserShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name is not null
        defaultMobileUserShouldBeFound("name.specified=true");

        // Get all the mobileUserList where name is null
        defaultMobileUserShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMobileUsersByNameContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name contains DEFAULT_NAME
        defaultMobileUserShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mobileUserList where name contains UPDATED_NAME
        defaultMobileUserShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where name does not contain DEFAULT_NAME
        defaultMobileUserShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mobileUserList where name does not contain UPDATED_NAME
        defaultMobileUserShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMobileUsersBySurnameIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname equals to DEFAULT_SURNAME
        defaultMobileUserShouldBeFound("surname.equals=" + DEFAULT_SURNAME);

        // Get all the mobileUserList where surname equals to UPDATED_SURNAME
        defaultMobileUserShouldNotBeFound("surname.equals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersBySurnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname not equals to DEFAULT_SURNAME
        defaultMobileUserShouldNotBeFound("surname.notEquals=" + DEFAULT_SURNAME);

        // Get all the mobileUserList where surname not equals to UPDATED_SURNAME
        defaultMobileUserShouldBeFound("surname.notEquals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersBySurnameIsInShouldWork() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname in DEFAULT_SURNAME or UPDATED_SURNAME
        defaultMobileUserShouldBeFound("surname.in=" + DEFAULT_SURNAME + "," + UPDATED_SURNAME);

        // Get all the mobileUserList where surname equals to UPDATED_SURNAME
        defaultMobileUserShouldNotBeFound("surname.in=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersBySurnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname is not null
        defaultMobileUserShouldBeFound("surname.specified=true");

        // Get all the mobileUserList where surname is null
        defaultMobileUserShouldNotBeFound("surname.specified=false");
    }
                @Test
    @Transactional
    public void getAllMobileUsersBySurnameContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname contains DEFAULT_SURNAME
        defaultMobileUserShouldBeFound("surname.contains=" + DEFAULT_SURNAME);

        // Get all the mobileUserList where surname contains UPDATED_SURNAME
        defaultMobileUserShouldNotBeFound("surname.contains=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllMobileUsersBySurnameNotContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where surname does not contain DEFAULT_SURNAME
        defaultMobileUserShouldNotBeFound("surname.doesNotContain=" + DEFAULT_SURNAME);

        // Get all the mobileUserList where surname does not contain UPDATED_SURNAME
        defaultMobileUserShouldBeFound("surname.doesNotContain=" + UPDATED_SURNAME);
    }


    @Test
    @Transactional
    public void getAllMobileUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email equals to DEFAULT_EMAIL
        defaultMobileUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the mobileUserList where email equals to UPDATED_EMAIL
        defaultMobileUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email not equals to DEFAULT_EMAIL
        defaultMobileUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the mobileUserList where email not equals to UPDATED_EMAIL
        defaultMobileUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMobileUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the mobileUserList where email equals to UPDATED_EMAIL
        defaultMobileUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email is not null
        defaultMobileUserShouldBeFound("email.specified=true");

        // Get all the mobileUserList where email is null
        defaultMobileUserShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllMobileUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email contains DEFAULT_EMAIL
        defaultMobileUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the mobileUserList where email contains UPDATED_EMAIL
        defaultMobileUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where email does not contain DEFAULT_EMAIL
        defaultMobileUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the mobileUserList where email does not contain UPDATED_EMAIL
        defaultMobileUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllMobileUsersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone equals to DEFAULT_PHONE
        defaultMobileUserShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the mobileUserList where phone equals to UPDATED_PHONE
        defaultMobileUserShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone not equals to DEFAULT_PHONE
        defaultMobileUserShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the mobileUserList where phone not equals to UPDATED_PHONE
        defaultMobileUserShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultMobileUserShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the mobileUserList where phone equals to UPDATED_PHONE
        defaultMobileUserShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone is not null
        defaultMobileUserShouldBeFound("phone.specified=true");

        // Get all the mobileUserList where phone is null
        defaultMobileUserShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllMobileUsersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone contains DEFAULT_PHONE
        defaultMobileUserShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the mobileUserList where phone contains UPDATED_PHONE
        defaultMobileUserShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where phone does not contain DEFAULT_PHONE
        defaultMobileUserShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the mobileUserList where phone does not contain UPDATED_PHONE
        defaultMobileUserShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllMobileUsersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address equals to DEFAULT_ADDRESS
        defaultMobileUserShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the mobileUserList where address equals to UPDATED_ADDRESS
        defaultMobileUserShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address not equals to DEFAULT_ADDRESS
        defaultMobileUserShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the mobileUserList where address not equals to UPDATED_ADDRESS
        defaultMobileUserShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultMobileUserShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the mobileUserList where address equals to UPDATED_ADDRESS
        defaultMobileUserShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address is not null
        defaultMobileUserShouldBeFound("address.specified=true");

        // Get all the mobileUserList where address is null
        defaultMobileUserShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllMobileUsersByAddressContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address contains DEFAULT_ADDRESS
        defaultMobileUserShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the mobileUserList where address contains UPDATED_ADDRESS
        defaultMobileUserShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMobileUsersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList where address does not contain DEFAULT_ADDRESS
        defaultMobileUserShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the mobileUserList where address does not contain UPDATED_ADDRESS
        defaultMobileUserShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllMobileUsersByPaymentsIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);
        Payment payments = PaymentResourceIT.createEntity(em);
        em.persist(payments);
        em.flush();
        mobileUser.addPayments(payments);
        mobileUserRepository.saveAndFlush(mobileUser);
        Long paymentsId = payments.getId();

        // Get all the mobileUserList where payments equals to paymentsId
        defaultMobileUserShouldBeFound("paymentsId.equals=" + paymentsId);

        // Get all the mobileUserList where payments equals to paymentsId + 1
        defaultMobileUserShouldNotBeFound("paymentsId.equals=" + (paymentsId + 1));
    }


    @Test
    @Transactional
    public void getAllMobileUsersByTariffsIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);
        TariffPlan tariffs = TariffPlanResourceIT.createEntity(em);
        em.persist(tariffs);
        em.flush();
        mobileUser.addTariffs(tariffs);
        mobileUserRepository.saveAndFlush(mobileUser);
        Long tariffsId = tariffs.getId();

        // Get all the mobileUserList where tariffs equals to tariffsId
        defaultMobileUserShouldBeFound("tariffsId.equals=" + tariffsId);

        // Get all the mobileUserList where tariffs equals to tariffsId + 1
        defaultMobileUserShouldNotBeFound("tariffsId.equals=" + (tariffsId + 1));
    }


    @Test
    @Transactional
    public void getAllMobileUsersBySupportedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);
        Admin supportedBy = AdminResourceIT.createEntity(em);
        em.persist(supportedBy);
        em.flush();
        mobileUser.addSupportedBy(supportedBy);
        mobileUserRepository.saveAndFlush(mobileUser);
        Long supportedById = supportedBy.getId();

        // Get all the mobileUserList where supportedBy equals to supportedById
        defaultMobileUserShouldBeFound("supportedById.equals=" + supportedById);

        // Get all the mobileUserList where supportedBy equals to supportedById + 1
        defaultMobileUserShouldNotBeFound("supportedById.equals=" + (supportedById + 1));
    }


    @Test
    @Transactional
    public void getAllMobileUsersByTariffPlanOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);
        OrderTariffPlan tariffPlanOrder = OrderTariffPlanResourceIT.createEntity(em);
        em.persist(tariffPlanOrder);
        em.flush();
        mobileUser.addTariffPlanOrder(tariffPlanOrder);
        mobileUserRepository.saveAndFlush(mobileUser);
        Long tariffPlanOrderId = tariffPlanOrder.getId();

        // Get all the mobileUserList where tariffPlanOrder equals to tariffPlanOrderId
        defaultMobileUserShouldBeFound("tariffPlanOrderId.equals=" + tariffPlanOrderId);

        // Get all the mobileUserList where tariffPlanOrder equals to tariffPlanOrderId + 1
        defaultMobileUserShouldNotBeFound("tariffPlanOrderId.equals=" + (tariffPlanOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMobileUserShouldBeFound(String filter) throws Exception {
        restMobileUserMockMvc.perform(get("/api/mobile-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));

        // Check, that the count call also returns 1
        restMobileUserMockMvc.perform(get("/api/mobile-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMobileUserShouldNotBeFound(String filter) throws Exception {
        restMobileUserMockMvc.perform(get("/api/mobile-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMobileUserMockMvc.perform(get("/api/mobile-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMobileUser() throws Exception {
        // Get the mobileUser
        restMobileUserMockMvc.perform(get("/api/mobile-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMobileUser() throws Exception {
        // Initialize the database
        mobileUserService.save(mobileUser);

        int databaseSizeBeforeUpdate = mobileUserRepository.findAll().size();

        // Update the mobileUser
        MobileUser updatedMobileUser = mobileUserRepository.findById(mobileUser.getId()).get();
        // Disconnect from session so that the updates on updatedMobileUser are not directly saved in db
        em.detach(updatedMobileUser);
        updatedMobileUser
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS);

        restMobileUserMockMvc.perform(put("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMobileUser)))
            .andExpect(status().isOk());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeUpdate);
        MobileUser testMobileUser = mobileUserList.get(mobileUserList.size() - 1);
        assertThat(testMobileUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMobileUser.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testMobileUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMobileUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMobileUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingMobileUser() throws Exception {
        int databaseSizeBeforeUpdate = mobileUserRepository.findAll().size();

        // Create the MobileUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobileUserMockMvc.perform(put("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMobileUser() throws Exception {
        // Initialize the database
        mobileUserService.save(mobileUser);

        int databaseSizeBeforeDelete = mobileUserRepository.findAll().size();

        // Delete the mobileUser
        restMobileUserMockMvc.perform(delete("/api/mobile-users/{id}", mobileUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
