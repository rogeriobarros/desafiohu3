package com.hu3.huapp.web.rest.mapper;

import com.hu3.huapp.domain.*;
import com.hu3.huapp.web.rest.dto.TravelPackagesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TravelPackages and its DTO TravelPackagesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPackagesMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.values", target = "locationValues")
    @Mapping(source = "photos", target = "photos")
    @Mapping(source = "options", target = "options")
    TravelPackagesDTO travelPackagesToTravelPackagesDTO(TravelPackages travelPackages);

    List<TravelPackagesDTO> travelPackagesToTravelPackagesDTOs(List<TravelPackages> travelPackages);

    @Mapping(target = "options", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(source = "locationId", target = "location")
    TravelPackages travelPackagesDTOToTravelPackages(TravelPackagesDTO travelPackagesDTO);

    List<TravelPackages> travelPackagesDTOsToTravelPackages(List<TravelPackagesDTO> travelPackagesDTOs);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
