package com.kg.secretsanta.repository;
import com.kg.secretsanta.domain.Event;
import com.kg.secretsanta.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select distinct event from Event event left join fetch event.members",
        countQuery = "select count(distinct event) from Event event")
    Page<Event> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct event from Event event left join fetch event.members")
    List<Event> findAllWithEagerRelationships();

    List<Event> findAllByMembers(Set<Member> members);

    @Query("select event from Event event left join fetch event.members where event.id =:id")
    Optional<Event> findOneWithEagerRelationships(@Param("id") Long id);

}
