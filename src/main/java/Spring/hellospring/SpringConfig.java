package Spring.hellospring;

import Spring.hellospring.repository.MemberRepository;
import Spring.hellospring.repository.MemoryMemberRepository;
import Spring.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean //스프링 빈을 등록할 거야! -> 멤버 서비스 로직을 호출하여 스프링빈에 등록해줌
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        // 멤버 서비스에 연결해주어야함
        return new MemoryMemberRepository();
    }
}
