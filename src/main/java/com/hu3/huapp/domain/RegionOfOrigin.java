package com.hu3.huapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RegionOfOrigin.
 */
@Entity
@Table(name = "region_of_origin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegionOfOrigin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name_region", nullable = false)
    private String nameRegion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_travel_options")
    private TravelOptions option;

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

    public TravelOptions getOption() {
		return option;
	}

	public void setOption(TravelOptions option) {
		this.option = option;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegionOfOrigin regionOfOrigin = (RegionOfOrigin) o;
        if(regionOfOrigin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, regionOfOrigin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegionOfOrigin{" +
            "id=" + id +
            ", nameRegion='" + nameRegion + "'" +
            '}';
    }
}
