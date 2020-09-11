package Spring.service;


import Spring.domain.Member;
import Spring.repository.MemberRepository;
import Spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { // 직접 생성하는 것이 아닌 외부에서 넣어주도록 변경
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member); // 통과하면
        return member.getId(); //저장
    }

    private void validateDuplicateMember(Member member) {
        //같은 이름이 있는 중복 회원 X
        /*Optional<Member> result = memberRepository.findByName(member.getName()); 옵셔널 반환하는 것 좋지 않다.
        result.ifPresent(m -> {  // 멤버에 값이 있으면 (NUll이 아닌 값이 있으면) optional로 감싸져있으므로 쓸 수 있다
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
        // 다음과 같이 쓰는 것 권장
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll(); //반환 타입 listmember이다
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);

    }

}
