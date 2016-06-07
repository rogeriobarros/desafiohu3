package com.hu3.huapp.service.impl;

import com.hu3.huapp.service.RegionOfOriginService;
import com.hu3.huapp.domain.RegionOfOrigin;
import com.hu3.huapp.repository.RegionOfOriginRepository;
import com.hu3.huapp.web.rest.dto.RegionOfOriginDTO;
import com.hu3.huapp.web.rest.mapper.RegionOfOriginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RegionOfOrigin.
 */
@Service
@Transactional
public class RegionOfOriginServiceImpl implements RegionOfOriginService{

    private final Logger log = LoggerFactory.getLogger(RegionOfOriginServiceImpl.class);
    
    @Inject
    private RegionOfOriginRepository regionOfOriginRepository;
    
    @Inject
    private RegionOfOriginMapper regionOfOriginMapper;
    
    /**
     * Save a regionOfOrigin.
     * 
     * @param regionOfOriginDTO the entity to save
     * @return the persisted entity
     */
    public RegionOfOriginDTO save(RegionOfOriginDTO regionOfOriginDTO) {
        log.debug("Request to save RegionOfOrigin : {}", regionOfOriginDTO);
        RegionOfOrigin regionOfOrigin = regionOfOriginMapper.regionOfOriginDTOToRegionOfOrigin(regionOfOriginDTO);
        regionOfOrigin = regionOfOriginRepository.save(regionOfOrigin);
        RegionOfOriginDTO result = regionOfOriginMapper.regionOfOriginToRegionOfOriginDTO(regionOfOrigin);
        return result;
    }

    /**
     *  Get all the regionOfOrigins.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<RegionOfOriginDTO> findAll() {
        log.debug("Request to get all RegionOfOrigins");
        List<RegionOfOriginDTO> result = regionOfOriginRepository.findAll().stream()
            .map(regionOfOriginMapper::regionOfOriginToRegionOfOriginDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one regionOfOrigin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RegionOfOriginDTO findOne(Long id) {
        log.debug("Request to get RegionOfOrigin : {}", id);
        RegionOfOrigin regionOfOrigin = regionOfOriginRepository.findOne(id);
        RegionOfOriginDTO regionOfOriginDTO = regionOfOriginMapper.regionOfOriginToRegionOfOriginDTO(regionOfOrigin);
        return regionOfOriginDTO;
    }

    /**
     *  Delete the  regionOfOrigin by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegionOfOrigin : {}", id);
        regionOfOriginRepository.delete(id);
    }
}
