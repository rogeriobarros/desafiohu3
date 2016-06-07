package com.hu3.huapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String stateProvince;

    @NotNull
    private String country;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;

        if ( ! Objects.equals(id, locationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + id +
            ", stateProvince='" + stateProvince + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
