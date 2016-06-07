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

    TravelPackagesDTO travelPackagesToTravelPackagesDTO(TravelPackages travelPackages);

    List<TravelPackagesDTO> travelPackagesToTravelPackagesDTOs(List<TravelPackages> travelPackages);

    @Mapping(target = "options", ignore = true)
    @Mapping(target = "photos", ignore = true)
    TravelPackages travelPackagesDTOToTravelPackages(TravelPackagesDTO travelPackagesDTO);

    List<TravelPackages> travelPackagesDTOsToTravelPackages(List<TravelPackagesDTO> travelPackagesDTOs);
}
