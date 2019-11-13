package com.kg.secretsanta.web.rest;

import com.kg.secretsanta.SecretSantaApp;
import com.kg.secretsanta.domain.Wish;
import com.kg.secretsanta.repository.WishRepository;
import com.kg.secretsanta.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kg.secretsanta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WishResource} REST controller.
 */
@SpringBootTest(classes = SecretSantaApp.class)
public class WishResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWishMockMvc;

    private Wish wish;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WishResource wishResource = new WishResource(wishRepository);
        this.restWishMockMvc = MockMvcBuilders.standaloneSetup(wishResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wish createEntity(EntityManager em) {
        Wish wish = new Wish()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL);
        return wish;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wish createUpdatedEntity(EntityManager em) {
        Wish wish = new Wish()
            .name(UPDATED_NAME)
            .url(UPDATED_URL);
        return wish;
    }

    @BeforeEach
    public void initTest() {
        wish = createEntity(em);
    }

    @Test
    @Transactional
    public void createWish() throws Exception {
        int databaseSizeBeforeCreate = wishRepository.findAll().size();

        // Create the Wish
        restWishMockMvc.perform(post("/api/wishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wish)))
            .andExpect(status().isCreated());

        // Validate the Wish in the database
        List<Wish> wishList = wishRepository.findAll();
        assertThat(wishList).hasSize(databaseSizeBeforeCreate + 1);
        Wish testWish = wishList.get(wishList.size() - 1);
        assertThat(testWish.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWish.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createWishWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wishRepository.findAll().size();

        // Create the Wish with an existing ID
        wish.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWishMockMvc.perform(post("/api/wishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wish)))
            .andExpect(status().isBadRequest());

        // Validate the Wish in the database
        List<Wish> wishList = wishRepository.findAll();
        assertThat(wishList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWishes() throws Exception {
        // Initialize the database
        wishRepository.saveAndFlush(wish);

        // Get all the wishList
        restWishMockMvc.perform(get("/api/wishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wish.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getWish() throws Exception {
        // Initialize the database
        wishRepository.saveAndFlush(wish);

        // Get the wish
        restWishMockMvc.perform(get("/api/wishes/{id}", wish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wish.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingWish() throws Exception {
        // Get the wish
        restWishMockMvc.perform(get("/api/wishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWish() throws Exception {
        // Initialize the database
        wishRepository.saveAndFlush(wish);

        int databaseSizeBeforeUpdate = wishRepository.findAll().size();

        // Update the wish
        Wish updatedWish = wishRepository.findById(wish.getId()).get();
        // Disconnect from session so that the updates on updatedWish are not directly saved in db
        em.detach(updatedWish);
        updatedWish
            .name(UPDATED_NAME)
            .url(UPDATED_URL);

        restWishMockMvc.perform(put("/api/wishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWish)))
            .andExpect(status().isOk());

        // Validate the Wish in the database
        List<Wish> wishList = wishRepository.findAll();
        assertThat(wishList).hasSize(databaseSizeBeforeUpdate);
        Wish testWish = wishList.get(wishList.size() - 1);
        assertThat(testWish.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWish.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingWish() throws Exception {
        int databaseSizeBeforeUpdate = wishRepository.findAll().size();

        // Create the Wish

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWishMockMvc.perform(put("/api/wishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wish)))
            .andExpect(status().isBadRequest());

        // Validate the Wish in the database
        List<Wish> wishList = wishRepository.findAll();
        assertThat(wishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWish() throws Exception {
        // Initialize the database
        wishRepository.saveAndFlush(wish);

        int databaseSizeBeforeDelete = wishRepository.findAll().size();

        // Delete the wish
        restWishMockMvc.perform(delete("/api/wishes/{id}", wish.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wish> wishList = wishRepository.findAll();
        assertThat(wishList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
