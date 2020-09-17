package Spring.hellospring.repository;

import Spring.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    //art+shift+enter로 다 implement

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    
    @Override
    public Member save(Member member) {
        member.setId(++sequence); //sequence값 하나 올려주고 == id 세팅
        store.put(member.getId(), member);  //store에 저장
        return member; //저장된 결과 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //스토어에서 id 꺼내준다 결과 없으면 NULL (Optional로 감싸준다)
    }

    @Override
    public Optional<Member> findByName(String name) {
        //맵에서 돌면서 하나 찾아지면 반환 끝까지 돌려도 없으면 Optional에 NULL 포함되서 반환
        return store.values().stream()  //루프 돌린다
                .filter(member -> member.getName().equals(name))   //member.getname과 받아온 name이 같으면 필터링
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store에 있는 member들이 반환
    }

    public void clearStore(){
        store.clear();
    }
}
