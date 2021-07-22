package project.shop.web.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.shop.domain.Member;
import project.shop.service.LoginService;
import project.shop.session.SessionManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletResponse response) {

        log.info("login controller");
        log.info("form.getLoginId= {}", form.getLoginId());
        log.info("form.getPassword= {}", form.getPassword());

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.addError(new ObjectError("loginForm", "등록되지 않은 아이디거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("login error !!");
            return "login/loginForm";
        }
        //로그인 성공 처리
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        sessionManager.expire(request);

        return "redirect:/";
    }
}
