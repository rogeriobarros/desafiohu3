package com.hu3.huapp.service.impl;

import com.hu3.huapp.service.TravelPackagesService;
import com.hu3.huapp.domain.TravelPackages;
import com.hu3.huapp.repository.TravelPackagesRepository;
import com.hu3.huapp.web.rest.dto.TravelPackagesDTO;
import com.hu3.huapp.web.rest.mapper.TravelPackagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TravelPackages.
 */
@Service
@Transactional
public class TravelPackagesServiceImpl implements TravelPackagesService{

    private final Logger log = LoggerFactory.getLogger(TravelPackagesServiceImpl.class);
    
    @Inject
    private TravelPackagesRepository travelPackagesRepository;
    
    @Inject
    private TravelPackagesMapper travelPackagesMapper;
    
    /**
     * Save a travelPackages.
     * 
     * @param travelPackagesDTO the entity to save
     * @return the persisted entity
     */
    public TravelPackagesDTO save(TravelPackagesDTO travelPackagesDTO) {
        log.debug("Request to save TravelPackages : {}", travelPackagesDTO);
        TravelPackages travelPackages = travelPackagesMapper.travelPackagesDTOToTravelPackages(travelPackagesDTO);
        travelPackages = travelPackagesRepository.save(travelPackages);
        TravelPackagesDTO result = travelPackagesMapper.travelPackagesToTravelPackagesDTO(travelPackages);
        return result;
    }

    /**
     *  Get all the travelPackages.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TravelPackagesDTO> findAll() {
        log.debug("Request to get all TravelPackages");
        List<TravelPackagesDTO> result = travelPackagesRepository.findAll().stream()
            .map(travelPackagesMapper::travelPackagesToTravelPackagesDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one travelPackages by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelPackagesDTO findOne(Long id) {
        log.debug("Request to get TravelPackages : {}", id);
        TravelPackages travelPackages = travelPackagesRepository.findOne(id);
        TravelPackagesDTO travelPackagesDTO = travelPackagesMapper.travelPackagesToTravelPackagesDTO(travelPackages);
        return travelPackagesDTO;
    }

    /**
     *  Delete the  travelPackages by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelPackages : {}", id);
        travelPackagesRepository.delete(id);
    }
}
