package com.czerner.foddr.dominio.dados;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;

@Repository
public interface SbcSetRepository extends JpaRepository<SbcSet, Integer> {

    Optional<SbcSet> findBySetId(Integer setId);

    boolean existsBySetId(Integer setId);

    List<SbcSet> findByCategory_Id(Integer categoryId);

    List<SbcSet> findByExpiresAtGreaterThan(Instant now);

    @Query("""
        select distinct s from SbcSet s
        join s.challenges c
        join c.requirements r
        where s.category.id = :categoryId
          and r.type = 'TEAM_RATING_1_TO_100'
          and r.eligibilityValue between :minRating and :maxRating
          and (s.expiresAt is null or s.expiresAt > :now)
        """)
    List<SbcSet> findByCategoryWithRatingRequirementBetween(
        @Param("categoryId") Integer categoryId,
        @Param("minRating") Integer minRating,
        @Param("maxRating") Integer maxRating,
        @Param("now") Instant now
    );
}
