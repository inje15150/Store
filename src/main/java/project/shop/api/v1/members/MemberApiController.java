package project.shop.api.v1.members;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.api.v1.Errors;
import project.shop.api.v1.converter.MemberParameterMapping;
import project.shop.api.v1.converter.QueryParser;
import project.shop.api.v1.converter.StringToMemberParameter;
import project.shop.api.v1.members.dto.delete.DeleteMemberResponse;
import project.shop.api.v1.members.dto.read.MemberDto;
import project.shop.api.v1.members.dto.read.MemberResult;
import project.shop.api.v1.members.dto.create.*;
import project.shop.api.v1.members.dto.update.UpdateMemberRequest;
import project.shop.api.v1.members.dto.update.UpdateMemberResponse;
import project.shop.domain.Address;
import project.shop.domain.Member;
import project.shop.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    /*
    * 회원목록 API
    * */

    @GetMapping("/api/v1/members")
    public ResponseEntity memberConditionSearch(@RequestParam @Nullable String query) {

        log.info("query= {}", query);

        if (query == null) {
            List<Member> findMembers = memberService.findMembers();
            List<MemberDto> collect = changeMemberDto(findMembers);
            return new ResponseEntity(new MemberResult(collect.size(), collect), HttpStatus.OK);
        }

        QueryParser<MemberParameterMapping> q = new QueryParser<>();
        MemberParameterMapping parse = q.parse(query, new MemberParameterMapping());
        String name = parse.getName();
        String city = parse.getCity();

//        StringToMemberParameter convert = new StringToMemberParameter();
//        String name = convert.convert(query).getName();
//        String city = convert.convert(query).getCity();

        List<Member> findMembers = memberService.findMembers(name, city);

        List<MemberDto> collect = changeMemberDto(findMembers);

        return new ResponseEntity(new MemberResult(collect.size(), collect), HttpStatus.OK);
    }

    // MemberDto 로 변환 후 리턴
    private List<MemberDto> changeMemberDto(List<Member> findMembers) {

        return findMembers.stream()
                .map(m -> new MemberDto(m.getLoginId(), m.getName(), m.getAddress()))
                .collect(Collectors.toList());
    }

    /*
    * 회원가입 API
    * */
    @PostMapping("/api/v1/members/save")
    public ResponseEntity saveMember(@RequestBody @Validated CreateMemberRequest param, BindingResult bindingResult,
                                     HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorsJson;

        if (memberService.validateDuplicateMember(param.getLoginId()) != null) {
            bindingResult.addError(new FieldError("createMemberRequest", "loginId", "이미 가입된 사용자입니다."));
        }

        if (!param.getPassword().equals(param.getRe_password())) {
            bindingResult.addError(new FieldError("createMemberRequest","password", "패스워드가 동일하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            ObjectMapper mapper = new ObjectMapper();

            List<String> allErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());

            return new ResponseEntity(new Errors(allErrors), HttpStatus.BAD_REQUEST); }

        Member saveMember = createMember(param);
        Long id = memberService.join(saveMember);
        Member findMember = memberService.findOne(id);

        return new ResponseEntity(new CreateMemberResponse(id, findMember.getName(), request.getMethod()), HttpStatus.OK);
    }

    /*
    * 회원 수정 API
    * */
    @PatchMapping("/api/v1/members/{id}/edit")
    public ResponseEntity updateMember(@PathVariable("id") Long id,
                                       @RequestBody @Validated UpdateMemberRequest param, BindingResult bindingResult,
                                       HttpServletRequest request) {

        Member findMember = memberService.findOne(id);

        if (!param.getPassword().equals(param.getRe_password())) {
            bindingResult.addError(new FieldError("updateMemberRequest", "password", "패스워드가 동일하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());

            return new ResponseEntity(new Errors(errors), HttpStatus.BAD_REQUEST);
        }

        memberService.update(id, param.getPassword(), new Address(param.getCity(), param.getStreet(), param.getZipcode()));

        return new ResponseEntity(
                new UpdateMemberResponse(request.getRequestURI(), request.getMethod(), id, findMember.getName(), HttpStatus.OK), HttpStatus.OK);
    }

    /*
     * 회원 삭제 API
     * */
    @DeleteMapping("/api/v1/members/{id}/delete")
    public ResponseEntity deleteMember(@PathVariable("id") Long id, HttpServletRequest request) {
        memberService.delete(id);

        return new ResponseEntity(new DeleteMemberResponse(request.getRequestURI(), id, request.getMethod(), HttpStatus.OK), HttpStatus.OK);
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
