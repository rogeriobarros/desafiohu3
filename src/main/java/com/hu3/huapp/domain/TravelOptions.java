package com.hu3.huapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private Long id;

    @NotNull
    @Column(name = "id_travel_option", nullable = false)
    private Long idTravelOption;

    @NotNull
    @Column(name = "title_travel_option", nullable = false)
    private String titleTravelOption;

    @Column(name = "description_travel_option")
    private String descriptionTravelOption;

    @Column(name = "daily")
    private Integer daily;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTravelOption() {
        return idTravelOption;
    }

    public void setIdTravelOption(Long idTravelOption) {
        this.idTravelOption = idTravelOption;
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
            ", idTravelOption='" + idTravelOption + "'" +
            ", titleTravelOption='" + titleTravelOption + "'" +
            ", descriptionTravelOption='" + descriptionTravelOption + "'" +
            ", daily='" + daily + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
