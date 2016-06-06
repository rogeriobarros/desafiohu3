package com.hu3.huapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hu3.huapp.domain.TravelOptions;
import com.hu3.huapp.service.TravelOptionsService;
import com.hu3.huapp.web.rest.util.HeaderUtil;
import com.hu3.huapp.web.rest.util.PaginationUtil;
import com.hu3.huapp.web.rest.dto.TravelOptionsDTO;
import com.hu3.huapp.web.rest.mapper.TravelOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing TravelOptions.
 */
@RestController
@RequestMapping("/api")
public class TravelOptionsResource {

    private final Logger log = LoggerFactory.getLogger(TravelOptionsResource.class);
        
    @Inject
    private TravelOptionsService travelOptionsService;
    
    @Inject
    private TravelOptionsMapper travelOptionsMapper;
    
    /**
     * POST  /travel-options : Create a new travelOptions.
     *
     * @param travelOptionsDTO the travelOptionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travelOptionsDTO, or with status 400 (Bad Request) if the travelOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-options",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelOptionsDTO> createTravelOptions(@Valid @RequestBody TravelOptionsDTO travelOptionsDTO) throws URISyntaxException {
        log.debug("REST request to save TravelOptions : {}", travelOptionsDTO);
        if (travelOptionsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelOptions", "idexists", "A new travelOptions cannot already have an ID")).body(null);
        }
        TravelOptionsDTO result = travelOptionsService.save(travelOptionsDTO);
        return ResponseEntity.created(new URI("/api/travel-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("travelOptions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travel-options : Updates an existing travelOptions.
     *
     * @param travelOptionsDTO the travelOptionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travelOptionsDTO,
     * or with status 400 (Bad Request) if the travelOptionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the travelOptionsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-options",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelOptionsDTO> updateTravelOptions(@Valid @RequestBody TravelOptionsDTO travelOptionsDTO) throws URISyntaxException {
        log.debug("REST request to update TravelOptions : {}", travelOptionsDTO);
        if (travelOptionsDTO.getId() == null) {
            return createTravelOptions(travelOptionsDTO);
        }
        TravelOptionsDTO result = travelOptionsService.save(travelOptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("travelOptions", travelOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travel-options : get all the travelOptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of travelOptions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/travel-options",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TravelOptionsDTO>> getAllTravelOptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TravelOptions");
        Page<TravelOptions> page = travelOptionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/travel-options");
        return new ResponseEntity<>(travelOptionsMapper.travelOptionsToTravelOptionsDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /travel-options/:id : get the "id" travelOptions.
     *
     * @param id the id of the travelOptionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the travelOptionsDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/travel-options/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelOptionsDTO> getTravelOptions(@PathVariable Long id) {
        log.debug("REST request to get TravelOptions : {}", id);
        TravelOptionsDTO travelOptionsDTO = travelOptionsService.findOne(id);
        return Optional.ofNullable(travelOptionsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travel-options/:id : delete the "id" travelOptions.
     *
     * @param id the id of the travelOptionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/travel-options/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTravelOptions(@PathVariable Long id) {
        log.debug("REST request to delete TravelOptions : {}", id);
        travelOptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelOptions", id.toString())).build();
    }

}
