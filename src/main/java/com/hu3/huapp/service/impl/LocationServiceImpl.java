package com.hu3.huapp.service.impl;

import com.hu3.huapp.service.LocationService;
import com.hu3.huapp.domain.Location;
import com.hu3.huapp.repository.LocationRepository;
import com.hu3.huapp.web.rest.dto.LocationDTO;
import com.hu3.huapp.web.rest.mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    @Inject
    private LocationRepository locationRepository;
    
    @Inject
    private LocationMapper locationMapper;
    
    /**
     * Save a location.
     * 
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return result;
    }

    /**
     *  Get all the locations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LocationDTO> findAll() {
        log.debug("Request to get all Locations");
        List<LocationDTO> result = locationRepository.findAll().stream()
            .map(locationMapper::locationToLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one location by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LocationDTO findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return locationDTO;
    }

    /**
     *  Delete the  location by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
