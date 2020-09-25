package Spring.hellospring.controller;

import Spring.hellospring.domain.Member;
import Spring.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // 스프링 관리하게되면 스프링 컨테이너에 다 등록하고 받아서 쓰도록해야한다
    // new하면 멤버 컨트롤러 아닌 다른 여러 컨트롤러가 멤버 서비스 가져다 쓸 수 있다
    // private final MemberService = new MemberService();
    private final MemberService memberService;
    // 하나만 생성하고 공용으로 사용하면 된다
    // 스프링 컨테이너에 등록하고 사용하면 된다 (딱 하나만 등록)

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberFore";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); //멤버를 넘긴다.

        return "redirect:/"; //회원가입 끝나면 홈화면으로 보낸다
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers(); //멤버를 다 끄집어낼 수 있다.
        model.addAttribute("members",members); //addAttrivute 기억해야한다.
        return "members/memberList";
    }
}
