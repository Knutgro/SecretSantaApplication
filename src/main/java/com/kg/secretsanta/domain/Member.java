package com.kg.secretsanta.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "member")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wish> wishes = new HashSet<>();

    @OneToMany(mappedBy = "owned")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> owners = new HashSet<>();

    @OneToMany(mappedBy = "giftedGift")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Gift> gifters = new HashSet<>();

    @OneToMany(mappedBy = "receivedGift")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Gift> receivers = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Member firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Member lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public Member user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Wish> getWishes() {
        return wishes;
    }

    public Member wishes(Set<Wish> wishes) {
        this.wishes = wishes;
        return this;
    }

    public Member addWish(Wish wish) {
        this.wishes.add(wish);
        wish.setMember(this);
        return this;
    }

    public Member removeWish(Wish wish) {
        this.wishes.remove(wish);
        wish.setMember(null);
        return this;
    }

    public void setWishes(Set<Wish> wishes) {
        this.wishes = wishes;
    }

    public Set<Event> getOwners() {
        return owners;
    }

    public Member owners(Set<Event> events) {
        this.owners = events;
        return this;
    }

    public Member addOwner(Event event) {
        this.owners.add(event);
        event.setOwned(this);
        return this;
    }

    public Member removeOwner(Event event) {
        this.owners.remove(event);
        event.setOwned(null);
        return this;
    }

    public void setOwners(Set<Event> events) {
        this.owners = events;
    }

    public Set<Gift> getGifters() {
        return gifters;
    }

    public Member gifters(Set<Gift> gifts) {
        this.gifters = gifts;
        return this;
    }

    public Member addGifter(Gift gift) {
        this.gifters.add(gift);
        gift.setGiftedGift(this);
        return this;
    }

    public Member removeGifter(Gift gift) {
        this.gifters.remove(gift);
        gift.setGiftedGift(null);
        return this;
    }

    public void setGifters(Set<Gift> gifts) {
        this.gifters = gifts;
    }

    public Set<Gift> getReceivers() {
        return receivers;
    }

    public Member receivers(Set<Gift> gifts) {
        this.receivers = gifts;
        return this;
    }

    public Member addReceiver(Gift gift) {
        this.receivers.add(gift);
        gift.setReceivedGift(this);
        return this;
    }

    public Member removeReceiver(Gift gift) {
        this.receivers.remove(gift);
        gift.setReceivedGift(null);
        return this;
    }

    public void setReceivers(Set<Gift> gifts) {
        this.receivers = gifts;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Member events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Member addEvent(Event event) {
        this.events.add(event);
        event.getMembers().add(this);
        return this;
    }

    public Member removeEvent(Event event) {
        this.events.remove(event);
        event.getMembers().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
