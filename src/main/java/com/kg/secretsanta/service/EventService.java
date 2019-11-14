package com.kg.secretsanta.service;


import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Gift;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.repository.EventRepository;
import com.kg.secretsanta.repository.GiftRepository;
import com.kg.secretsanta.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final GiftRepository giftRepository;
    private final MemberRepository memberRepository;

    public EventService(EventRepository eventRepository, GiftRepository giftRepository, MemberRepository memberRepository) {
        this.eventRepository = eventRepository;
        this.giftRepository = giftRepository;
        this.memberRepository = memberRepository;
    }

    public Event newEvent(Event event) {
        Set<Member> members = event.getMembers();
        List<Member> memberArray = new ArrayList<>(members);
        Collections.shuffle(memberArray);
        Event newEvent = eventRepository.save(event);
        for(int i = 0; i < memberArray.size() - 1; i++) {
            event.addGift(new Gift(memberArray.get(i), memberArray.get(i + 1),newEvent));
            if (i == memberArray.size() - 2) {
                event.addGift(new Gift(memberArray.get(i+1), memberArray.get(0),newEvent));
            }
        }
        giftRepository.saveAll(event.getGifts());



        return newEvent;

    }
}
