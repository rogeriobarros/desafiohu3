package com.hu3.huapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "id_travel_package")
    private Long id;

    @NotNull
    @Column(name = "title_travel_package", nullable = false)
    private String titleTravelPackage;

    @Column(name = "description_travel_package")
    private String descriptionTravelPackage;

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TravelOptions> options = new HashSet<>();

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<TravelOptions> getOptions() {
        return options;
    }

    public void setOptions(Set<TravelOptions> travelOptions) {
        this.options = travelOptions;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
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
            ", titleTravelPackage='" + titleTravelPackage + "'" +
            ", descriptionTravelPackage='" + descriptionTravelPackage + "'" +
            '}';
    }
}
