package com.hu3.huapp.web.rest;

import com.hu3.huapp.Desafiohu3App;
import com.hu3.huapp.domain.Country;
import com.hu3.huapp.repository.CountryRepository;
import com.hu3.huapp.service.CountryService;
import com.hu3.huapp.web.rest.dto.CountryDTO;
import com.hu3.huapp.web.rest.mapper.CountryMapper;

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
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Desafiohu3App.class)
@WebAppConfiguration
@IntegrationTest
public class CountryResourceIntTest {


    private static final Long DEFAULT_ID_COUNTRY = 1L;
    private static final Long UPDATED_ID_COUNTRY = 2L;
    private static final String DEFAULT_NAME_COUNTRY = "AAAAA";
    private static final String UPDATED_NAME_COUNTRY = "BBBBB";

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private CountryMapper countryMapper;

    @Inject
    private CountryService countryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCountryMockMvc;

    private Country country;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountryResource countryResource = new CountryResource();
        ReflectionTestUtils.setField(countryResource, "countryService", countryService);
        ReflectionTestUtils.setField(countryResource, "countryMapper", countryMapper);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        country = new Country();
        country.setIdCountry(DEFAULT_ID_COUNTRY);
        country.setNameCountry(DEFAULT_NAME_COUNTRY);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.countryToCountryDTO(country);

        restCountryMockMvc.perform(post("/api/countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
                .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countries = countryRepository.findAll();
        assertThat(countries).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countries.get(countries.size() - 1);
        assertThat(testCountry.getIdCountry()).isEqualTo(DEFAULT_ID_COUNTRY);
        assertThat(testCountry.getNameCountry()).isEqualTo(DEFAULT_NAME_COUNTRY);
    }

    @Test
    @Transactional
    public void checkIdCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setIdCountry(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.countryToCountryDTO(country);

        restCountryMockMvc.perform(post("/api/countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
                .andExpect(status().isBadRequest());

        List<Country> countries = countryRepository.findAll();
        assertThat(countries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setNameCountry(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.countryToCountryDTO(country);

        restCountryMockMvc.perform(post("/api/countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
                .andExpect(status().isBadRequest());

        List<Country> countries = countryRepository.findAll();
        assertThat(countries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countries
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
                .andExpect(jsonPath("$.[*].idCountry").value(hasItem(DEFAULT_ID_COUNTRY.intValue())))
                .andExpect(jsonPath("$.[*].nameCountry").value(hasItem(DEFAULT_NAME_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.idCountry").value(DEFAULT_ID_COUNTRY.intValue()))
            .andExpect(jsonPath("$.nameCountry").value(DEFAULT_NAME_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = new Country();
        updatedCountry.setId(country.getId());
        updatedCountry.setIdCountry(UPDATED_ID_COUNTRY);
        updatedCountry.setNameCountry(UPDATED_NAME_COUNTRY);
        CountryDTO countryDTO = countryMapper.countryToCountryDTO(updatedCountry);

        restCountryMockMvc.perform(put("/api/countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
                .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countries = countryRepository.findAll();
        assertThat(countries).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countries.get(countries.size() - 1);
        assertThat(testCountry.getIdCountry()).isEqualTo(UPDATED_ID_COUNTRY);
        assertThat(testCountry.getNameCountry()).isEqualTo(UPDATED_NAME_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Get the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Country> countries = countryRepository.findAll();
        assertThat(countries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
