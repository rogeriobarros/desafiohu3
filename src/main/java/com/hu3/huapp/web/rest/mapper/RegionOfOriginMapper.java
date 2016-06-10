package com.hu3.huapp.web.rest.mapper;

import com.hu3.huapp.domain.*;
import com.hu3.huapp.web.rest.dto.RegionOfOriginDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RegionOfOrigin and its DTO RegionOfOriginDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegionOfOriginMapper {

    RegionOfOriginDTO regionOfOriginToRegionOfOriginDTO(RegionOfOrigin regionOfOrigin);

    List<RegionOfOriginDTO> regionOfOriginsToRegionOfOriginDTOs(List<RegionOfOrigin> regionOfOrigins);
    
    @Mapping(target = "option", ignore = true)
    RegionOfOrigin regionOfOriginDTOToRegionOfOrigin(RegionOfOriginDTO regionOfOriginDTO);

    List<RegionOfOrigin> regionOfOriginDTOsToRegionOfOrigins(List<RegionOfOriginDTO> regionOfOriginDTOs);
}
