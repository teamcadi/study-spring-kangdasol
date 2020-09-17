package Spring.repository;

import Spring.hellospring.domain.Member;
import Spring.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트 끝날 때마다 repository 지워주는 코드
    //테스트 메서드의 실행순서 보장되지 않으므로 필요하다 (의존관계 없이 설계되어야함)
    @AfterEach  // 메서드 실행 끝날 때마다 어떤 동작 하는 것 (콜백 메서드)
    public void afterEach(){
        repository.clearStore();  //테스트 실행되고 끝날떄마다 저장소 지운다
    }

    @Test //만든 기능 실행가능하도록 한다
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); //Optional에서 값 꺼낼 때는 get() 사용하여 꺼낸다.
        //검증해야함
        //System.out.println("(result==member = " + (result==member));
        //Assertions.assertEquals(member, result); //기대, 실제값 다르면 오류난다 (junit aseertion)
        assertThat(member).isEqualTo(result); //art+enter로 static import 해주었다
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); //get으로 optional 까서 받는다

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
