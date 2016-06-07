package com.hu3.huapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hu3.huapp.domain.RegionOfOrigin;
import com.hu3.huapp.service.RegionOfOriginService;
import com.hu3.huapp.web.rest.util.HeaderUtil;
import com.hu3.huapp.web.rest.dto.RegionOfOriginDTO;
import com.hu3.huapp.web.rest.mapper.RegionOfOriginMapper;
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
 * REST controller for managing RegionOfOrigin.
 */
@RestController
@RequestMapping("/api")
public class RegionOfOriginResource {

    private final Logger log = LoggerFactory.getLogger(RegionOfOriginResource.class);
        
    @Inject
    private RegionOfOriginService regionOfOriginService;
    
    @Inject
    private RegionOfOriginMapper regionOfOriginMapper;
    
    /**
     * POST  /region-of-origins : Create a new regionOfOrigin.
     *
     * @param regionOfOriginDTO the regionOfOriginDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regionOfOriginDTO, or with status 400 (Bad Request) if the regionOfOrigin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/region-of-origins",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegionOfOriginDTO> createRegionOfOrigin(@Valid @RequestBody RegionOfOriginDTO regionOfOriginDTO) throws URISyntaxException {
        log.debug("REST request to save RegionOfOrigin : {}", regionOfOriginDTO);
        if (regionOfOriginDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("regionOfOrigin", "idexists", "A new regionOfOrigin cannot already have an ID")).body(null);
        }
        RegionOfOriginDTO result = regionOfOriginService.save(regionOfOriginDTO);
        return ResponseEntity.created(new URI("/api/region-of-origins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("regionOfOrigin", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /region-of-origins : Updates an existing regionOfOrigin.
     *
     * @param regionOfOriginDTO the regionOfOriginDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regionOfOriginDTO,
     * or with status 400 (Bad Request) if the regionOfOriginDTO is not valid,
     * or with status 500 (Internal Server Error) if the regionOfOriginDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/region-of-origins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegionOfOriginDTO> updateRegionOfOrigin(@Valid @RequestBody RegionOfOriginDTO regionOfOriginDTO) throws URISyntaxException {
        log.debug("REST request to update RegionOfOrigin : {}", regionOfOriginDTO);
        if (regionOfOriginDTO.getId() == null) {
            return createRegionOfOrigin(regionOfOriginDTO);
        }
        RegionOfOriginDTO result = regionOfOriginService.save(regionOfOriginDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("regionOfOrigin", regionOfOriginDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /region-of-origins : get all the regionOfOrigins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regionOfOrigins in body
     */
    @RequestMapping(value = "/region-of-origins",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<RegionOfOriginDTO> getAllRegionOfOrigins() {
        log.debug("REST request to get all RegionOfOrigins");
        return regionOfOriginService.findAll();
    }

    /**
     * GET  /region-of-origins/:id : get the "id" regionOfOrigin.
     *
     * @param id the id of the regionOfOriginDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regionOfOriginDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/region-of-origins/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegionOfOriginDTO> getRegionOfOrigin(@PathVariable Long id) {
        log.debug("REST request to get RegionOfOrigin : {}", id);
        RegionOfOriginDTO regionOfOriginDTO = regionOfOriginService.findOne(id);
        return Optional.ofNullable(regionOfOriginDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /region-of-origins/:id : delete the "id" regionOfOrigin.
     *
     * @param id the id of the regionOfOriginDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/region-of-origins/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRegionOfOrigin(@PathVariable Long id) {
        log.debug("REST request to delete RegionOfOrigin : {}", id);
        regionOfOriginService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("regionOfOrigin", id.toString())).build();
    }

}
