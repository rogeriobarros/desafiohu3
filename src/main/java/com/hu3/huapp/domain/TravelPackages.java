package com.hu3.huapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TravelPackages.
 */
@Entity
@Table(name = "travel_packages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TravelPackages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "id_travel_package", nullable = false)
    private Long idTravelPackage;

    @NotNull
    @Column(name = "title_travel_package", nullable = false)
    private String titleTravelPackage;

    @Column(name = "description_travel_package")
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
        TravelPackages travelPackages = (TravelPackages) o;
        if(travelPackages.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelPackages.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelPackages{" +
            "id=" + id +
            ", idTravelPackage='" + idTravelPackage + "'" +
            ", titleTravelPackage='" + titleTravelPackage + "'" +
            ", descriptionTravelPackage='" + descriptionTravelPackage + "'" +
            '}';
    }
}
