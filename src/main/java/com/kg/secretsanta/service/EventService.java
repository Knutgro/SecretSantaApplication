package com.kg.secretsanta.service;


import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Gift;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.User;
import com.kg.secretsanta.repository.EventRepository;
import com.kg.secretsanta.repository.GiftRepository;
import com.kg.secretsanta.repository.MemberRepository;
import com.kg.secretsanta.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final GiftRepository giftRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, GiftRepository giftRepository, MemberRepository memberRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.giftRepository = giftRepository;
        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    public Boolean isNotEventLeader(Event event) {
        final Optional<User> isUser = userService.getUserWithAuthorities();
        if(!isUser.isPresent()) {
            throw new BadRequestAlertException("no user present", "USER", "no user");
        }
        Member member = memberRepository.findMemberByUser(isUser.get());
        return eventRepository.findEventByOwnedAndId(member, event.getId()) == null;
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
