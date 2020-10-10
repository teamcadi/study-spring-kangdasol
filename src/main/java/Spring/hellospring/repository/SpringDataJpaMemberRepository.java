package Spring.hellospring.repository;

import Spring.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //인터페이스는 다중상속 가능


    //JPQL select m from Member m where m,namme =?
    @Override
    Optional<Member> findByName(String name);
}
