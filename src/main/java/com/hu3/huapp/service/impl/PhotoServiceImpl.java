package com.hu3.huapp.service.impl;

import com.hu3.huapp.service.PhotoService;
import com.hu3.huapp.domain.Photo;
import com.hu3.huapp.repository.PhotoRepository;
import com.hu3.huapp.web.rest.dto.PhotoDTO;
import com.hu3.huapp.web.rest.mapper.PhotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Photo.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService{

    private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);
    
    @Inject
    private PhotoRepository photoRepository;
    
    @Inject
    private PhotoMapper photoMapper;
    
    /**
     * Save a photo.
     * 
     * @param photoDTO the entity to save
     * @return the persisted entity
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.photoDTOToPhoto(photoDTO);
        photo = photoRepository.save(photo);
        PhotoDTO result = photoMapper.photoToPhotoDTO(photo);
        return result;
    }

    /**
     *  Get all the photos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PhotoDTO> findAll() {
        log.debug("Request to get all Photos");
        List<PhotoDTO> result = photoRepository.findAll().stream()
            .map(photoMapper::photoToPhotoDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one photo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PhotoDTO findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        Photo photo = photoRepository.findOne(id);
        PhotoDTO photoDTO = photoMapper.photoToPhotoDTO(photo);
        return photoDTO;
    }

    /**
     *  Delete the  photo by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.delete(id);
    }
}
