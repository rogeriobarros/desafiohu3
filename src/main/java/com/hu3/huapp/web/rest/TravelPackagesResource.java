package com.hu3.huapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hu3.huapp.domain.TravelPackages;
import com.hu3.huapp.service.TravelPackagesService;
import com.hu3.huapp.web.rest.util.HeaderUtil;
import com.hu3.huapp.web.rest.dto.TravelPackagesDTO;
import com.hu3.huapp.web.rest.mapper.TravelPackagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TravelPackages.
 */
@RestController
@RequestMapping("/api")
public class TravelPackagesResource {

    private final Logger log = LoggerFactory.getLogger(TravelPackagesResource.class);
        
    @Inject
    private TravelPackagesService travelPackagesService;
    
    @Inject
    private TravelPackagesMapper travelPackagesMapper;
    
    /**
     * POST  /travel-packages : Create a new travelPackages.
     *
     * @param travelPackagesDTO the travelPackagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travelPackagesDTO, or with status 400 (Bad Request) if the travelPackages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-packages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelPackagesDTO> createTravelPackages(@Valid @RequestBody TravelPackagesDTO travelPackagesDTO) throws URISyntaxException {
        log.debug("REST request to save TravelPackages : {}", travelPackagesDTO);
        if (travelPackagesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelPackages", "idexists", "A new travelPackages cannot already have an ID")).body(null);
        }
        TravelPackagesDTO result = travelPackagesService.save(travelPackagesDTO);
        return ResponseEntity.created(new URI("/api/travel-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("travelPackages", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travel-packages : Updates an existing travelPackages.
     *
     * @param travelPackagesDTO the travelPackagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travelPackagesDTO,
     * or with status 400 (Bad Request) if the travelPackagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the travelPackagesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-packages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelPackagesDTO> updateTravelPackages(@Valid @RequestBody TravelPackagesDTO travelPackagesDTO) throws URISyntaxException {
        log.debug("REST request to update TravelPackages : {}", travelPackagesDTO);
        if (travelPackagesDTO.getId() == null) {
            return createTravelPackages(travelPackagesDTO);
        }
        TravelPackagesDTO result = travelPackagesService.save(travelPackagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("travelPackages", travelPackagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travel-packages : get all the travelPackages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of travelPackages in body
     */
    @RequestMapping(value = "/travel-packages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<TravelPackagesDTO> getAllTravelPackages() {
        log.debug("REST request to get all TravelPackages");
        return travelPackagesService.findAll();
    }

    /**
     * GET  /travel-packages/:id : get the "id" travelPackages.
     *
     * @param id the id of the travelPackagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the travelPackagesDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/travel-packages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelPackagesDTO> getTravelPackages(@PathVariable Long id) {
        log.debug("REST request to get TravelPackages : {}", id);
        TravelPackagesDTO travelPackagesDTO = travelPackagesService.findOne(id);
        return Optional.ofNullable(travelPackagesDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travel-packages/:id : delete the "id" travelPackages.
     *
     * @param id the id of the travelPackagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/travel-packages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTravelPackages(@PathVariable Long id) {
        log.debug("REST request to delete TravelPackages : {}", id);
        travelPackagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelPackages", id.toString())).build();
    }

}
