package com.sahan.t_mobile.repository;

import com.sahan.t_mobile.domain.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Admin entity.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    @Query(value = "select distinct admin from Admin admin left join fetch admin.whoHelpeds",
        countQuery = "select count(distinct admin) from Admin admin")
    Page<Admin> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct admin from Admin admin left join fetch admin.whoHelpeds")
    List<Admin> findAllWithEagerRelationships();

    @Query("select admin from Admin admin left join fetch admin.whoHelpeds where admin.id =:id")
    Optional<Admin> findOneWithEagerRelationships(@Param("id") Long id);
}
