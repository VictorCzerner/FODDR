package com.czerner.foddr.dominio.dados;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcChallenge;

@Repository
public interface SbcChallengeRepository extends JpaRepository<SbcChallenge, Integer> {

    boolean existsByChallengeId(Integer challengeId);

    List<SbcChallenge> findBySet_SetId(Integer setId);

    List<SbcChallenge> findByExpiresAtGreaterThan(Instant now);
}
