package com.czerner.foddr.aplicação.Responses;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcChallenge;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcRequirement;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;

public class SbcSetResponse {

    private final Integer setId;
    private final String name;
    private final Long expiresAt;
    private final String imageId;
    private final Integer categoryId;
    private final String categoryName;
    private final List<ChallengeResponse> challenges;

    public SbcSetResponse(SbcSet set) {
        this.setId = set.getSetId();
        this.name = set.getName();
        this.expiresAt = toEpochSeconds(set.getExpiresAt());
        this.imageId = set.getImageId();
        this.categoryId = set.getCategory() != null ? set.getCategory().getId() : null;
        this.categoryName = set.getCategory() != null ? set.getCategory().getName() : null;
        this.challenges = mapChallenges(set.getChallenges());
    }

    private List<ChallengeResponse> mapChallenges(List<SbcChallenge> entidades) {
        if (entidades == null || entidades.isEmpty()) {
            return Collections.emptyList();
        }
        return entidades.stream()
                .map(ChallengeResponse::new)
                .collect(Collectors.toList());
    }

    public Integer getSetId() {
        return setId;
    }

    public String getName() {
        return name;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public String getImageId() {
        return imageId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<ChallengeResponse> getChallenges() {
        return challenges;
    }

    public static class ChallengeResponse {

        private final Integer challengeId;
        private final String name;
        private final String formation;
        private final Long expiresAt;
        private final Boolean repeatable;
        private final List<RequirementResponse> requirements;

        ChallengeResponse(SbcChallenge challenge) {
            this.challengeId = challenge.getChallengeId();
            this.name = challenge.getName();
            this.formation = challenge.getFormation();
            this.expiresAt = toEpochSeconds(challenge.getExpiresAt());
            this.repeatable = challenge.getRepeatable();
            this.requirements = mapRequirements(challenge.getRequirements());
        }

        private List<RequirementResponse> mapRequirements(List<SbcRequirement> entidades) {
            if (entidades == null || entidades.isEmpty()) {
                return Collections.emptyList();
            }
            return entidades.stream()
                    .map(RequirementResponse::new)
                    .collect(Collectors.toList());
        }

        public Integer getChallengeId() {
            return challengeId;
        }

        public String getName() {
            return name;
        }

        public String getFormation() {
            return formation;
        }

        public Long getExpiresAt() {
            return expiresAt;
        }

        public Boolean getRepeatable() {
            return repeatable;
        }

        public List<RequirementResponse> getRequirements() {
            return requirements;
        }
    }

    public static class RequirementResponse {

        private final Long requirementId;
        private final String type;
        private final Integer eligibilitySlot;
        private final Integer eligibilityValue;
        private final Integer nationId;
        private final Integer leagueId;
        private final Integer clubId;

        RequirementResponse(SbcRequirement requirement) {
            this.requirementId = requirement.getId();
            this.type = requirement.getType();
            this.eligibilitySlot = requirement.getEligibilitySlot();
            this.eligibilityValue = requirement.getEligibilityValue();
            this.nationId = requirement.getNationId();
            this.leagueId = requirement.getLeagueId();
            this.clubId = requirement.getClubId();
        }

        public Long getRequirementId() {
            return requirementId;
        }

        public String getType() {
            return type;
        }

        public Integer getEligibilitySlot() {
            return eligibilitySlot;
        }

        public Integer getEligibilityValue() {
            return eligibilityValue;
        }

        public Integer getNationId() {
            return nationId;
        }

        public Integer getLeagueId() {
            return leagueId;
        }

        public Integer getClubId() {
            return clubId;
        }
    }

    private static Long toEpochSeconds(Instant instant) {
        return instant != null ? instant.getEpochSecond() : null;
    }
}
