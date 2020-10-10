package Spring.hellospring;

import Spring.hellospring.repository.*;
import Spring.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    @Autowired //생성자 하나일때는 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }*/

    //private DataSource dataSource;

    //스프링부트가 자체적으로 빈도 생성해준다.
    //밑 코드에서 주입해준다.
    /*
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Bean //스프링 빈을 등록할 거야! -> 멤버 서비스 로직을 호출하여 스프링빈에 등록해줌
    public MemberService memberService(){
        //return new MemberService(memberRepository());
        return new MemberService(memberRepository); //의존관계 세팅
    }

/*
    @Bean
    public MemberRepository memberRepository(){
        // 멤버 서비스에 연결해주어야함
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);

    }*/
}
