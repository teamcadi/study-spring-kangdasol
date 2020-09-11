package Spring.service;

import Spring.domain.Member;
import Spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    //클리어 해주기위해 가져와야함
    MemoryMemberRepository memberRepository; //main->service와 다른 repository

    @BeforeEach
    public void beforeEach(){
        // 같은 메모리 멤버 repository 사용하도록 해주는 것
        memberRepository = new MemoryMemberRepository(); //repository 만들고
        memberService = new MemberService(memberRepository); //memberservice에 repository 넣어준다
    }

    @AfterEach  // 메서드 실행 끝날 때마다 어떤 동작 하는 것 (콜백 메서드)
    public void afterEach(){
        memberRepository.clearStore();  //테스트 실행되고 끝날떄마다 저장소 지운다
    }

    @Test
    void 회원가입() { // 테스트는 한글로 해도 된다
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
       /* try{
            memberService.join(member2);
            fail(); //exception안뜨면 fail
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        } try catch는 애매하다*/
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//로직 실행할 떄 예외 터져야함

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}