package com.hu3.huapp.web.rest;

import com.hu3.huapp.Desafiohu3App;
import com.hu3.huapp.domain.TravelPackages;
import com.hu3.huapp.repository.TravelPackagesRepository;
import com.hu3.huapp.service.TravelPackagesService;
import com.hu3.huapp.web.rest.dto.TravelPackagesDTO;
import com.hu3.huapp.web.rest.mapper.TravelPackagesMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelPackagesResource REST controller.
 *
 * @see TravelPackagesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Desafiohu3App.class)
@WebAppConfiguration
@IntegrationTest
public class TravelPackagesResourceIntTest {


    private static final Long DEFAULT_ID_TRAVEL_PACKAGE = 1L;
    private static final Long UPDATED_ID_TRAVEL_PACKAGE = 2L;
    private static final String DEFAULT_TITLE_TRAVEL_PACKAGE = "AAAAA";
    private static final String UPDATED_TITLE_TRAVEL_PACKAGE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_TRAVEL_PACKAGE = "AAAAA";
    private static final String UPDATED_DESCRIPTION_TRAVEL_PACKAGE = "BBBBB";

    @Inject
    private TravelPackagesRepository travelPackagesRepository;

    @Inject
    private TravelPackagesMapper travelPackagesMapper;

    @Inject
    private TravelPackagesService travelPackagesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelPackagesMockMvc;

    private TravelPackages travelPackages;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelPackagesResource travelPackagesResource = new TravelPackagesResource();
        ReflectionTestUtils.setField(travelPackagesResource, "travelPackagesService", travelPackagesService);
        ReflectionTestUtils.setField(travelPackagesResource, "travelPackagesMapper", travelPackagesMapper);
        this.restTravelPackagesMockMvc = MockMvcBuilders.standaloneSetup(travelPackagesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelPackages = new TravelPackages();
        travelPackages.setIdTravelPackage(DEFAULT_ID_TRAVEL_PACKAGE);
        travelPackages.setTitleTravelPackage(DEFAULT_TITLE_TRAVEL_PACKAGE);
        travelPackages.setDescriptionTravelPackage(DEFAULT_DESCRIPTION_TRAVEL_PACKAGE);
    }

    @Test
    @Transactional
    public void createTravelPackages() throws Exception {
        int databaseSizeBeforeCreate = travelPackagesRepository.findAll().size();

        // Create the TravelPackages
        TravelPackagesDTO travelPackagesDTO = travelPackagesMapper.travelPackagesToTravelPackagesDTO(travelPackages);

        restTravelPackagesMockMvc.perform(post("/api/travel-packages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelPackagesDTO)))
                .andExpect(status().isCreated());

        // Validate the TravelPackages in the database
        List<TravelPackages> travelPackages = travelPackagesRepository.findAll();
        assertThat(travelPackages).hasSize(databaseSizeBeforeCreate + 1);
        TravelPackages testTravelPackages = travelPackages.get(travelPackages.size() - 1);
        assertThat(testTravelPackages.getIdTravelPackage()).isEqualTo(DEFAULT_ID_TRAVEL_PACKAGE);
        assertThat(testTravelPackages.getTitleTravelPackage()).isEqualTo(DEFAULT_TITLE_TRAVEL_PACKAGE);
        assertThat(testTravelPackages.getDescriptionTravelPackage()).isEqualTo(DEFAULT_DESCRIPTION_TRAVEL_PACKAGE);
    }

    @Test
    @Transactional
    public void checkIdTravelPackageIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPackagesRepository.findAll().size();
        // set the field null
        travelPackages.setIdTravelPackage(null);

        // Create the TravelPackages, which fails.
        TravelPackagesDTO travelPackagesDTO = travelPackagesMapper.travelPackagesToTravelPackagesDTO(travelPackages);

        restTravelPackagesMockMvc.perform(post("/api/travel-packages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelPackagesDTO)))
                .andExpect(status().isBadRequest());

        List<TravelPackages> travelPackages = travelPackagesRepository.findAll();
        assertThat(travelPackages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleTravelPackageIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPackagesRepository.findAll().size();
        // set the field null
        travelPackages.setTitleTravelPackage(null);

        // Create the TravelPackages, which fails.
        TravelPackagesDTO travelPackagesDTO = travelPackagesMapper.travelPackagesToTravelPackagesDTO(travelPackages);

        restTravelPackagesMockMvc.perform(post("/api/travel-packages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelPackagesDTO)))
                .andExpect(status().isBadRequest());

        List<TravelPackages> travelPackages = travelPackagesRepository.findAll();
        assertThat(travelPackages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTravelPackages() throws Exception {
        // Initialize the database
        travelPackagesRepository.saveAndFlush(travelPackages);

        // Get all the travelPackages
        restTravelPackagesMockMvc.perform(get("/api/travel-packages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelPackages.getId().intValue())))
                .andExpect(jsonPath("$.[*].idTravelPackage").value(hasItem(DEFAULT_ID_TRAVEL_PACKAGE.intValue())))
                .andExpect(jsonPath("$.[*].titleTravelPackage").value(hasItem(DEFAULT_TITLE_TRAVEL_PACKAGE.toString())))
                .andExpect(jsonPath("$.[*].descriptionTravelPackage").value(hasItem(DEFAULT_DESCRIPTION_TRAVEL_PACKAGE.toString())));
    }

    @Test
    @Transactional
    public void getTravelPackages() throws Exception {
        // Initialize the database
        travelPackagesRepository.saveAndFlush(travelPackages);

        // Get the travelPackages
        restTravelPackagesMockMvc.perform(get("/api/travel-packages/{id}", travelPackages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelPackages.getId().intValue()))
            .andExpect(jsonPath("$.idTravelPackage").value(DEFAULT_ID_TRAVEL_PACKAGE.intValue()))
            .andExpect(jsonPath("$.titleTravelPackage").value(DEFAULT_TITLE_TRAVEL_PACKAGE.toString()))
            .andExpect(jsonPath("$.descriptionTravelPackage").value(DEFAULT_DESCRIPTION_TRAVEL_PACKAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelPackages() throws Exception {
        // Get the travelPackages
        restTravelPackagesMockMvc.perform(get("/api/travel-packages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelPackages() throws Exception {
        // Initialize the database
        travelPackagesRepository.saveAndFlush(travelPackages);
        int databaseSizeBeforeUpdate = travelPackagesRepository.findAll().size();

        // Update the travelPackages
        TravelPackages updatedTravelPackages = new TravelPackages();
        updatedTravelPackages.setId(travelPackages.getId());
        updatedTravelPackages.setIdTravelPackage(UPDATED_ID_TRAVEL_PACKAGE);
        updatedTravelPackages.setTitleTravelPackage(UPDATED_TITLE_TRAVEL_PACKAGE);
        updatedTravelPackages.setDescriptionTravelPackage(UPDATED_DESCRIPTION_TRAVEL_PACKAGE);
        TravelPackagesDTO travelPackagesDTO = travelPackagesMapper.travelPackagesToTravelPackagesDTO(updatedTravelPackages);

        restTravelPackagesMockMvc.perform(put("/api/travel-packages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelPackagesDTO)))
                .andExpect(status().isOk());

        // Validate the TravelPackages in the database
        List<TravelPackages> travelPackages = travelPackagesRepository.findAll();
        assertThat(travelPackages).hasSize(databaseSizeBeforeUpdate);
        TravelPackages testTravelPackages = travelPackages.get(travelPackages.size() - 1);
        assertThat(testTravelPackages.getIdTravelPackage()).isEqualTo(UPDATED_ID_TRAVEL_PACKAGE);
        assertThat(testTravelPackages.getTitleTravelPackage()).isEqualTo(UPDATED_TITLE_TRAVEL_PACKAGE);
        assertThat(testTravelPackages.getDescriptionTravelPackage()).isEqualTo(UPDATED_DESCRIPTION_TRAVEL_PACKAGE);
    }

    @Test
    @Transactional
    public void deleteTravelPackages() throws Exception {
        // Initialize the database
        travelPackagesRepository.saveAndFlush(travelPackages);
        int databaseSizeBeforeDelete = travelPackagesRepository.findAll().size();

        // Get the travelPackages
        restTravelPackagesMockMvc.perform(delete("/api/travel-packages/{id}", travelPackages.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelPackages> travelPackages = travelPackagesRepository.findAll();
        assertThat(travelPackages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
