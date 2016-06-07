package com.hu3.huapp.web.rest;

import com.hu3.huapp.Desafiohu3App;
import com.hu3.huapp.domain.RegionOfOrigin;
import com.hu3.huapp.repository.RegionOfOriginRepository;
import com.hu3.huapp.service.RegionOfOriginService;
import com.hu3.huapp.web.rest.dto.RegionOfOriginDTO;
import com.hu3.huapp.web.rest.mapper.RegionOfOriginMapper;

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
 * Test class for the RegionOfOriginResource REST controller.
 *
 * @see RegionOfOriginResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Desafiohu3App.class)
@WebAppConfiguration
@IntegrationTest
public class RegionOfOriginResourceIntTest {

    private static final String DEFAULT_NAME_REGION = "AAAAA";
    private static final String UPDATED_NAME_REGION = "BBBBB";

    @Inject
    private RegionOfOriginRepository regionOfOriginRepository;

    @Inject
    private RegionOfOriginMapper regionOfOriginMapper;

    @Inject
    private RegionOfOriginService regionOfOriginService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRegionOfOriginMockMvc;

    private RegionOfOrigin regionOfOrigin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegionOfOriginResource regionOfOriginResource = new RegionOfOriginResource();
        ReflectionTestUtils.setField(regionOfOriginResource, "regionOfOriginService", regionOfOriginService);
        ReflectionTestUtils.setField(regionOfOriginResource, "regionOfOriginMapper", regionOfOriginMapper);
        this.restRegionOfOriginMockMvc = MockMvcBuilders.standaloneSetup(regionOfOriginResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        regionOfOrigin = new RegionOfOrigin();
        regionOfOrigin.setNameRegion(DEFAULT_NAME_REGION);
    }

    @Test
    @Transactional
    public void createRegionOfOrigin() throws Exception {
        int databaseSizeBeforeCreate = regionOfOriginRepository.findAll().size();

        // Create the RegionOfOrigin
        RegionOfOriginDTO regionOfOriginDTO = regionOfOriginMapper.regionOfOriginToRegionOfOriginDTO(regionOfOrigin);

        restRegionOfOriginMockMvc.perform(post("/api/region-of-origins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(regionOfOriginDTO)))
                .andExpect(status().isCreated());

        // Validate the RegionOfOrigin in the database
        List<RegionOfOrigin> regionOfOrigins = regionOfOriginRepository.findAll();
        assertThat(regionOfOrigins).hasSize(databaseSizeBeforeCreate + 1);
        RegionOfOrigin testRegionOfOrigin = regionOfOrigins.get(regionOfOrigins.size() - 1);
        assertThat(testRegionOfOrigin.getNameRegion()).isEqualTo(DEFAULT_NAME_REGION);
    }

    @Test
    @Transactional
    public void checkNameRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionOfOriginRepository.findAll().size();
        // set the field null
        regionOfOrigin.setNameRegion(null);

        // Create the RegionOfOrigin, which fails.
        RegionOfOriginDTO regionOfOriginDTO = regionOfOriginMapper.regionOfOriginToRegionOfOriginDTO(regionOfOrigin);

        restRegionOfOriginMockMvc.perform(post("/api/region-of-origins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(regionOfOriginDTO)))
                .andExpect(status().isBadRequest());

        List<RegionOfOrigin> regionOfOrigins = regionOfOriginRepository.findAll();
        assertThat(regionOfOrigins).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegionOfOrigins() throws Exception {
        // Initialize the database
        regionOfOriginRepository.saveAndFlush(regionOfOrigin);

        // Get all the regionOfOrigins
        restRegionOfOriginMockMvc.perform(get("/api/region-of-origins?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(regionOfOrigin.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameRegion").value(hasItem(DEFAULT_NAME_REGION.toString())));
    }

    @Test
    @Transactional
    public void getRegionOfOrigin() throws Exception {
        // Initialize the database
        regionOfOriginRepository.saveAndFlush(regionOfOrigin);

        // Get the regionOfOrigin
        restRegionOfOriginMockMvc.perform(get("/api/region-of-origins/{id}", regionOfOrigin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(regionOfOrigin.getId().intValue()))
            .andExpect(jsonPath("$.nameRegion").value(DEFAULT_NAME_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegionOfOrigin() throws Exception {
        // Get the regionOfOrigin
        restRegionOfOriginMockMvc.perform(get("/api/region-of-origins/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionOfOrigin() throws Exception {
        // Initialize the database
        regionOfOriginRepository.saveAndFlush(regionOfOrigin);
        int databaseSizeBeforeUpdate = regionOfOriginRepository.findAll().size();

        // Update the regionOfOrigin
        RegionOfOrigin updatedRegionOfOrigin = new RegionOfOrigin();
        updatedRegionOfOrigin.setId(regionOfOrigin.getId());
        updatedRegionOfOrigin.setNameRegion(UPDATED_NAME_REGION);
        RegionOfOriginDTO regionOfOriginDTO = regionOfOriginMapper.regionOfOriginToRegionOfOriginDTO(updatedRegionOfOrigin);

        restRegionOfOriginMockMvc.perform(put("/api/region-of-origins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(regionOfOriginDTO)))
                .andExpect(status().isOk());

        // Validate the RegionOfOrigin in the database
        List<RegionOfOrigin> regionOfOrigins = regionOfOriginRepository.findAll();
        assertThat(regionOfOrigins).hasSize(databaseSizeBeforeUpdate);
        RegionOfOrigin testRegionOfOrigin = regionOfOrigins.get(regionOfOrigins.size() - 1);
        assertThat(testRegionOfOrigin.getNameRegion()).isEqualTo(UPDATED_NAME_REGION);
    }

    @Test
    @Transactional
    public void deleteRegionOfOrigin() throws Exception {
        // Initialize the database
        regionOfOriginRepository.saveAndFlush(regionOfOrigin);
        int databaseSizeBeforeDelete = regionOfOriginRepository.findAll().size();

        // Get the regionOfOrigin
        restRegionOfOriginMockMvc.perform(delete("/api/region-of-origins/{id}", regionOfOrigin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RegionOfOrigin> regionOfOrigins = regionOfOriginRepository.findAll();
        assertThat(regionOfOrigins).hasSize(databaseSizeBeforeDelete - 1);
    }
}
