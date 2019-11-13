package com.kg.secretsanta.repository;
import com.kg.secretsanta.domain.Member;
import com.kg.secretsanta.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByUser(Optional<User> user);

}
