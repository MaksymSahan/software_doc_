package com.sahan.t_mobile.service;

import com.sahan.t_mobile.domain.MobileUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MobileUser}.
 */
public interface MobileUserService {

    /**
     * Save a mobileUser.
     *
     * @param mobileUser the entity to save.
     * @return the persisted entity.
     */
    MobileUser save(MobileUser mobileUser);

    /**
     * Get all the mobileUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MobileUser> findAll(Pageable pageable);

    /**
     * Get all the mobileUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<MobileUser> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mobileUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobileUser> findOne(Long id);

    /**
     * Delete the "id" mobileUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
