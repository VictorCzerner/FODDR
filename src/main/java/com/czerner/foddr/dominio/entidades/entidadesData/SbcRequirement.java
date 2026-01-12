package com.czerner.foddr.dominio.entidades.entidadesData;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sbc_requirements")
@Data
@NoArgsConstructor
public class SbcRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private SbcChallenge challenge;

    @Column(nullable = false)
    private String type;

    @Column(name = "eligibility_slot")
    private Integer eligibilitySlot;

    @Column(name = "eligibility_value")
    private Integer eligibilityValue;

    @Column(name = "nation_id")
    private Integer nationId;

    @Column(name = "league_id")
    private Integer leagueId;

    @Column(name = "club_id")
    private Integer clubId;

    public SbcRequirement(Long id, SbcChallenge challenge, String type, Integer eligibilitySlot,
            Integer eligibilityValue, Integer nationId, Integer leagueId, Integer clubId) {
        this.id = id;
        this.challenge = challenge;
        this.type = type;
        this.eligibilitySlot = eligibilitySlot;
        this.eligibilityValue = eligibilityValue;
        this.nationId = nationId;
        this.leagueId = leagueId;
        this.clubId = clubId;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return SbcChallenge return the challenge
     */
    public SbcChallenge getChallenge() {
        return challenge;
    }

    /**
     * @param challenge the challenge to set
     */
    public void setChallenge(SbcChallenge challenge) {
        this.challenge = challenge;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Integer return the eligibilitySlot
     */
    public Integer getEligibilitySlot() {
        return eligibilitySlot;
    }

    /**
     * @param eligibilitySlot the eligibilitySlot to set
     */
    public void setEligibilitySlot(Integer eligibilitySlot) {
        this.eligibilitySlot = eligibilitySlot;
    }

    /**
     * @return Integer return the eligibilityValue
     */
    public Integer getEligibilityValue() {
        return eligibilityValue;
    }

    /**
     * @param eligibilityValue the eligibilityValue to set
     */
    public void setEligibilityValue(Integer eligibilityValue) {
        this.eligibilityValue = eligibilityValue;
    }

    /**
     * @return Integer return the nationId
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * @param nationId the nationId to set
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    /**
     * @return Integer return the leagueId
     */
    public Integer getLeagueId() {
        return leagueId;
    }

    /**
     * @param leagueId the leagueId to set
     */
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    /**
     * @return Integer return the clubId
     */
    public Integer getClubId() {
        return clubId;
    }

    /**
     * @param clubId the clubId to set
     */
    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

}
