package com.example.demo.user;

import com.example.demo.DataNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import java.security.Principal;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "signup_form";

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue(
                    "password2",
                    "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form"; }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/find")
    public String find(){
        return "find_form";
    }

    @PostMapping("/find")
    public String find(@RequestParam String email, Model model){
        try{
            userService.findPassword(email);
            model.addAttribute("message", "임시비밀번호를 이메일로 발송했습니다.");
            return "login_form";
        } catch(DataNotFoundException e){
            model.addAttribute("message", "등록되지 않은 이메일입니다.");
            return "find_form";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String mypage(Principal principal, Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        SiteUser user = userService.getUser(principal.getName());

        // 모델에 필요한 데이터 추가
        model.addAttribute("user", user);
        model.addAttribute("questions", user.getQuestionList());
        model.addAttribute("answers", user.getAnswerList());
        model.addAttribute("comments", user.getCommentList());

        return "profile";
    }

}
