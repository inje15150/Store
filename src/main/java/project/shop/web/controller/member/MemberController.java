package project.shop.web.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.shop.domain.Address;
import project.shop.domain.Member;
import project.shop.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Validated @ModelAttribute MemberForm form, BindingResult result) {
        log.info("create controller");

        if (!(form.getPassword().equals(form.getRe_password()))) {
            log.info("password= {}, re_password= {}", form.getPassword(), form.getRe_password());
            result.addError(new ObjectError("memberForm", "비밀번호가 일치하지 않습니다."));
        }

        if (result.hasErrors()) {
            log.info("errors= {}", result);
            return "members/createMemberForm";
        }

        Member member = getMember(form.getName(), form.getLoginId(), form.getPassword(), form.getCity(), form.getStreet(), form.getZipcode());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    private Address getAddress(String city, String street, String zipcode) {
        return new Address(city, street, zipcode);
    }

    private Member getMember(String name, String loginId, String password, String city, String street, String zipcode) {

        Member member = new Member();
        member.setName(name);
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setAddress(new Address(city, street, zipcode));

        return member;
    }
}
