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
@Table(name = "sbc_sets")
@Data
@NoArgsConstructor
public class SbcSet {

    @Id
    @Column(name = "set_id")
    private Integer setId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SbcCategory category;

    @Column(nullable = false)
    private String name;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "image_id")
    private String imageId;

    @OneToMany(mappedBy = "set", fetch = FetchType.LAZY)
    private List<SbcChallenge> challenges;

    public SbcSet(Integer setId, SbcCategory category, String name, Instant expiresAt, String imageId,
            List<SbcChallenge> challenges) {
        this.setId = setId;
        this.category = category;
        this.name = name;
        this.expiresAt = expiresAt;
        this.imageId = imageId;
        this.challenges = challenges;
    }
    

    /**
     * @return Integer return the setId
     */
    public Integer getSetId() {
        return setId;
    }

    /**
     * @param setId the setId to set
     */
    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    /**
     * @return SbcCategory return the category
     */
    public SbcCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(SbcCategory category) {
        this.category = category;
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
     * @return String return the imageId
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * @param imageId the imageId to set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * @return List<SbcChallenge> return the challenges
     */
    public List<SbcChallenge> getChallenges() {
        return challenges;
    }

    /**
     * @param challenges the challenges to set
     */
    public void setChallenges(List<SbcChallenge> challenges) {
        this.challenges = challenges;
    }

}
