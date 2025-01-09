package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;

@RequiredArgsConstructor
@Getter
@Setter
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public  String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "signup_form";

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue(
                    "password2",
                    "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form"; }

        this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(),
                                userCreateForm.getPassword1());
        return "redirect:/";

    }
}
