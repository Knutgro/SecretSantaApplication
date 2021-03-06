package com.kg.secretsanta.web.rest;

import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.User;
import com.kg.secretsanta.repository.EventRepository;
import com.kg.secretsanta.repository.MemberRepository;
import com.kg.secretsanta.service.EventService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.kg.secretsanta.domain.Event}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final EventService eventService;

    public EventResource(EventRepository eventRepository, MemberRepository memberRepository, UserService userService,
                         EventService eventService) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    /**
     * {@code POST  /events} : Create a new event.
     *
     * @param event the event to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new event, or with status {@code 400 (Bad Request)} if the event has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to save Event : {}", event);
        if (event.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        final Optional<User> isUser = userService.getUserWithAuthorities();
        if(!isUser.isPresent()) {
            throw new BadRequestAlertException("no user present", "USER", "no user");
        }
        Member member = memberRepository.findMemberByUser(isUser.get());
        if (member == null) {
            throw new BadRequestAlertException("no member present", "MEMBER", "no member");
        }
        event.setOwned(member);
        Event result = eventService.newEvent(event);
        return ResponseEntity.created(new URI("/api/events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /events} : Updates an existing event.
     *
     * @param event the event to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated event,
     * or with status {@code 400 (Bad Request)} if the event is not valid,
     * or with status {@code 500 (Internal Server Error)} if the event couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/events")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to update Event : {}", event);
        if (event.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (eventService.isNotEventLeader(event)) {
            throw new BadRequestAlertException("invalid request", ENTITY_NAME, "not owner");
        }

        Event result = eventRepository.save(event);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, event.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /events} : get all the events.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of events in body.
     */
    @GetMapping("/events")
    public List<Event> getAllEvents(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Events");
        final Optional<User> isUser = userService.getUserWithAuthorities();
        Set<Member> members = new HashSet<>();
        if(!isUser.isPresent()) {
            throw new BadRequestAlertException("no user present", "USER", "no user");
        }
        members.add(memberRepository.findMemberByUser(isUser.get()));
        return eventRepository.findAllByMembers(members);
    }

    /**
     * {@code GET  /events/:id} : get the "id" event.
     *
     * @param id the id of the event to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        log.debug("REST request to get Event : {}", id);
        final Optional<User> isUser = userService.getUserWithAuthorities();
        Set<Member> members = new HashSet<>();
        if(!isUser.isPresent()) {
            throw new BadRequestAlertException("no user present", "USER", "no user");
        }
        members.add(memberRepository.findMemberByUser(isUser.get()));
        Optional<Event> event = eventRepository.findFirstByIdAndMembers(id, members);
        return ResponseUtil.wrapOrNotFound(event);
    }

    /**
     * {@code DELETE  /events/:id} : delete the "id" event.
     *
     * @param id the id of the event to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        final Optional<User> isUser = userService.getUserWithAuthorities();
        if (!isUser.isPresent()) {
            throw new BadRequestAlertException("User could not be found", "USER", "no user");
        }

        Member member = memberRepository.findMemberByUser(isUser.get());
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent() ) {
            throw new BadRequestAlertException("event could not be found,", "EVENT", "no event");
        }
        if (!event.get().equals(eventRepository.findByOwned(member))) {
            throw new BadRequestAlertException("not the owner", "MEMBER", "wrong owner");

        }
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
