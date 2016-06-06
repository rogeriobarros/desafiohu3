package com.hu3.huapp.service;

import com.hu3.huapp.domain.TravelPackages;
import com.hu3.huapp.web.rest.dto.TravelPackagesDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TravelPackages.
 */
public interface TravelPackagesService {

    /**
     * Save a travelPackages.
     * 
     * @param travelPackagesDTO the entity to save
     * @return the persisted entity
     */
    TravelPackagesDTO save(TravelPackagesDTO travelPackagesDTO);

    /**
     *  Get all the travelPackages.
     *  
     *  @return the list of entities
     */
    List<TravelPackagesDTO> findAll();

    /**
     *  Get the "id" travelPackages.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TravelPackagesDTO findOne(Long id);

    /**
     *  Delete the "id" travelPackages.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
