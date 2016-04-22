package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Itinary;
import com.mycompany.myapp.service.ItinaryService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.ItinaryDTO;
import com.mycompany.myapp.web.rest.mapper.ItinaryMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Itinary.
 */
@RestController
@RequestMapping("/api")
public class ItinaryResource {

    private final Logger log = LoggerFactory.getLogger(ItinaryResource.class);
        
    @Inject
    private ItinaryService itinaryService;
    
    @Inject
    private ItinaryMapper itinaryMapper;
    
    /**
     * POST  /itinaries : Create a new itinary.
     *
     * @param itinaryDTO the itinaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itinaryDTO, or with status 400 (Bad Request) if the itinary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/itinaries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItinaryDTO> createItinary(@RequestBody ItinaryDTO itinaryDTO) throws URISyntaxException {
        log.debug("REST request to save Itinary : {}", itinaryDTO);
        if (itinaryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("itinary", "idexists", "A new itinary cannot already have an ID")).body(null);
        }
        ItinaryDTO result = itinaryService.save(itinaryDTO);
        return ResponseEntity.created(new URI("/api/itinaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("itinary", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /itinaries : Updates an existing itinary.
     *
     * @param itinaryDTO the itinaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itinaryDTO,
     * or with status 400 (Bad Request) if the itinaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the itinaryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/itinaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItinaryDTO> updateItinary(@RequestBody ItinaryDTO itinaryDTO) throws URISyntaxException {
        log.debug("REST request to update Itinary : {}", itinaryDTO);
        if (itinaryDTO.getId() == null) {
            return createItinary(itinaryDTO);
        }
        ItinaryDTO result = itinaryService.save(itinaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("itinary", itinaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /itinaries : get all the itinaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of itinaries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/itinaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ItinaryDTO>> getAllItinaries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Itinaries");
        Page<Itinary> page = itinaryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/itinaries");
        return new ResponseEntity<>(itinaryMapper.itinariesToItinaryDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /itinaries/:id : get the "id" itinary.
     *
     * @param id the id of the itinaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itinaryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/itinaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItinaryDTO> getItinary(@PathVariable Long id) {
        log.debug("REST request to get Itinary : {}", id);
        ItinaryDTO itinaryDTO = itinaryService.findOne(id);
        return Optional.ofNullable(itinaryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /itinaries/:id : delete the "id" itinary.
     *
     * @param id the id of the itinaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/itinaries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteItinary(@PathVariable Long id) {
        log.debug("REST request to delete Itinary : {}", id);
        itinaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("itinary", id.toString())).build();
    }

}
