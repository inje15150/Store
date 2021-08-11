package project.shop.api.v1.members;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.api.v1.members.dto.MemberDto;
import project.shop.api.v1.members.dto.MemberResult;
import project.shop.api.v1.members.dto.create.CreateMemberRequest;
import project.shop.api.v1.members.dto.create.CreateMemberResponse;
import project.shop.api.v1.members.dto.update.UpdateMemberRequest;
import project.shop.api.v1.members.dto.update.UpdateMemberResponse;
import project.shop.domain.Address;
import project.shop.domain.Member;
import project.shop.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public MemberResult members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getLoginId(), m.getName()))
                .collect(Collectors.toList());

        return new MemberResult(collect.size(), collect);

    }

    @PostMapping("/api/v1/members/save")
    public CreateMemberResponse saveMember(@RequestBody @Validated CreateMemberRequest request) {

        Member saveMember = createMember(request);

        Long id = memberService.join(saveMember);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v1/members/edit/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Validated UpdateMemberRequest request) throws Exception {

        memberService.update(id, request.getPassword(), new Address(request.getCity(), request.getStreet(), request.getZipcode()));
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(id, findMember.getName());
    }

    private Member createMember(CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setPassword(request.getPassword());
        member.setAddress(new Address(request.getCity(), request.getStreet(), request.getZipcode()));
        member.setLoginId(request.getLoginId());

        return member;
    }
}
