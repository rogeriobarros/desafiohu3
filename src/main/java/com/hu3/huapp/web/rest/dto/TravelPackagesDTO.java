package com.hu3.huapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TravelPackages entity.
 */
public class TravelPackagesDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idTravelPackage;

    @NotNull
    private String titleTravelPackage;

    private String descriptionTravelPackage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdTravelPackage() {
        return idTravelPackage;
    }

    public void setIdTravelPackage(Long idTravelPackage) {
        this.idTravelPackage = idTravelPackage;
    }
    public String getTitleTravelPackage() {
        return titleTravelPackage;
    }

    public void setTitleTravelPackage(String titleTravelPackage) {
        this.titleTravelPackage = titleTravelPackage;
    }
    public String getDescriptionTravelPackage() {
        return descriptionTravelPackage;
    }

    public void setDescriptionTravelPackage(String descriptionTravelPackage) {
        this.descriptionTravelPackage = descriptionTravelPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TravelPackagesDTO travelPackagesDTO = (TravelPackagesDTO) o;

        if ( ! Objects.equals(id, travelPackagesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelPackagesDTO{" +
            "id=" + id +
            ", idTravelPackage='" + idTravelPackage + "'" +
            ", titleTravelPackage='" + titleTravelPackage + "'" +
            ", descriptionTravelPackage='" + descriptionTravelPackage + "'" +
            '}';
    }
}
