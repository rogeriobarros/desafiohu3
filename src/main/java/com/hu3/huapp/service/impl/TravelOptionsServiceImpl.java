package com.hu3.huapp.service.impl;

import com.hu3.huapp.service.TravelOptionsService;
import com.hu3.huapp.domain.TravelOptions;
import com.hu3.huapp.repository.TravelOptionsRepository;
import com.hu3.huapp.web.rest.dto.TravelOptionsDTO;
import com.hu3.huapp.web.rest.mapper.TravelOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TravelOptions.
 */
@Service
@Transactional
public class TravelOptionsServiceImpl implements TravelOptionsService{

    private final Logger log = LoggerFactory.getLogger(TravelOptionsServiceImpl.class);
    
    @Inject
    private TravelOptionsRepository travelOptionsRepository;
    
    @Inject
    private TravelOptionsMapper travelOptionsMapper;
    
    /**
     * Save a travelOptions.
     * 
     * @param travelOptionsDTO the entity to save
     * @return the persisted entity
     */
    public TravelOptionsDTO save(TravelOptionsDTO travelOptionsDTO) {
        log.debug("Request to save TravelOptions : {}", travelOptionsDTO);
        TravelOptions travelOptions = travelOptionsMapper.travelOptionsDTOToTravelOptions(travelOptionsDTO);
        travelOptions = travelOptionsRepository.save(travelOptions);
        TravelOptionsDTO result = travelOptionsMapper.travelOptionsToTravelOptionsDTO(travelOptions);
        return result;
    }

    /**
     *  Get all the travelOptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TravelOptions> findAll(Pageable pageable) {
        log.debug("Request to get all TravelOptions");
        Page<TravelOptions> result = travelOptionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one travelOptions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelOptionsDTO findOne(Long id) {
        log.debug("Request to get TravelOptions : {}", id);
        TravelOptions travelOptions = travelOptionsRepository.findOne(id);
        TravelOptionsDTO travelOptionsDTO = travelOptionsMapper.travelOptionsToTravelOptionsDTO(travelOptions);
        return travelOptionsDTO;
    }

    /**
     *  Delete the  travelOptions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelOptions : {}", id);
        travelOptionsRepository.delete(id);
    }
}
