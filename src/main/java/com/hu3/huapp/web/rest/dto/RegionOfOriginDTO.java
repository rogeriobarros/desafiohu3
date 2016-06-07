package com.hu3.huapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the RegionOfOrigin entity.
 */
public class RegionOfOriginDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameRegion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNameRegion() {
        return nameRegion;
    }

    public void setNameRegion(String nameRegion) {
        this.nameRegion = nameRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegionOfOriginDTO regionOfOriginDTO = (RegionOfOriginDTO) o;

        if ( ! Objects.equals(id, regionOfOriginDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegionOfOriginDTO{" +
            "id=" + id +
            ", nameRegion='" + nameRegion + "'" +
            '}';
    }
}
