package Spring.hellospring.controller;

import Spring.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // 스프링 관리하게되면 스프링 컨테이너에 다 등록하고 받아서 쓰도록해야한다
    // new하면 멤버 컨트롤러 아닌 다른 여러 컨트롤러가 멤버 서비스 가져다 쓸 수 있다
    private final MemberService memberService;
    // 하나만 생성하고 공용으로 사용하면 된다
    // 스프링 컨테이너에 등록하고 사용하면 된다 (딱 하나만 등록)

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
