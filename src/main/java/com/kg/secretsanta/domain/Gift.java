package com.kg.secretsanta.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Gift.
 */
@Entity
@Table(name = "gift")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("gifters")
    private Member giftedGift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("receivers")
    private Member receivedGift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("gifts")
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getGiftedGift() {
        return giftedGift;
    }

    public Gift giftedGift(Member member) {
        this.giftedGift = member;
        return this;
    }

    public void setGiftedGift(Member member) {
        this.giftedGift = member;
    }

    public Member getReceivedGift() {
        return receivedGift;
    }

    public Gift receivedGift(Member member) {
        this.receivedGift = member;
        return this;
    }

    public void setReceivedGift(Member member) {
        this.receivedGift = member;
    }

    public Event getEvent() {
        return event;
    }

    public Gift event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gift)) {
            return false;
        }
        return id != null && id.equals(((Gift) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gift{" +
            "id=" + getId() +
            "}";
    }
}
