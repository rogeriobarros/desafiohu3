package com.hu3.huapp.web.rest.mapper;

import com.hu3.huapp.domain.*;
import com.hu3.huapp.web.rest.dto.TravelOptionsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TravelOptions and its DTO TravelOptionsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelOptionsMapper {

	@Mapping(source = "froms", target = "froms")
    TravelOptionsDTO travelOptionsToTravelOptionsDTO(TravelOptions travelOptions);

    List<TravelOptionsDTO> travelOptionsToTravelOptionsDTOs(List<TravelOptions> travelOptions);

    @Mapping(target = "froms", ignore = true)
    @Mapping(target = "packages", ignore = true)
    TravelOptions travelOptionsDTOToTravelOptions(TravelOptionsDTO travelOptionsDTO);

    List<TravelOptions> travelOptionsDTOsToTravelOptions(List<TravelOptionsDTO> travelOptionsDTOs);
}
