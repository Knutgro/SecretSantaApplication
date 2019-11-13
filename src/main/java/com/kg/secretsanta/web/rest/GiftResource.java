package com.kg.secretsanta.web.rest;

import com.kg.secretsanta.domain.Gift;
import com.kg.secretsanta.repository.GiftRepository;
import com.kg.secretsanta.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kg.secretsanta.domain.Gift}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiftResource {

    private final Logger log = LoggerFactory.getLogger(GiftResource.class);

    private static final String ENTITY_NAME = "gift";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftRepository giftRepository;

    public GiftResource(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    /**
     * {@code POST  /gifts} : Create a new gift.
     *
     * @param gift the gift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gift, or with status {@code 400 (Bad Request)} if the gift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gifts")
    public ResponseEntity<Gift> createGift(@RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to save Gift : {}", gift);
        if (gift.getId() != null) {
            throw new BadRequestAlertException("A new gift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gift result = giftRepository.save(gift);
        return ResponseEntity.created(new URI("/api/gifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gifts} : Updates an existing gift.
     *
     * @param gift the gift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gift,
     * or with status {@code 400 (Bad Request)} if the gift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gifts")
    public ResponseEntity<Gift> updateGift(@RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to update Gift : {}", gift);
        if (gift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gift result = giftRepository.save(gift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gift.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gifts} : get all the gifts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gifts in body.
     */
    @GetMapping("/gifts")
    public List<Gift> getAllGifts() {
        log.debug("REST request to get all Gifts");
        return giftRepository.findAll();
    }

    /**
     * {@code GET  /gifts/:id} : get the "id" gift.
     *
     * @param id the id of the gift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gifts/{id}")
    public ResponseEntity<Gift> getGift(@PathVariable Long id) {
        log.debug("REST request to get Gift : {}", id);
        Optional<Gift> gift = giftRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gift);
    }

    /**
     * {@code DELETE  /gifts/:id} : delete the "id" gift.
     *
     * @param id the id of the gift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gifts/{id}")
    public ResponseEntity<Void> deleteGift(@PathVariable Long id) {
        log.debug("REST request to delete Gift : {}", id);
        giftRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
