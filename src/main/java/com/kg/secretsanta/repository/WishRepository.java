package com.kg.secretsanta.repository;
import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.Wish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Wish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByMember(Member member);

    List<Wish> findAllByMemberAndEvent(Member member, Event event);

    List<Wish> findAllByMember_IdAndEvent_Id(Long member_id, Long event_id);
}
