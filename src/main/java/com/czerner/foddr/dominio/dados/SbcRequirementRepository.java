package com.czerner.foddr.dominio.dados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcRequirement;

@Repository
public interface SbcRequirementRepository extends JpaRepository<SbcRequirement, Long> {

    List<SbcRequirement> findByChallenge_ChallengeId(Integer challengeId);

    List<SbcRequirement> findByType(String type);

    boolean existsByChallenge_ChallengeIdAndType(
            Integer challengeId,
            String type
    );
}
