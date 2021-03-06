package com.kg.secretsanta.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_limit")
    private Integer maxLimit;

    @Column(name = "min_limit")
    private Integer minLimit;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_expired")
    private Instant dateExpired;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Gift> gifts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties("event")
    private Set<Wish> wishes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_member",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private Set<Member> members = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("owners")
    private Member owned;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public Event maxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
        return this;
    }
    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Integer getMinLimit() {
        return minLimit;
    }

    public Event minLimit(Integer minLimit) {
        this.minLimit = minLimit;
        return this;
    }

    public void setMinLimit(Integer minLimit) {
        this.minLimit = minLimit;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Event dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateExpired() {
        return dateExpired;
    }

    public Event dateExpired(Instant dateExpired) {
        this.dateExpired = dateExpired;
        return this;
    }

    public void setDateExpired(Instant dateExpired) {
        this.dateExpired = dateExpired;
    }



    public Set<Gift> getGifts() {
        return gifts;
    }

    public Event gifts(Set<Gift> gifts) {
        this.gifts = gifts;
        return this;
    }

    public Event addGift(Gift gift) {
        this.gifts.add(gift);
        gift.setEvent(this);
        return this;
    }

    public Event removeGift(Gift gift) {
        this.gifts.remove(gift);
        gift.setEvent(null);
        return this;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }

    public Set<Wish> getWishes() {
        return wishes;
    }

    public Event wishes(Set<Wish> wishes) {
        this.wishes = wishes;
        return this;
    }

    public Event addWish(Wish wish) {
        this.wishes.add(wish);
        wish.setEvent(this);
        return this;
    }

    public Event removeWish(Wish wish) {
        this.wishes.remove(wish);
        wish.setEvent(null);
        return this;
    }

    public void setWishes(Set<Wish> wishes) {
        this.wishes = wishes;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Event members(Set<Member> members) {
        this.members = members;
        return this;
    }

    public Event addMember(Member member) {
        this.members.add(member);
        member.getEvents().add(this);
        return this;
    }

    public Event removeMember(Member member) {
        this.members.remove(member);
        member.getEvents().remove(this);
        return this;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public Member getOwned() {
        return owned;
    }

    public Event owned(Member member) {
        this.owned = member;
        return this;
    }

    public void setOwned(Member member) {
        this.owned = member;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
            Objects.equals(name, event.name) &&
            Objects.equals(maxLimit, event.maxLimit) &&
            Objects.equals(minLimit, event.minLimit) &&
            Objects.equals(dateCreated, event.dateCreated) &&
            Objects.equals(dateExpired, event.dateExpired) &&
            Objects.equals(description, event.description) &&
            Objects.equals(gifts, event.gifts) &&
            Objects.equals(wishes, event.wishes) &&
            Objects.equals(members, event.members) &&
            Objects.equals(owned, event.owned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxLimit, minLimit, dateCreated, dateExpired, description, gifts, wishes, members, owned);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", maxLimit=" + maxLimit +
            ", minLimit=" + minLimit +
            ", dateCreated=" + dateCreated +
            ", dateExpired=" + dateExpired +
            ", description='" + description + '\'' +
            ", gifts=" + gifts +
            ", wishes=" + wishes +
            ", members=" + members +
            ", owned=" + owned +
            '}';
    }

}
