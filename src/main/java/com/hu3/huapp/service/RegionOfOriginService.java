package com.hu3.huapp.service;

import com.hu3.huapp.domain.RegionOfOrigin;
import com.hu3.huapp.web.rest.dto.RegionOfOriginDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing RegionOfOrigin.
 */
public interface RegionOfOriginService {

    /**
     * Save a regionOfOrigin.
     * 
     * @param regionOfOriginDTO the entity to save
     * @return the persisted entity
     */
    RegionOfOriginDTO save(RegionOfOriginDTO regionOfOriginDTO);

    /**
     *  Get all the regionOfOrigins.
     *  
     *  @return the list of entities
     */
    List<RegionOfOriginDTO> findAll();

    /**
     *  Get the "id" regionOfOrigin.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    RegionOfOriginDTO findOne(Long id);

    /**
     *  Delete the "id" regionOfOrigin.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
