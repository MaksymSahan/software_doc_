package com.sahan.t_mobile.service.impl;

import com.sahan.t_mobile.service.MobileUserService;
import com.sahan.t_mobile.domain.MobileUser;
import com.sahan.t_mobile.repository.MobileUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MobileUser}.
 */
@Service
@Transactional
public class MobileUserServiceImpl implements MobileUserService {

    private final Logger log = LoggerFactory.getLogger(MobileUserServiceImpl.class);

    private final MobileUserRepository mobileUserRepository;

    public MobileUserServiceImpl(MobileUserRepository mobileUserRepository) {
        this.mobileUserRepository = mobileUserRepository;
    }

    /**
     * Save a mobileUser.
     *
     * @param mobileUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MobileUser save(MobileUser mobileUser) {
        log.debug("Request to save MobileUser : {}", mobileUser);
        return mobileUserRepository.save(mobileUser);
    }

    /**
     * Get all the mobileUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MobileUser> findAll(Pageable pageable) {
        log.debug("Request to get all MobileUsers");
        return mobileUserRepository.findAll(pageable);
    }

    /**
     * Get all the mobileUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MobileUser> findAllWithEagerRelationships(Pageable pageable) {
        return mobileUserRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one mobileUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MobileUser> findOne(Long id) {
        log.debug("Request to get MobileUser : {}", id);
        return mobileUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the mobileUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MobileUser : {}", id);
        mobileUserRepository.deleteById(id);
    }
}
