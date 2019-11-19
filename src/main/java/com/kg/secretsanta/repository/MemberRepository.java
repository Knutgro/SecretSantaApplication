package com.kg.secretsanta.repository;
import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByUser(User user);

    Member findByOwnersIn(Set<Event> event);


}
