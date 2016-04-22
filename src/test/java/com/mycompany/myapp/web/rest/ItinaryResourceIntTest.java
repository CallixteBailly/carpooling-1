package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CarpoolingCsid2016App;
import com.mycompany.myapp.domain.Itinary;
import com.mycompany.myapp.repository.ItinaryRepository;
import com.mycompany.myapp.service.ItinaryService;
import com.mycompany.myapp.web.rest.dto.ItinaryDTO;
import com.mycompany.myapp.web.rest.mapper.ItinaryMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ItinaryResource REST controller.
 *
 * @see ItinaryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarpoolingCsid2016App.class)
@WebAppConfiguration
@IntegrationTest
public class ItinaryResourceIntTest {


    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PRICE_PER_SEAT = 1;
    private static final Integer UPDATED_PRICE_PER_SEAT = 2;

    @Inject
    private ItinaryRepository itinaryRepository;

    @Inject
    private ItinaryMapper itinaryMapper;

    @Inject
    private ItinaryService itinaryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restItinaryMockMvc;

    private Itinary itinary;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItinaryResource itinaryResource = new ItinaryResource();
        ReflectionTestUtils.setField(itinaryResource, "itinaryService", itinaryService);
        ReflectionTestUtils.setField(itinaryResource, "itinaryMapper", itinaryMapper);
        this.restItinaryMockMvc = MockMvcBuilders.standaloneSetup(itinaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        itinary = new Itinary();
        itinary.setStartDate(DEFAULT_START_DATE);
        itinary.setPricePerSeat(DEFAULT_PRICE_PER_SEAT);
    }

    @Test
    @Transactional
    public void createItinary() throws Exception {
        int databaseSizeBeforeCreate = itinaryRepository.findAll().size();

        // Create the Itinary
        ItinaryDTO itinaryDTO = itinaryMapper.itinaryToItinaryDTO(itinary);

        restItinaryMockMvc.perform(post("/api/itinaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itinaryDTO)))
                .andExpect(status().isCreated());

        // Validate the Itinary in the database
        List<Itinary> itinaries = itinaryRepository.findAll();
        assertThat(itinaries).hasSize(databaseSizeBeforeCreate + 1);
        Itinary testItinary = itinaries.get(itinaries.size() - 1);
        assertThat(testItinary.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testItinary.getPricePerSeat()).isEqualTo(DEFAULT_PRICE_PER_SEAT);
    }

    @Test
    @Transactional
    public void getAllItinaries() throws Exception {
        // Initialize the database
        itinaryRepository.saveAndFlush(itinary);

        // Get all the itinaries
        restItinaryMockMvc.perform(get("/api/itinaries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itinary.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].pricePerSeat").value(hasItem(DEFAULT_PRICE_PER_SEAT)));
    }

    @Test
    @Transactional
    public void getItinary() throws Exception {
        // Initialize the database
        itinaryRepository.saveAndFlush(itinary);

        // Get the itinary
        restItinaryMockMvc.perform(get("/api/itinaries/{id}", itinary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itinary.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.pricePerSeat").value(DEFAULT_PRICE_PER_SEAT));
    }

    @Test
    @Transactional
    public void getNonExistingItinary() throws Exception {
        // Get the itinary
        restItinaryMockMvc.perform(get("/api/itinaries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItinary() throws Exception {
        // Initialize the database
        itinaryRepository.saveAndFlush(itinary);
        int databaseSizeBeforeUpdate = itinaryRepository.findAll().size();

        // Update the itinary
        Itinary updatedItinary = new Itinary();
        updatedItinary.setId(itinary.getId());
        updatedItinary.setStartDate(UPDATED_START_DATE);
        updatedItinary.setPricePerSeat(UPDATED_PRICE_PER_SEAT);
        ItinaryDTO itinaryDTO = itinaryMapper.itinaryToItinaryDTO(updatedItinary);

        restItinaryMockMvc.perform(put("/api/itinaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itinaryDTO)))
                .andExpect(status().isOk());

        // Validate the Itinary in the database
        List<Itinary> itinaries = itinaryRepository.findAll();
        assertThat(itinaries).hasSize(databaseSizeBeforeUpdate);
        Itinary testItinary = itinaries.get(itinaries.size() - 1);
        assertThat(testItinary.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testItinary.getPricePerSeat()).isEqualTo(UPDATED_PRICE_PER_SEAT);
    }

    @Test
    @Transactional
    public void deleteItinary() throws Exception {
        // Initialize the database
        itinaryRepository.saveAndFlush(itinary);
        int databaseSizeBeforeDelete = itinaryRepository.findAll().size();

        // Get the itinary
        restItinaryMockMvc.perform(delete("/api/itinaries/{id}", itinary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itinary> itinaries = itinaryRepository.findAll();
        assertThat(itinaries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
