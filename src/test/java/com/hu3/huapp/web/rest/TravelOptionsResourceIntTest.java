package com.hu3.huapp.web.rest;

import com.hu3.huapp.Desafiohu3App;
import com.hu3.huapp.domain.TravelOptions;
import com.hu3.huapp.repository.TravelOptionsRepository;
import com.hu3.huapp.service.TravelOptionsService;
import com.hu3.huapp.web.rest.dto.TravelOptionsDTO;
import com.hu3.huapp.web.rest.mapper.TravelOptionsMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelOptionsResource REST controller.
 *
 * @see TravelOptionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Desafiohu3App.class)
@WebAppConfiguration
@IntegrationTest
public class TravelOptionsResourceIntTest {

    private static final String DEFAULT_TITLE_TRAVEL_OPTION = "AAAAA";
    private static final String UPDATED_TITLE_TRAVEL_OPTION = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_TRAVEL_OPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION_TRAVEL_OPTION = "BBBBB";

    private static final Integer DEFAULT_DAILY = 1;
    private static final Integer UPDATED_DAILY = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Inject
    private TravelOptionsRepository travelOptionsRepository;

    @Inject
    private TravelOptionsMapper travelOptionsMapper;

    @Inject
    private TravelOptionsService travelOptionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelOptionsMockMvc;

    private TravelOptions travelOptions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelOptionsResource travelOptionsResource = new TravelOptionsResource();
        ReflectionTestUtils.setField(travelOptionsResource, "travelOptionsService", travelOptionsService);
        ReflectionTestUtils.setField(travelOptionsResource, "travelOptionsMapper", travelOptionsMapper);
        this.restTravelOptionsMockMvc = MockMvcBuilders.standaloneSetup(travelOptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelOptions = new TravelOptions();
        travelOptions.setTitleTravelOption(DEFAULT_TITLE_TRAVEL_OPTION);
        travelOptions.setDescriptionTravelOption(DEFAULT_DESCRIPTION_TRAVEL_OPTION);
        travelOptions.setDaily(DEFAULT_DAILY);
        travelOptions.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createTravelOptions() throws Exception {
        int databaseSizeBeforeCreate = travelOptionsRepository.findAll().size();

        // Create the TravelOptions
        TravelOptionsDTO travelOptionsDTO = travelOptionsMapper.travelOptionsToTravelOptionsDTO(travelOptions);

        restTravelOptionsMockMvc.perform(post("/api/travel-options")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelOptionsDTO)))
                .andExpect(status().isCreated());

        // Validate the TravelOptions in the database
        List<TravelOptions> travelOptions = travelOptionsRepository.findAll();
        assertThat(travelOptions).hasSize(databaseSizeBeforeCreate + 1);
        TravelOptions testTravelOptions = travelOptions.get(travelOptions.size() - 1);
        assertThat(testTravelOptions.getTitleTravelOption()).isEqualTo(DEFAULT_TITLE_TRAVEL_OPTION);
        assertThat(testTravelOptions.getDescriptionTravelOption()).isEqualTo(DEFAULT_DESCRIPTION_TRAVEL_OPTION);
        assertThat(testTravelOptions.getDaily()).isEqualTo(DEFAULT_DAILY);
        assertThat(testTravelOptions.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void checkTitleTravelOptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelOptionsRepository.findAll().size();
        // set the field null
        travelOptions.setTitleTravelOption(null);

        // Create the TravelOptions, which fails.
        TravelOptionsDTO travelOptionsDTO = travelOptionsMapper.travelOptionsToTravelOptionsDTO(travelOptions);

        restTravelOptionsMockMvc.perform(post("/api/travel-options")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelOptionsDTO)))
                .andExpect(status().isBadRequest());

        List<TravelOptions> travelOptions = travelOptionsRepository.findAll();
        assertThat(travelOptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTravelOptions() throws Exception {
        // Initialize the database
        travelOptionsRepository.saveAndFlush(travelOptions);

        // Get all the travelOptions
        restTravelOptionsMockMvc.perform(get("/api/travel-options?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelOptions.getId().intValue())))
                .andExpect(jsonPath("$.[*].titleTravelOption").value(hasItem(DEFAULT_TITLE_TRAVEL_OPTION.toString())))
                .andExpect(jsonPath("$.[*].descriptionTravelOption").value(hasItem(DEFAULT_DESCRIPTION_TRAVEL_OPTION.toString())))
                .andExpect(jsonPath("$.[*].daily").value(hasItem(DEFAULT_DAILY)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getTravelOptions() throws Exception {
        // Initialize the database
        travelOptionsRepository.saveAndFlush(travelOptions);

        // Get the travelOptions
        restTravelOptionsMockMvc.perform(get("/api/travel-options/{id}", travelOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelOptions.getId().intValue()))
            .andExpect(jsonPath("$.titleTravelOption").value(DEFAULT_TITLE_TRAVEL_OPTION.toString()))
            .andExpect(jsonPath("$.descriptionTravelOption").value(DEFAULT_DESCRIPTION_TRAVEL_OPTION.toString()))
            .andExpect(jsonPath("$.daily").value(DEFAULT_DAILY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelOptions() throws Exception {
        // Get the travelOptions
        restTravelOptionsMockMvc.perform(get("/api/travel-options/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelOptions() throws Exception {
        // Initialize the database
        travelOptionsRepository.saveAndFlush(travelOptions);
        int databaseSizeBeforeUpdate = travelOptionsRepository.findAll().size();

        // Update the travelOptions
        TravelOptions updatedTravelOptions = new TravelOptions();
        updatedTravelOptions.setId(travelOptions.getId());
        updatedTravelOptions.setTitleTravelOption(UPDATED_TITLE_TRAVEL_OPTION);
        updatedTravelOptions.setDescriptionTravelOption(UPDATED_DESCRIPTION_TRAVEL_OPTION);
        updatedTravelOptions.setDaily(UPDATED_DAILY);
        updatedTravelOptions.setPrice(UPDATED_PRICE);
        TravelOptionsDTO travelOptionsDTO = travelOptionsMapper.travelOptionsToTravelOptionsDTO(updatedTravelOptions);

        restTravelOptionsMockMvc.perform(put("/api/travel-options")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelOptionsDTO)))
                .andExpect(status().isOk());

        // Validate the TravelOptions in the database
        List<TravelOptions> travelOptions = travelOptionsRepository.findAll();
        assertThat(travelOptions).hasSize(databaseSizeBeforeUpdate);
        TravelOptions testTravelOptions = travelOptions.get(travelOptions.size() - 1);
        assertThat(testTravelOptions.getTitleTravelOption()).isEqualTo(UPDATED_TITLE_TRAVEL_OPTION);
        assertThat(testTravelOptions.getDescriptionTravelOption()).isEqualTo(UPDATED_DESCRIPTION_TRAVEL_OPTION);
        assertThat(testTravelOptions.getDaily()).isEqualTo(UPDATED_DAILY);
        assertThat(testTravelOptions.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteTravelOptions() throws Exception {
        // Initialize the database
        travelOptionsRepository.saveAndFlush(travelOptions);
        int databaseSizeBeforeDelete = travelOptionsRepository.findAll().size();

        // Get the travelOptions
        restTravelOptionsMockMvc.perform(delete("/api/travel-options/{id}", travelOptions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelOptions> travelOptions = travelOptionsRepository.findAll();
        assertThat(travelOptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
