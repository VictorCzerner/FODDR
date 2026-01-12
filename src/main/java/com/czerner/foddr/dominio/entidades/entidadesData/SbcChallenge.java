package com.czerner.foddr.dominio.entidades.entidadesData;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sbc_challenges")
@Data
@NoArgsConstructor
public class SbcChallenge {

    @Id
    @Column(name = "challenge_id")
    private Integer challengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", nullable = false)
    private SbcSet set;

    @Column(nullable = false)
    private String name;

    private String formation;

    @Column(name = "expires_at")
    private Instant expiresAt;

    private Boolean repeatable;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY)
    private List<SbcRequirement> requirements;

    public SbcChallenge(Integer challengeId, SbcSet set, String name, String formation, Instant expiresAt,
            Boolean repeatable, List<SbcRequirement> requirements) {
        this.challengeId = challengeId;
        this.set = set;
        this.name = name;
        this.formation = formation;
        this.expiresAt = expiresAt;
        this.repeatable = repeatable;
        this.requirements = requirements;
    }

    /**
     * @return Integer return the challengeId
     */
    public Integer getChallengeId() {
        return challengeId;
    }

    /**
     * @param challengeId the challengeId to set
     */
    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    /**
     * @return SbcSet return the set
     */
    public SbcSet getSet() {
        return set;
    }

    /**
     * @param set the set to set
     */
    public void setSet(SbcSet set) {
        this.set = set;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the formation
     */
    public String getFormation() {
        return formation;
    }

    /**
     * @param formation the formation to set
     */
    public void setFormation(String formation) {
        this.formation = formation;
    }

    /**
     * @return Instant return the expiresAt
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * @param expiresAt the expiresAt to set
     */
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * @return Boolean return the repeatable
     */
    public Boolean isRepeatable() {
        return repeatable;
    }

    /**
     * @param repeatable the repeatable to set
     */
    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    /**
     * @return List<SbcRequirement> return the requirements
     */
    public List<SbcRequirement> getRequirements() {
        return requirements;
    }

    /**
     * @param requirements the requirements to set
     */
    public void setRequirements(List<SbcRequirement> requirements) {
        this.requirements = requirements;
    }

}
