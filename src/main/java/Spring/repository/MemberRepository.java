package Spring.repository;

import Spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //저장소에 회원 저장
    //Optional은 없으면 NULL반환된다 NULL대신 Optional로 감싸서 반환
    Optional<Member> findById(Long id); //id로 회원 찾는 것
    Optional<Member> findByName(String name);
    List<Member> findAll(); //지금까지 저장된 모든 회원 리스트 반환
}
