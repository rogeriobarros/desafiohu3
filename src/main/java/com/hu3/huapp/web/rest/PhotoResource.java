package com.hu3.huapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hu3.huapp.domain.Photo;
import com.hu3.huapp.service.PhotoService;
import com.hu3.huapp.web.rest.util.HeaderUtil;
import com.hu3.huapp.web.rest.dto.PhotoDTO;
import com.hu3.huapp.web.rest.mapper.PhotoMapper;
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
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private final Logger log = LoggerFactory.getLogger(PhotoResource.class);
        
    @Inject
    private PhotoService photoService;
    
    @Inject
    private PhotoMapper photoMapper;
    
    /**
     * POST  /photos : Create a new photo.
     *
     * @param photoDTO the photoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photoDTO, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/photos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhotoDTO> createPhoto(@Valid @RequestBody PhotoDTO photoDTO) throws URISyntaxException {
        log.debug("REST request to save Photo : {}", photoDTO);
        if (photoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("photo", "idexists", "A new photo cannot already have an ID")).body(null);
        }
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("photo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photoDTO the photoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photoDTO,
     * or with status 400 (Bad Request) if the photoDTO is not valid,
     * or with status 500 (Internal Server Error) if the photoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/photos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhotoDTO> updatePhoto(@Valid @RequestBody PhotoDTO photoDTO) throws URISyntaxException {
        log.debug("REST request to update Photo : {}", photoDTO);
        if (photoDTO.getId() == null) {
            return createPhoto(photoDTO);
        }
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("photo", photoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @RequestMapping(value = "/photos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PhotoDTO> getAllPhotos(@RequestParam(value = "travelPackageId", required = false) String travelPackageId) {
        log.debug("REST request to get all Photos");
        if(travelPackageId != null){
        	return photoService.findAllTravelPackageId( parseLong(travelPackageId) );
        }else{
        	return photoService.findAll();
        }
        
    }

    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/photos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhotoDTO> getPhoto(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        PhotoDTO photoDTO = photoService.findOne(id);
        return Optional.ofNullable(photoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/photos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        photoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("photo", id.toString())).build();
    }
    
    private Long parseLong(String value){
    	try {
			return Long.valueOf(value);
		} catch (Exception e) {
			log.error("Falha ao manipular parametro.");
		}
		return 0L;
    }

}
