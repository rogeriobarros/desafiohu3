package com.hu3.huapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the TravelOptions entity.
 */
public class TravelOptionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idTravelOption;

    @NotNull
    private String titleTravelOption;

    private String descriptionTravelOption;

    private Integer daily;

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

        TravelOptionsDTO travelOptionsDTO = (TravelOptionsDTO) o;

        if ( ! Objects.equals(id, travelOptionsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelOptionsDTO{" +
            "id=" + id +
            ", idTravelOption='" + idTravelOption + "'" +
            ", titleTravelOption='" + titleTravelOption + "'" +
            ", descriptionTravelOption='" + descriptionTravelOption + "'" +
            ", daily='" + daily + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
