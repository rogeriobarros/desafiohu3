package com.hu3.huapp.service;

import com.hu3.huapp.domain.Photo;
import com.hu3.huapp.web.rest.dto.PhotoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Photo.
 */
public interface PhotoService {

    /**
     * Save a photo.
     * 
     * @param photoDTO the entity to save
     * @return the persisted entity
     */
    PhotoDTO save(PhotoDTO photoDTO);

    /**
     *  Get all the photos.
     *  
     *  @return the list of entities
     */
    List<PhotoDTO> findAll();
    
    /**
     *  Get all the photos.
     *  
     *  @return the list of entities
     */
    List<PhotoDTO> findAllTravelPackageId(Long travelPackageId);

    /**
     *  Get the "id" photo.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    PhotoDTO findOne(Long id);

    /**
     *  Delete the "id" photo.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
