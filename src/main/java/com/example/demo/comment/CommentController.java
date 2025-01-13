package com.example.demo.comment;


import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/question/{id}")
    public String createQuestionComment(Model model,
                                        @PathVariable("id") Integer id,
                                        CommentForm commentForm,
                                        Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        // 댓글 생성
        Comment comment = this.commentService.createCommentForQuestion(question, commentForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/answer/{id}")
    public String createAnswerComment(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            Principal principal) {

        Answer answer = this.answerService.getAnswer(id);
        Question question = answer.getQuestion();
        SiteUser siteUser = this.userService.getUser(principal.getName());

        // 댓글 생성
        Comment comment = this.commentService.createCommentForAnswer(answer, commentForm.getContent(), siteUser);

        // 댓글 ID로 URL 생성
        return String.format("redirect:/question/detail/%s#answer_%s", question.getId(), answer.getId());
    }
}
