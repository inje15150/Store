package project.shop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import project.shop.domain.Member;
import project.shop.repository.MemberRepository;
import project.shop.service.MemberService;
import project.shop.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {
        log.info("home controller");

        Member member = (Member)sessionManager.getSession(request);

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "login-home";
    }
}
