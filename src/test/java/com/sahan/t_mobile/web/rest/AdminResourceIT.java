package com.sahan.t_mobile.web.rest;

import com.sahan.t_mobile.TMobileApp;
import com.sahan.t_mobile.domain.Admin;
import com.sahan.t_mobile.domain.TariffPlan;
import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.repository.AdminRepository;
import com.sahan.t_mobile.service.AdminService;
import com.sahan.t_mobile.service.dto.AdminCriteria;
import com.sahan.t_mobile.service.AdminQueryService;

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
 * Integration tests for the {@link AdminResource} REST controller.
 */
@SpringBootTest(classes = TMobileApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdminResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AdminRepository adminRepository;

    @Mock
    private AdminRepository adminRepositoryMock;

    @Mock
    private AdminService adminServiceMock;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminQueryService adminQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminMockMvc;

    private Admin admin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createEntity(EntityManager em) {
        Admin admin = new Admin()
            .name(DEFAULT_NAME);
        return admin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createUpdatedEntity(EntityManager em) {
        Admin admin = new Admin()
            .name(UPDATED_NAME);
        return admin;
    }

    @BeforeEach
    public void initTest() {
        admin = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdmin() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isCreated());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate + 1);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin with an existing ID
        admin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setName(null);

        // Create the Admin, which fails.

        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdmins() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAdminsWithEagerRelationshipsIsEnabled() throws Exception {
        when(adminServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdminMockMvc.perform(get("/api/admins?eagerload=true"))
            .andExpect(status().isOk());

        verify(adminServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAdminsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(adminServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdminMockMvc.perform(get("/api/admins?eagerload=true"))
            .andExpect(status().isOk());

        verify(adminServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", admin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(admin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getAdminsByIdFiltering() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        Long id = admin.getId();

        defaultAdminShouldBeFound("id.equals=" + id);
        defaultAdminShouldNotBeFound("id.notEquals=" + id);

        defaultAdminShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdminShouldNotBeFound("id.greaterThan=" + id);

        defaultAdminShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdminShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdminsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name equals to DEFAULT_NAME
        defaultAdminShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adminList where name equals to UPDATED_NAME
        defaultAdminShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name not equals to DEFAULT_NAME
        defaultAdminShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adminList where name not equals to UPDATED_NAME
        defaultAdminShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdminShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adminList where name equals to UPDATED_NAME
        defaultAdminShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name is not null
        defaultAdminShouldBeFound("name.specified=true");

        // Get all the adminList where name is null
        defaultAdminShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdminsByNameContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name contains DEFAULT_NAME
        defaultAdminShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adminList where name contains UPDATED_NAME
        defaultAdminShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where name does not contain DEFAULT_NAME
        defaultAdminShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adminList where name does not contain UPDATED_NAME
        defaultAdminShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdminsByUpdatedTariffsIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);
        TariffPlan updatedTariffs = TariffPlanResourceIT.createEntity(em);
        em.persist(updatedTariffs);
        em.flush();
        admin.addUpdatedTariffs(updatedTariffs);
        adminRepository.saveAndFlush(admin);
        Long updatedTariffsId = updatedTariffs.getId();

        // Get all the adminList where updatedTariffs equals to updatedTariffsId
        defaultAdminShouldBeFound("updatedTariffsId.equals=" + updatedTariffsId);

        // Get all the adminList where updatedTariffs equals to updatedTariffsId + 1
        defaultAdminShouldNotBeFound("updatedTariffsId.equals=" + (updatedTariffsId + 1));
    }


    @Test
    @Transactional
    public void getAllAdminsByWhoHelpedIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);
        MobileUser whoHelped = MobileUserResourceIT.createEntity(em);
        em.persist(whoHelped);
        em.flush();
        admin.addWhoHelped(whoHelped);
        adminRepository.saveAndFlush(admin);
        Long whoHelpedId = whoHelped.getId();

        // Get all the adminList where whoHelped equals to whoHelpedId
        defaultAdminShouldBeFound("whoHelpedId.equals=" + whoHelpedId);

        // Get all the adminList where whoHelped equals to whoHelpedId + 1
        defaultAdminShouldNotBeFound("whoHelpedId.equals=" + (whoHelpedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdminShouldBeFound(String filter) throws Exception {
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restAdminMockMvc.perform(get("/api/admins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdminShouldNotBeFound(String filter) throws Exception {
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdminMockMvc.perform(get("/api/admins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdmin() throws Exception {
        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdmin() throws Exception {
        // Initialize the database
        adminService.save(admin);

        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Update the admin
        Admin updatedAdmin = adminRepository.findById(admin.getId()).get();
        // Disconnect from session so that the updates on updatedAdmin are not directly saved in db
        em.detach(updatedAdmin);
        updatedAdmin
            .name(UPDATED_NAME);

        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdmin)))
            .andExpect(status().isOk());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAdmin() throws Exception {
        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Create the Admin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdmin() throws Exception {
        // Initialize the database
        adminService.save(admin);

        int databaseSizeBeforeDelete = adminRepository.findAll().size();

        // Delete the admin
        restAdminMockMvc.perform(delete("/api/admins/{id}", admin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
