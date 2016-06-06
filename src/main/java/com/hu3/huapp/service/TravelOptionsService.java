package com.hu3.huapp.service;

import com.hu3.huapp.domain.TravelOptions;
import com.hu3.huapp.web.rest.dto.TravelOptionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TravelOptions.
 */
public interface TravelOptionsService {

    /**
     * Save a travelOptions.
     * 
     * @param travelOptionsDTO the entity to save
     * @return the persisted entity
     */
    TravelOptionsDTO save(TravelOptionsDTO travelOptionsDTO);

    /**
     *  Get all the travelOptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TravelOptions> findAll(Pageable pageable);

    /**
     *  Get the "id" travelOptions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TravelOptionsDTO findOne(Long id);

    /**
     *  Delete the "id" travelOptions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
