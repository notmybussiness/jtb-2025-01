package com.example.demo.comment;

import com.example.demo.answer.Answer;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment createCommentForQuestion(Question question, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setQuestion(question);
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setCreateDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment createCommentForAnswer(Answer answer, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setAnswer(answer);
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setCreateDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

}
