package com.hu3.huapp.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;

import com.hu3.huapp.domain.Photo;
import com.hu3.huapp.domain.TravelOptions;


/**
 * A DTO for the TravelPackages entity.
 */
public class TravelPackagesDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String titleTravelPackage;

    private String descriptionTravelPackage;

    private Long locationId;
    
    private String locationValues;
    
    private Set<Photo> photos;
    
    private Set<TravelOptions> options;
    
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationValues() {
		return locationValues;
	}

	public void setLocationValues(String locationValues) {
		this.locationValues = locationValues;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public Set<TravelOptions> getOptions() {
    	Set<TravelOptions> travelOptions = new TreeSet<TravelOptions>(new java.util.Comparator<TravelOptions>(){ 
    	    public int compare(TravelOptions a, TravelOptions b){
    	        return a.getPrice().compareTo(b.getPrice());
    	    } 
    	});
    	
    	travelOptions.addAll(options);
    	
        return travelOptions;
	}

	public void setOptions(Set<TravelOptions> options) {
		this.options = options;
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
            ", titleTravelPackage='" + titleTravelPackage + "'" +
            ", descriptionTravelPackage='" + descriptionTravelPackage + "'" +
            '}';
    }
}
