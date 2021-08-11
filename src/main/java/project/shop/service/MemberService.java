package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.domain.Address;
import project.shop.domain.Member;
import project.shop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 검증
     */
    public Member validateDuplicateMember(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String password, Address address) throws Exception {
        Member member = memberRepository.findOne(id);

        if (member.getPassword().equals(password)) {
            throw new Exception("기존 비밀번호와 동일합니다.");
        }
        member.setPassword(password);
        member.setAddress(address);
    }
}
