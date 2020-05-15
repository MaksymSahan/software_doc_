package com.sahan.t_mobile.repository;

import com.sahan.t_mobile.domain.MobileUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MobileUser entity.
 */
@Repository
public interface MobileUserRepository extends JpaRepository<MobileUser, Long>, JpaSpecificationExecutor<MobileUser> {

    @Query(value = "select distinct mobileUser from MobileUser mobileUser left join fetch mobileUser.tariffs",
        countQuery = "select count(distinct mobileUser) from MobileUser mobileUser")
    Page<MobileUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct mobileUser from MobileUser mobileUser left join fetch mobileUser.tariffs")
    List<MobileUser> findAllWithEagerRelationships();

    @Query("select mobileUser from MobileUser mobileUser left join fetch mobileUser.tariffs where mobileUser.id =:id")
    Optional<MobileUser> findOneWithEagerRelationships(@Param("id") Long id);
}
