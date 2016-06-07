package com.hu3.huapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TravelOptions.
 */
@Entity
@Table(name = "travel_options")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TravelOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_travel_options")
    private Long id;

    @NotNull
    @Column(name = "title_travel_option", nullable = false)
    private String titleTravelOption;

    @Column(name = "description_travel_option")
    private String descriptionTravelOption;

    @Column(name = "daily")
    private Integer daily;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "option")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RegionOfOrigin> froms = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_travel_package")
    private TravelPackages packages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleTravelOption() {
        return titleTravelOption;
    }

    public void setTitleTravelOption(String titleTravelOption) {
        this.titleTravelOption = titleTravelOption;
    }

    public String getDescriptionTravelOption() {
        return descriptionTravelOption;
    }

    public void setDescriptionTravelOption(String descriptionTravelOption) {
        this.descriptionTravelOption = descriptionTravelOption;
    }

    public Integer getDaily() {
        return daily;
    }

    public void setDaily(Integer daily) {
        this.daily = daily;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<RegionOfOrigin> getFroms() {
        return froms;
    }

    public void setFroms(Set<RegionOfOrigin> regionOfOrigins) {
        this.froms = regionOfOrigins;
    }

    public TravelPackages getPackages() {
		return packages;
	}

	public void setPackages(TravelPackages packages) {
		this.packages = packages;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TravelOptions travelOptions = (TravelOptions) o;
        if(travelOptions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelOptions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelOptions{" +
            "id=" + id +
            ", titleTravelOption='" + titleTravelOption + "'" +
            ", descriptionTravelOption='" + descriptionTravelOption + "'" +
            ", daily='" + daily + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
