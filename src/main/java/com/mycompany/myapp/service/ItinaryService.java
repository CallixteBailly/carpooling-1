package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Itinary;
import com.mycompany.myapp.repository.ItinaryRepository;
import com.mycompany.myapp.web.rest.dto.ItinaryDTO;
import com.mycompany.myapp.web.rest.mapper.ItinaryMapper;
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
 * Service Implementation for managing Itinary.
 */
@Service
@Transactional
public class ItinaryService {

    private final Logger log = LoggerFactory.getLogger(ItinaryService.class);
    
    @Inject
    private ItinaryRepository itinaryRepository;
    
    @Inject
    private ItinaryMapper itinaryMapper;
    
    /**
     * Save a itinary.
     * 
     * @param itinaryDTO the entity to save
     * @return the persisted entity
     */
    public ItinaryDTO save(ItinaryDTO itinaryDTO) {
        log.debug("Request to save Itinary : {}", itinaryDTO);
        Itinary itinary = itinaryMapper.itinaryDTOToItinary(itinaryDTO);
        itinary = itinaryRepository.save(itinary);
        ItinaryDTO result = itinaryMapper.itinaryToItinaryDTO(itinary);
        return result;
    }

    /**
     *  Get all the itinaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Itinary> findAll(Pageable pageable) {
        log.debug("Request to get all Itinaries");
        Page<Itinary> result = itinaryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one itinary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ItinaryDTO findOne(Long id) {
        log.debug("Request to get Itinary : {}", id);
        Itinary itinary = itinaryRepository.findOneWithEagerRelationships(id);
        ItinaryDTO itinaryDTO = itinaryMapper.itinaryToItinaryDTO(itinary);
        return itinaryDTO;
    }

    /**
     *  Delete the  itinary by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Itinary : {}", id);
        itinaryRepository.delete(id);
    }
}
