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

    public List<Member> findMembers(String name, String city) {
        return memberRepository.findAll(name, city);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Long findByName(String name) {
        return memberRepository.findByName(name).getId();
    }

    @Transactional
    public void update(Long id, String password, Address address) {
        Member member = memberRepository.findOne(id);
        member.setPassword(password);
        member.setAddress(address);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.delete(id);
    }
}
