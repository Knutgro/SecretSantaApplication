package com.kg.secretsanta.web.rest;

import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.User;
import com.kg.secretsanta.domain.Wish;
import com.kg.secretsanta.repository.MemberRepository;
import com.kg.secretsanta.repository.WishRepository;
import com.kg.secretsanta.service.UserService;
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
 * REST controller for managing {@link com.kg.secretsanta.domain.Wish}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WishResource {

    private final Logger log = LoggerFactory.getLogger(WishResource.class);

    private static final String ENTITY_NAME = "wish";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WishRepository wishRepository;
    private final UserService userService;
    private final MemberRepository memberRepository;

    public WishResource(WishRepository wishRepository, UserService userService, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.userService = userService;
        this.memberRepository = memberRepository;
    }

    /**
     * {@code POST  /wishes} : Create a new wish.
     *
     * @param wish the wish to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wish, or with status {@code 400 (Bad Request)} if the wish has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wishes")
    public ResponseEntity<Wish> createWish(@RequestBody Wish wish) throws URISyntaxException {
        log.debug("REST request to save Wish : {}", wish);
        if (wish.getId() != null) {
            throw new BadRequestAlertException("A new wish cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wish result = wishRepository.save(wish);
        return ResponseEntity.created(new URI("/api/wishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wishes} : Updates an existing wish.
     *
     * @param wish the wish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wish,
     * or with status {@code 400 (Bad Request)} if the wish is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wishes")
    public ResponseEntity<Wish> updateWish(@RequestBody Wish wish) throws URISyntaxException {
        log.debug("REST request to update Wish : {}", wish);
        if (wish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Wish result = wishRepository.save(wish);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wish.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wishes} : get all wishes from logged in user
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wishes in body.
     */
    @GetMapping("/wishes")
    public List<Wish> getAllWishes() {
        log.debug("REST request to get all Wishes");
        final Optional<User> isUser = userService.getUserWithAuthorities();
        if(!isUser.isPresent()) {
            throw new BadRequestAlertException("no user present", "USER", "no user");
        }
        Member member = memberRepository.findMemberByUser(isUser.get());
        return wishRepository.findAllByMember(member);
    }

    /**
     * {@code GET  /wishes/:id} : get the "id" wish.
     *
     * @param id the id of the wish to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wish, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wishes/{id}")
    public ResponseEntity<Wish> getWish(@PathVariable Long id) {
        log.debug("REST request to get Wish : {}", id);
        Optional<Wish> wish = wishRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wish);
    }

    /**
     * {@code DELETE  /wishes/:id} : delete the "id" wish.
     *
     * @param id the id of the wish to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wishes/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        log.debug("REST request to delete Wish : {}", id);
        wishRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
