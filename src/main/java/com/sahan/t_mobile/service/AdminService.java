package com.sahan.t_mobile.service;

import com.sahan.t_mobile.domain.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Admin}.
 */
public interface AdminService {

    /**
     * Save a admin.
     *
     * @param admin the entity to save.
     * @return the persisted entity.
     */
    Admin save(Admin admin);

    /**
     * Get all the admins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Admin> findAll(Pageable pageable);

    /**
     * Get all the admins with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Admin> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" admin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Admin> findOne(Long id);

    /**
     * Delete the "id" admin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
