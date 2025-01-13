package com.example.demo.category;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionForm;
import com.example.demo.question.QuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private UserService userService;

    @GetMapping("/")
    public String list(Model model){
        List<Category> categories = this.categoryService.getList();
        model.addAttribute("categories", categories);
        return "category_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String categoryCreate(CategoryForm categoryForm) {
        return "category_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String categoryCreate(@Valid CategoryForm categoryForm,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "category_form";
        this.categoryService.createCategory(categoryForm.getCategory());
        return "redirect:/";
    }


}
